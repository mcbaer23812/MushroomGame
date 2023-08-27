package com.mushroom.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MapRenderer {
	// BACKGROUNDS
	private SpriteBatch backgroundBatch;
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	// GRASS
	private SpriteBatch decorBatch;
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
	// MAP & RENDERER
	private TiledMap map;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private float PPM = 100.0f; // Pixels Per Meter

	public MapRenderer() {
		// BACKGROUNDS & OTHER TEXTURES
		backgroundBatch = new SpriteBatch();
		backgroundTexture1 = new Texture("images/background_layer_1.png");
		backgroundTexture2 = new Texture("images/background_layer_2.png");
		backgroundTexture3 = new Texture("images/background_layer_3.png");
		// TEXTURE ATLAS, GRASS REGIONS, ROCK REGIONS, MISC OBJECTS
		decorBatch = new SpriteBatch();
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
		for (TextureAtlas.AtlasRegion region : decorAtlas.getRegions()) {
			// Apply texture filter so they aren't blurry
			region.getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
		// CAMERA & VEWIPORT
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(640.0f / PPM, 320.0f / PPM, camera);
		// MAP & MAP RENDERER
		map = new TmxMapLoader().load("tilesets/OakWoodsTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
	}
	
	public void renderMap(Vector2 playerPosition) {
		updateCamera(playerPosition);
		renderBackgrounds(6);
		renderTileMap();
		
		decorBatch.setProjectionMatrix(camera.combined);
		decorBatch.begin();
		// scaling x & y based on what looked best for grass1 texture, then multiplying
		// as
		// appropriate based on sizes in decorAtlas file, then manually tweaking if
		// necessary
		decorBatch.draw(fenceRegion1, 575.0f / PPM, 31.8f / PPM, 121.1f / PPM, 31.3f / PPM);
		decorBatch.draw(grassRegion1, 740.0f / PPM, 31.8f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 330.0f / PPM, 255.0f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 935.0f / PPM, 31.8f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(grassRegion1, 640.0f / PPM, 31.8f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 460.0f / PPM, 31.8f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 1510.0f / PPM, 64.0f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 1775.0f / PPM, 64.0f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion1, 1950.0f / PPM, 64.0f / PPM, 17.0f / PPM, 5.0f / PPM);
		decorBatch.draw(grassRegion2, 776.0f / PPM, 31.8f / PPM, 21.3f / PPM, 8.4f / PPM);
		decorBatch.draw(grassRegion2, 880.0f / PPM, 31.8f / PPM, 21.3f / PPM, 8.4f / PPM);
		decorBatch.draw(grassRegion2, 420.0f / PPM, 64.0f / PPM, 21.3f / PPM, 8.4f / PPM);
		decorBatch.draw(grassRegion2, 1150.0f / PPM, 127.8f / PPM, 21.3f / PPM, 8.4f / PPM);
		decorBatch.draw(grassRegion2, 1925.0f / PPM, 64.0f / PPM, 21.3f / PPM, 8.4f / PPM);
		decorBatch.draw(grassRegion3, 610.0f / PPM, 31.8f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(grassRegion3, 820.0f / PPM, 31.8f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(grassRegion3, 1180.0f / PPM, 127.8f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(grassRegion3, 1900.0f / PPM, 64.0f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(grassRegion3, 2155.0f / PPM, 96.0f / PPM, 19.1f / PPM, 6.7f / PPM);
		decorBatch.draw(rockRegion1, 480.0f / PPM, 31.8f / PPM, 42.5f / PPM, 18.3f / PPM);
		decorBatch.draw(rockRegion1, 1300.0f / PPM, 63.8f / PPM, 42.5f / PPM, 18.3f / PPM);
		decorBatch.draw(rockRegion2, 825.0f / PPM, 63.8f / PPM, 57.4f / PPM, 20.0f / PPM);
		decorBatch.draw(rockRegion2, 1420.0f / PPM, 63.8f / PPM, 57.4f / PPM, 20.0f / PPM);
		decorBatch.draw(rockRegion3, 1030.0f / PPM, 127.8f / PPM, 95.6f / PPM, 30.0f / PPM);
		decorBatch.draw(rockRegion3, 1330.0f / PPM, 64.0f / PPM, 95.6f / PPM, 30.0f / PPM);
		decorBatch.draw(rockRegion3, 2000.0f / PPM, 64.0f / PPM, 95.6f / PPM, 30.0f / PPM);
		decorBatch.draw(lampRegion, 525.0f / PPM, 31.8f / PPM, 41.9f / PPM, 95.0f / PPM);
		decorBatch.draw(lampRegion, 695.0f / PPM, 31.8f / PPM, 41.9f / PPM, 95.0f / PPM);
		decorBatch.draw(lampRegion, 1815.0f / PPM, 64.0f / PPM, 41.9f / PPM, 95.0f / PPM);
		decorBatch.draw(fenceRegion2, 1615.0f / PPM, 64.0f / PPM,  121.1f / PPM, 31.3f / PPM);
		decorBatch.draw(signRegion, 930.0f / PPM, 127.8f / PPM, 29.8f / PPM, 51.5f / PPM);
		
		decorBatch.end();
	}
	
	public void updateCamera(Vector2 playerPosition) {
		camera.update();
		camera.position.set(playerPosition.x, viewport.getWorldHeight() / 2, 0);
	}
	
	public void renderTileMap() {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	
	public void renderBackgrounds(int repetitions) {
		backgroundBatch.setProjectionMatrix(camera.combined);
		backgroundBatch.begin();
		float backgroundOffset = 0;
		for(int i = 0; i < repetitions; i++) {
			backgroundBatch.draw(backgroundTexture1, backgroundOffset, 0, 6.4f, 3.6f);
			backgroundBatch.draw(backgroundTexture2, backgroundOffset, 0, 6.4f, 3.6f);
			backgroundBatch.draw(backgroundTexture3, backgroundOffset, 0, 6.4f, 3.6f);
			backgroundOffset += backgroundTexture1.getWidth()/PPM;
		}

		backgroundBatch.end();
	}

	public void resize(int width, int height) {
		camera.position.set(new Vector3(0, 0, 0)); // this vector3 object is temporary, as its values are passed to the
													// camera position and then it gets ready for garbage collection
		camera.update();
		viewport.update(width, height, true);
	}

	public TiledMap getTiledMap() {
		return map;
	}

	public OrthographicCamera getOrthoCamera() {
		return camera;
	}

	public ExtendViewport getViewport() {
		return viewport;
	}
	
	public void dispose() {
		backgroundBatch.dispose();
		backgroundTexture1.dispose();
		backgroundTexture2.dispose();
		backgroundTexture3.dispose();
		map.dispose();
		tiledMapRenderer.dispose();
		decorAtlas.dispose();
	}
}

