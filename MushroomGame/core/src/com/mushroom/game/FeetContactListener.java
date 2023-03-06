package com.mushroom.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class FeetContactListener implements ContactListener {
	private boolean isGrounded;

	private boolean isFootSensor(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("footSensor");
	}

	private boolean isMapObject(Fixture fixture) {
		Object userData = fixture.getUserData();
		return userData != null && userData.equals("mapObject");
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		if (isFootSensor(fixtureA) && isMapObject(fixtureB)) {
			isGrounded = true;
		} else if (isFootSensor(fixtureB) && isMapObject(fixtureA)) {
			isGrounded = true;
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if (isFootSensor(fixtureA) && isMapObject(fixtureB)) {
			isGrounded = false;
		} else if (isFootSensor(fixtureB) && isMapObject(fixtureA)) {
			isGrounded = false;
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	public boolean getGrounded() {
		return isGrounded;
	}

}
