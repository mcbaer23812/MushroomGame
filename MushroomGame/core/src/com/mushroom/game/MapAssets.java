package com.mushroom.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MapAssets {
	// BACKGROUNDS
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	private TextureRegion backgroundRegion1;
	private TextureRegion backgroundRegion2;
	private TextureRegion backgroundRegion3;
	//DECOR ASSETS
	private TextureAtlas decorAtlas;
	private AtlasRegion fenceRegion1;
	private AtlasRegion fenceRegion2;
	private AtlasRegion grassRegion1;
	private AtlasRegion grassRegion2;
	private AtlasRegion grassRegion3;
	private AtlasRegion lampRegion;
	private AtlasRegion rockRegion1;
	private AtlasRegion rockRegion2;
	private AtlasRegion rockRegion3;
	private AtlasRegion signRegion;
	// CAMERA & VIEWPORT
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private static final float PPM = 100.0f;

	public MapAssets() {
		// BACKGROUNDS & OTHER TEXTURES
		backgroundTexture1 = new Texture("images/background_layer_1.png");
		backgroundTexture2 = new Texture("images/background_layer_2.png");
		backgroundTexture3 = new Texture("images/background_layer_3.png");
		// TEXTURE ATLAS, GRASS REGIONS, ROCK REGIONS, MISC OBJECTS
		decorAtlas = new TextureAtlas(Gdx.files.internal("atlases/decorAtlas.atlas"));
		fenceRegion1 = decorAtlas.findRegion("fence1");
		fenceRegion2 = decorAtlas.findRegion("fence2");
		grassRegion1 = decorAtlas.findRegion("grass1");
		grassRegion2 = decorAtlas.findRegion("grass2");
		grassRegion3 = decorAtlas.findRegion("grass3");
		lampRegion = decorAtlas.findRegion("lamp");
		rockRegion1 = decorAtlas.findRegion("rock1");
		rockRegion2 = decorAtlas.findRegion("rock2");
		rockRegion3 = decorAtlas.findRegion("rock3");
		signRegion = decorAtlas.findRegion("sign");
		backgroundRegion1 = new TextureRegion(backgroundTexture1);
		backgroundRegion2 = new TextureRegion(backgroundTexture2);
		backgroundRegion3 = new TextureRegion(backgroundTexture3);
		for (TextureAtlas.AtlasRegion region : decorAtlas.getRegions()) {
			// Apply texture filter so they aren't blurry
			region.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
		// CAMERA & VEWIPORT
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(512.0f / PPM, 256.0f / PPM, camera);
	}
	
	public void drawBackgrounds(int repetitions, SpriteBatch batch) {
		float backgroundOffset = 0;
		for(int i = 0; i < repetitions; i++) {
			batch.draw(backgroundRegion1, backgroundOffset, 0, 6.4f, 3.6f);
			batch.draw(backgroundRegion2, backgroundOffset, 0, 6.4f, 3.6f);
			batch.draw(backgroundRegion3, backgroundOffset, 0, 6.4f, 3.6f);
			backgroundOffset += backgroundTexture1.getWidth()/PPM;
		}
	}
	
	public void drawFenceOne(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(fenceRegion1, xPosition / PPM, yPosition / PPM, 121.1f / PPM, 31.3f / PPM);
	}
	
	public void drawFenceTwo(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(fenceRegion2, xPosition / PPM, yPosition / PPM,  121.1f / PPM, 31.3f / PPM);

	}
	
	public void drawGrassOne(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(grassRegion1, xPosition / PPM, yPosition / PPM, 17.0f / PPM, 5.0f / PPM);
	}
	
	public void drawGrassTwo(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(grassRegion2, xPosition / PPM, yPosition / PPM, 21.3f / PPM, 8.4f / PPM);
	}
	
	public void drawGrassThree(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(grassRegion3, xPosition / PPM, yPosition / PPM, 19.1f / PPM, 6.7f / PPM);
	}
	
	public void drawLamp(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(lampRegion, xPosition / PPM, yPosition / PPM, 41.9f / PPM, 95.0f / PPM);
	}
	
	public void drawRockOne(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(rockRegion1, xPosition / PPM, yPosition / PPM, 42.5f / PPM, 18.3f / PPM);
	}
	
	public void drawRockTwo(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(rockRegion2, xPosition / PPM, yPosition / PPM, 57.4f / PPM, 20.0f / PPM);

	}
	
	public void drawRockThree(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(rockRegion3, xPosition / PPM, yPosition / PPM, 95.6f / PPM, 30.0f / PPM);
	}
	
	public void drawSign(SpriteBatch batch, float xPosition, float yPosition) {
		batch.draw(signRegion, xPosition / PPM, yPosition / PPM, 29.8f / PPM, 51.5f / PPM);
	}

	public OrthographicCamera getOrthoCamera() {
		return camera;
	}

	public ExtendViewport getViewport() {
		return viewport;
	}
	
	public void dispose() {
		backgroundTexture1.dispose();
		backgroundTexture2.dispose();
		backgroundTexture3.dispose();
		decorAtlas.dispose();
	}
}

