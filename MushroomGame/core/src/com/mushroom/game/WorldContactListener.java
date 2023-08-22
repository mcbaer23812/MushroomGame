package com.mushroom.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
	private Player player;
	private Shop shop;

	public WorldContactListener(Player player, Shop shop) {
		this.player = player;
		this.shop = shop;
	}

	private boolean isFootSensor(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("footSensor");
	}

	private boolean isMapObject(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("mapObject");
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
		if (isFootSensor(fixtureA) && isMapObject(fixtureB)) {
			player.setGrounded(true);
		} else if (isFootSensor(fixtureB) && isMapObject(fixtureA)) {
			player.setGrounded(true);
		} else if(isBodyFixture(fixtureA) && isShopFixture(fixtureB)) {
			shop.setTouching(true);
		} else if(isShopFixture(fixtureA) && isBodyFixture(fixtureB)) {
			shop.setTouching(true);
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		if (isFootSensor(fixtureA) && isMapObject(fixtureB)) {
			player.setGrounded(false);
		} else if (isFootSensor(fixtureB) && isMapObject(fixtureA)) {
			player.setGrounded(false);
		} else if(isBodyFixture(fixtureA) && isShopFixture(fixtureB)) {
			shop.setTouching(false);
		} else if(isShopFixture(fixtureA) && isBodyFixture(fixtureB)) {
			shop.setTouching(false);
		}		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		contact.resetFriction();
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
}
