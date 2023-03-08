package com.mushroom.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	private SpriteBatch backgroundBatch1;
	private SpriteBatch backgroundBatch2;
	private SpriteBatch backgroundBatch3;
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	
	//GRASS
	private SpriteBatch grassBatch;
	private TextureAtlas grassAtlas;
	private AtlasRegion grassRegion1;
	private AtlasRegion grassRegion2;
	private AtlasRegion grassRegion3;
	private float backgroundOffset = 0f;
	private float PPM = 100f; // Pixels Per Meter
	// CAMERA & VIEWPORT
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	// MAP & RENDERER
	private TiledMap map;
	private OrthogonalTiledMapRenderer tiledMapRenderer;

	public MapRenderer() {
		// BACKGROUNDS & OTHER TEXTURES
		backgroundBatch1 = new SpriteBatch();
		backgroundBatch2 = new SpriteBatch();
		backgroundBatch3 = new SpriteBatch();
		backgroundTexture1 = new Texture("images/background_layer_1.png");
		backgroundTexture2 = new Texture("images/background_layer_2.png");
		backgroundTexture3 = new Texture("images/background_layer_3.png");
		
		//TEXTURE ATLAS, GRASS REGIONS
		grassBatch = new SpriteBatch();
		grassAtlas = new TextureAtlas(Gdx.files.internal("atlases/testGrassAtlas.atlas"));
		grassRegion1 = grassAtlas.findRegion("grass1");
		grassRegion2 = grassAtlas.findRegion("grass2");
		grassRegion3 = grassAtlas.findRegion("grass3");
		for (TextureAtlas.AtlasRegion region : grassAtlas.getRegions()) {
		    // Apply texture filter so they aren't blurry
		    region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		}		
		// CAMERA & VEWIPORT
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(640 / PPM, 320 / PPM, camera);
		// MAP & MAP RENDERER
		map = new TmxMapLoader().load("tilesets/OakWoodsTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
	}

	public void renderMap(Vector2 playerPosition) {
		camera.update();
		camera.position.set(playerPosition.x, viewport.getWorldHeight() / 2, 0);
		loadBackgrounds(0 + backgroundOffset, 0);
		loadBackgrounds(viewport.getWorldWidth() + backgroundOffset, 0);
		if (playerPosition.x - backgroundOffset >= viewport.getWorldWidth() * 1.5) {
			backgroundOffset += 640 / PPM;
		} else if (playerPosition.x <= (0 + backgroundOffset) + viewport.getWorldWidth() * 0.5) {
			backgroundOffset -= 640 / PPM;
		}
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
	    grassBatch.setProjectionMatrix(camera.combined);
		grassBatch.begin();
		grassBatch.draw(grassRegion1, 670/PPM, 31.8f/PPM,17/PPM,5f/PPM);
		grassBatch.draw(grassRegion2, 700/PPM, 31.8f/PPM,17/PPM,5f/PPM);
		grassBatch.draw(grassRegion3, 640/PPM, 31.8f/PPM,17/PPM,5f/PPM);
		grassBatch.end();
	}

	public void loadBackgrounds(float x, int y) {
		backgroundBatch1.setProjectionMatrix(camera.combined);
		backgroundBatch1.begin();
		backgroundBatch1.draw(backgroundTexture1, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		backgroundBatch1.end();

		backgroundBatch2.setProjectionMatrix(camera.combined);
		backgroundBatch2.begin();
		backgroundBatch2.draw(backgroundTexture2, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		backgroundBatch2.end();

		backgroundBatch3.setProjectionMatrix(camera.combined);
		backgroundBatch3.begin();
		backgroundBatch3.draw(backgroundTexture3, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		backgroundBatch3.end();
		
	}

	public void resize(int width, int height) {
		camera.position.set(new Vector3(0, 0, 0)); // this vector3 object is temporary, as its values are passed to the
													// camera position and then it gets ready for garbage collection
		camera.update();
		viewport.update(width, height, true);
	}

	public void dispose() {
		backgroundBatch1.dispose();
		backgroundBatch2.dispose();
		backgroundBatch3.dispose();
		backgroundTexture1.dispose();
		backgroundTexture2.dispose();
		backgroundTexture3.dispose();
		map.dispose();
		tiledMapRenderer.dispose();
		grassAtlas.dispose();
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
}
