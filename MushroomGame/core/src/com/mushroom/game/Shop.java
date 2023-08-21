package com.mushroom.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Shop implements ContactListener{
	private Texture shopSpriteSheet;
	private TextureRegion[] frames;
	private Animation<TextureRegion> animation;
	private float stateTime;
	private float PPM = 100f;
	
	private World world;
	private Body body;
	
	private boolean touching;
	
	public Shop(Texture shopSpriteSheet, World world, Player player) {
		this.world = world;
		TextureRegion[][] textureRegions = TextureRegion.split(shopSpriteSheet, 118, 128);
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
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, 1655.0f/PPM, 64.0f/PPM, 118.0f/PPM, 128.0f/PPM);
	}
	
	public boolean isBodyFixture(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("body");
	}
	
	public boolean isShopFixture(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("shop");
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		if(isBodyFixture(fixtureA) && isShopFixture(fixtureB)) {
			touching = true;
		} else if(isShopFixture(fixtureA) && isBodyFixture(fixtureB)) {
			touching = true;
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		if(isBodyFixture(fixtureA) && isShopFixture(fixtureB)) {
			touching = false;
		} else if(isShopFixture(fixtureA) && isBodyFixture(fixtureB)) {
			touching = false;
		}		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getTouching() {
		return touching; 
	}
	
	public void dispose() {
		shopSpriteSheet.dispose();
		world.dispose();
		
	}
	
}
