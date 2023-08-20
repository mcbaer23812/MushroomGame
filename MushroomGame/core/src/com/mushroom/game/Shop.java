package com.mushroom.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Shop {
	private Texture shopSpriteSheet;
	private TextureRegion[] frames;
	private Animation<TextureRegion> animation;
	private float stateTime;
	private float PPM = 100f;
	
	public Shop(Texture shopSpriteSheet) {
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
	
	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, 1655.0f/PPM, 64.0f/PPM, 118.0f/PPM, 128.0f/PPM);
	}
	
	public void dispose() {
		shopSpriteSheet.dispose();
	}
	
}
