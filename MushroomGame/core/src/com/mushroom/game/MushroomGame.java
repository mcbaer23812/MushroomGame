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
 * finish level 1
 * 
 * make background parallax and have it load in a less messy way
 * 
 * add mushroom spore mechanic
 * 
 * multiple levels (talk to shop guy to go to next level?), if next levels, then fix map renderer
 * to be abstract and allow drawing of textures per level instead of in a separate class to "mushroom game lvl x"
 * 
 * main menu
 * 
 */

public class MushroomGame extends ApplicationAdapter {
	// LEVELS
	private LevelSelector lvlSelector;
	// BOX2D
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	private MapPolygons mapPolygons;
	private WorldContactListener worldContactListener;
	// PLAYER
	private SpriteBatch playerBatch;
	private Player player;
	// SHOP
	private SpriteBatch shopBatch;
	private Shop shop;

	@Override
	public void create() {
		world = new World(new Vector2(0, -9.81f), false); // gravity vector here
		box2DDebugRenderer = new Box2DDebugRenderer();
		
		Vector2 playerPosition = new Vector2(1440.0f, 64.0f);
		playerBatch = new SpriteBatch();
		player = new Player(new Texture("images/red-shroom-idle.png"), new Texture("images/red-shroom-run.png"), world, playerPosition);
		lvlSelector = new LevelSelector(player);
		
		mapPolygons = new MapPolygons(world);
		mapPolygons.parseMapObjects(lvlSelector.getLevel().getMap().getLayers().get("objects").getObjects());
		
		Vector2 shopPosition = new Vector2(1440.0f, 48.0f);
		shopBatch = new SpriteBatch();
		shop = new Shop(new Texture("images/shop_anim.png"), world, player, shopPosition);
		
		worldContactListener = new WorldContactListener(player, shop);
		world.setContactListener(worldContactListener);
		
	}

	@Override
	public void render() {
		world.step(1 / 60.0f, 6, 2);
		Gdx.gl.glClearColor(0f,0f,0f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //prevents overdraw, clears different portions of the framebuffer
		
		// RGB(21/255,21/255,255/255) RGB = float*255 RGB/maxrgb = float
		player.update(Gdx.graphics.getDeltaTime());
		shop.update(Gdx.graphics.getDeltaTime());
		
		lvlSelector.getLevel().render();
		shopBatch.setProjectionMatrix(lvlSelector.getLevel().getCamera().combined);
		shopBatch.begin();
		shop.draw(shopBatch);
		shopBatch.end();
		
		playerBatch.setProjectionMatrix(lvlSelector.getLevel().getCamera().combined);
		playerBatch.begin();
		player.draw(playerBatch);
		playerBatch.end();
		
		world.clearForces();
		System.out.println("pos: " + player.getPosition());
//		System.out.println(player.getGrounded() + " " +  player.getBody().getFixtureList().get(1).getFriction() + " " + player.getBody().getLinearDamping());
//		box2DDebugRenderer.render(world, mapRenderer.getOrthoCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		lvlSelector.getLevel().resize(width, height);
	}

	@Override
	public void dispose() {
		box2DDebugRenderer.dispose();
		world.dispose();
		player.dispose();
		playerBatch.dispose();
		shop.dispose();
		shopBatch.dispose();
		Gdx.input.setInputProcessor(null);
	}
}