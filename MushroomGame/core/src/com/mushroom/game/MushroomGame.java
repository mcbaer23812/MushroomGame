package com.mushroom.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/*
 * ROADMAP
 * finish map
 * fix jumping physics (DONE)
 * add mushroom spore mechanic
 * multiple levels (talk to shop guy to go to next level?)
 * main menu
 */

public class MushroomGame extends ApplicationAdapter {
	// MAP
	private MapRenderer mapRenderer;
	// BOX2D
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	private MapPolygons mapPolygons;
	private FeetContactListener feetContactListener;
	// PLAYER
	private SpriteBatch playerBatch;
	private Player player;
	// SHOP
	private SpriteBatch shopBatch;
	private Shop shop;

	private static final float PPM = 100f;

	@Override
	public void create() {
		mapRenderer = new MapRenderer();
		world = new World(new Vector2(0, -9.81f), false); // gravity vector here
		box2DDebugRenderer = new Box2DDebugRenderer();
		mapPolygons = new MapPolygons(world);
		playerBatch = new SpriteBatch();
		player = new Player(new Texture("images/red-shroom-idle.png"), new Texture("images/red-shroom-run.png"), world);
		shopBatch = new SpriteBatch();
		shop = new Shop(new Texture("images/shop_anim.png"), world, player);
		shop.setHitbox(new Vector2(1710.0f, 64.0f));
		mapPolygons.parseMapObjects(mapRenderer.getTiledMap().getLayers().get("objects").getObjects());
		feetContactListener = new FeetContactListener(player);
		world.setContactListener(feetContactListener);
		world.setContactListener(shop);
	}

	@Override
	public void render() {
		world.step(1 / 60f, 6, 2);
		Gdx.gl.glClearColor(0.53f, 0.81f, 0.92f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// RGB(21/255,21/255,255/255) RGB = float*255 RGB/maxrgb = float
		player.update(Gdx.graphics.getDeltaTime());
		mapRenderer.renderMap(player.getPosition().scl(1f / PPM));
		
		shop.update(Gdx.graphics.getDeltaTime());
		shopBatch.setProjectionMatrix(mapRenderer.getOrthoCamera().combined);
		shopBatch.begin();
		shop.draw(shopBatch);
		shopBatch.end();
		
		playerBatch.setProjectionMatrix(mapRenderer.getOrthoCamera().combined);
		playerBatch.begin();
		player.draw(playerBatch);
		playerBatch.end();
		
		world.clearForces();
		System.out.println("pos: " + player.getPosition());
//		System.out.println(shop.getTouching());
//		System.out.println(player.getGrounded() + " " +  player.getBody().getFixtureList().get(1).getFriction() + " " + player.getBody().getLinearDamping());
		box2DDebugRenderer.render(world, mapRenderer.getOrthoCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		mapRenderer.resize(width, height);
	}

	@Override
	public void dispose() {
		mapRenderer.dispose();
		box2DDebugRenderer.dispose();
		world.dispose();
		player.dispose();
		playerBatch.dispose();
		shop.dispose();
		shopBatch.dispose();
		Gdx.input.setInputProcessor(null);
	}
}