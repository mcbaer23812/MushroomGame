package com.mushroom.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class MapRenderer {
	// BACKGROUNDS
	private SpriteBatch batch1;
	private SpriteBatch batch2;
	private SpriteBatch batch3;
	private Texture backgroundTexture1;
	private Texture backgroundTexture2;
	private Texture backgroundTexture3;
	private Texture grassTexture1;
	private Texture grassTexture2;
	private Texture grassTexture3;
	private Texture combinedTexture;
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
		batch1 = new SpriteBatch();
		batch2 = new SpriteBatch();
		batch3 = new SpriteBatch();
		backgroundTexture1 = new Texture("images/background_layer_1.png");
		backgroundTexture2 = new Texture("images/background_layer_2.png");
		backgroundTexture3 = new Texture("images/background_layer_3.png");
		grassTexture1 = new Texture("images/grass_1.png");
		grassTexture2 = new Texture("images/grass_2.png");
		grassTexture3 = new Texture("images/grass_3.png");
		// CAMERA & VEWIPORT
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(640 /PPM, 320 /PPM, camera);
		// MAP & MAP RENDERER
		map = new TmxMapLoader().load("tilesets/OakWoodsTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1/PPM);
	}

	public void renderMap(Vector2 playerPosition) {
		camera.update();
		camera.position.set(playerPosition.x, viewport.getWorldHeight()/2, 0);
		loadBackgrounds(0 + backgroundOffset, 0);
		loadBackgrounds(viewport.getWorldWidth() + backgroundOffset, 0);
		if (playerPosition.x - backgroundOffset >= viewport.getWorldWidth() * 1.5) {
			backgroundOffset += 640 /PPM;
		} else if (playerPosition.x <= (0 + backgroundOffset) + viewport.getWorldWidth() * 0.5) {
			backgroundOffset -= 640 /PPM;
		}

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	public void loadBackgrounds(float x, int y) {
		batch1.setProjectionMatrix(camera.combined);
		batch1.begin();
		batch1.draw(backgroundTexture1, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch1.end();

		batch2.setProjectionMatrix(camera.combined);
		batch2.begin();
		batch2.draw(backgroundTexture2, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch2.end();

		batch3.setProjectionMatrix(camera.combined);
		batch3.begin();
		batch3.draw(backgroundTexture3, x, y, viewport.getWorldWidth(), viewport.getWorldHeight());
		batch3.end();
	}

	public void resize(int width, int height) {
		camera.position.set(new Vector3(0, 0, 0)); // this vector3 object is temporary, as its values are passed to the
													// camera position and then it gets ready for garbage collection
		camera.update();
		viewport.update(width, height, true);
	}

	public void dispose() {
		batch1.dispose();
		batch2.dispose();
		batch3.dispose();
		backgroundTexture1.dispose();
		backgroundTexture2.dispose();
		backgroundTexture3.dispose();
		map.dispose();
		tiledMapRenderer.dispose();
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
