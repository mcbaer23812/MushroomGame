package com.mushroom.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;

public class Shop{
	private Texture shopSpriteSheet;
	private TextureRegion[] frames;
	private Animation<TextureRegion> animation;
	private float stateTime;
	private float PPM = 100f;
	
	private World world;
	private Body body;
	
	private BitmapFont interactFont;
	private String interactText;
	private int interactCounter;
	private boolean touching;
	
	public Shop(Texture shopSpriteSheet, World world, Player player) {
		this.world = world;
		TextureRegion[][] textureRegions = TextureRegion.split(shopSpriteSheet, 118, 128);
		
		interactFont = new BitmapFont();
//		interactFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		interactFont.getData().setScale(0.75f/PPM);
		interactFont.setUseIntegerPositions(false);
		interactCounter = 0;
		setInteractText();
		
		frames = new TextureRegion[5];
		int index = 0;
		for (int i = 0; i < 5; i++) {
			frames[index++] = textureRegions[0][i];
		}
		animation = new Animation<TextureRegion>(1f/5f, frames); //1 second for 5 frames
	}
	
	public void update(float delta) {
		stateTime += delta;
	}
	
	public void draw(SpriteBatch batch) {
		GlyphLayout fontLayout = new GlyphLayout(interactFont, interactText, Color.WHITE, 1000.0f, Align.left, false);
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, 1655.0f/PPM, 64.0f/PPM, 118.0f/PPM, 128.0f/PPM);
		
		if(getTouching()) {
			interactFont.draw(batch, fontLayout, 1670.0f/PPM, 128.0f/PPM);
		}
	}
	
	public void setHitbox(Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(position.x / PPM, position.y / PPM);
		body = this.world.createBody(bodyDef);
		
		PolygonShape shopShape = new PolygonShape();
		shopShape.setAsBox((frames[0].getRegionWidth() / 4.5f) / PPM, (frames[0].getRegionHeight() / 3.5f) / PPM);
		FixtureDef shopFixtureDef = new FixtureDef();
		shopFixtureDef.shape = shopShape;
		shopFixtureDef.isSensor = true;
		Fixture shopFixture = body.createFixture(shopFixtureDef);
		shopFixture.setUserData("shop");
		shopShape.dispose();
	}
	
	public void setInteractText() {
		if(interactCounter == 0) {
			interactText = "Press E to interact.";
		}
	}
	
	public boolean getTouching() {
		return touching; 
	}
	
	public void setTouching(boolean touching) {
		this.touching = touching;
	}
	
	public void dispose() {
		shopSpriteSheet.dispose();
		world.dispose();
		
	}
	
}
