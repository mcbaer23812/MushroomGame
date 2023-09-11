package com.mushroom.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class LevelOne implements Level{
	private Player player;
	// CAMERA & VIEWPORT
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private MapAssets mapAssets;
	private SpriteBatch batch;
	private SpriteBatch backgroundBatch;
	
	private static final float PPM = 100.0f;
	
	public LevelOne(Player player) {
		this.player = player;
		mapAssets = new MapAssets();
		batch = new SpriteBatch();
		backgroundBatch = new SpriteBatch();
		
		// CAMERA & VEWIPORT
		camera = mapAssets.getOrthoCamera();
		viewport = mapAssets.getViewport();
		
		// MAP
		map = new TmxMapLoader().load("tilesets/OakWoodsTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
		
	}
	
	@Override
	public void render() {
		updateCamera(player.getPosition().scl(1.0f/PPM));
		backgroundBatch.setProjectionMatrix(camera.combined);
		backgroundBatch.begin();
		mapAssets.drawBackgrounds(6, backgroundBatch);
		backgroundBatch.end();
		renderTileMap();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mapAssets.drawFenceOne(batch, 435.0f, 24.0f);
		mapAssets.drawGrassOne(batch, 740.0f, 96.0f);
		mapAssets.drawGrassOne(batch, 245.0f, 190.0f);
		mapAssets.drawGrassOne(batch, 540.0f, 24.0f);
		mapAssets.drawGrassOne(batch, 310.0f, 47.8f);
		mapAssets.drawGrassOne(batch, 1510.0f, 64.0f);
		mapAssets.drawGrassOne(batch, 1680.0f, 264.0f);
		mapAssets.drawGrassOne(batch, 1656.0f, 192.0f);
		mapAssets.drawGrassTwo(batch, 776.0f, 96.0f);
		mapAssets.drawGrassTwo(batch, 890.0f, 96.0f);
		mapAssets.drawGrassTwo(batch, 450.0f, 24.0f);
		mapAssets.drawGrassTwo(batch, 950.0f, 48.0f);
		mapAssets.drawGrassTwo(batch, 1632.0f, 120.0f);
		mapAssets.drawGrassThree(batch, 510.0f, 24.0f);
		mapAssets.drawGrassThree(batch, 755.0f, 96.0f);
		mapAssets.drawGrassThree(batch, 980.0f, 48.0f);
		mapAssets.drawGrassThree(batch, 1584.0f, 48.0f);
		mapAssets.drawGrassThree(batch, 1608.0f, 72.0f);
		mapAssets.drawRockOne(batch, 340.0f, 24.0f);
		mapAssets.drawRockOne(batch, 1320.0f, 48.0f);
		mapAssets.drawRockTwo(batch, 825.0f, 96.0f);
		mapAssets.drawRockTwo(batch, 1120.0f, 48.0f);
		mapAssets.drawRockThree(batch, 1030.0f, 48.0f);
		mapAssets.drawLamp(batch, 400.0f, 24.0f);
		mapAssets.drawLamp(batch, 550.0f, 24.0f);
		mapAssets.drawLamp(batch, 1230.0f, 48.0f);
		mapAssets.drawFenceTwo(batch, 1360.0f, 48.0f);
		mapAssets.drawSign(batch, 700.0f, 96.0f);
		batch.end();
	}
	
	public void renderTileMap() {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	
	public void updateCamera(Vector2 playerPosition) {
		camera.update();
		camera.position.set(playerPosition.x, viewport.getWorldHeight() / 2, 0);
	}
	
	public void resize(int width, int height) {
		camera.position.set(new Vector3(0, 0, 0)); // this vector3 object is temporary, as its values are passed to the
													// camera position and then it gets ready for garbage collection
		camera.update();
		viewport.update(width, height, true);
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void dispose() {
		map.dispose();
		tiledMapRenderer.dispose();
	}
	
}
