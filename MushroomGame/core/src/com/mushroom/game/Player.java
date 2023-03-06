package com.mushroom.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
	private Texture idleSpriteSheet;
	private Texture runningSpriteSheet;
	private TextureRegion[] frames;
	private Animation<TextureRegion> animation;

	private Vector2 footPosition;
	private Vector2 position;
	private Boolean walking;
	private Boolean running;
	private Boolean facingLeft;
	private Boolean jumping;
	public Boolean grounded;
	private float stateTime;

	private float landingTime = -1f;
	private float PPM = 100f; // Pixels Per Meter
	private World world;
	private Body body;

	public Player(Texture idleSpriteSheet, Texture runningSpriteSheet, World world) {
		this.world = world;
		this.idleSpriteSheet = idleSpriteSheet;
		this.runningSpriteSheet = runningSpriteSheet;
		frames = new TextureRegion[0];
		animation = new Animation<TextureRegion>(0.1f, frames);
		position = new Vector2(640, 50);
		footPosition = new Vector2(0 , -0.15f);
		jumping = false;
		grounded = true;
		facingLeft = false;
		running = false;
		walking = false;
		pickFrames();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(position.x / PPM, position.y / PPM);
		body = this.world.createBody(bodyDef);
		body.setFixedRotation(true);
		body.setLinearDamping(2f);

		PolygonShape capShape = new PolygonShape();
		capShape.setAsBox((frames[0].getRegionWidth() / 2.3f) / PPM, (frames[0].getRegionHeight() / 3.7f) / PPM);
		FixtureDef capFixtureDef = new FixtureDef();
		capFixtureDef.shape = capShape;
		capFixtureDef.density = 1.0f;
		capFixtureDef.friction = 0.1f; // 0.1f
		body.createFixture(capFixtureDef);
		capShape.dispose();

		PolygonShape bodyShape = new PolygonShape();
		bodyShape.setAsBox((frames[0].getRegionWidth() / 4) / PPM, (frames[0].getRegionHeight() / 2.25f) / PPM);
		FixtureDef bodyFixtureDef = new FixtureDef();
		bodyFixtureDef.shape = bodyShape;
		bodyFixtureDef.density = 1.0f;
		bodyFixtureDef.friction = 2.5f; // 2.5f
		body.createFixture(bodyFixtureDef);
		bodyShape.dispose();

		PolygonShape footSensor = new PolygonShape();
		footSensor.setAsBox((frames[0].getRegionWidth() / 6) / PPM, (frames[0].getRegionHeight() / 16) / PPM,
				footPosition, 0);
		FixtureDef footSensorFixtureDef = new FixtureDef();
		footSensorFixtureDef.shape = footSensor;
		footSensorFixtureDef.isSensor = true;
		Fixture footFixture = body.createFixture(footSensorFixtureDef);
		footFixture.setUserData("footSensor");
		footSensor.dispose();
	}

	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
				runLeft();
			} else {
				walkLeft();
			}
		} else if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
				runRight();
			} else {
				walkRight();
			}
		} else {
			idle();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && Math.abs(body.getLinearVelocity().y) < 0.01f) {
			jump();
		}

		friction();

		position.set(body.getPosition().scl(PPM));
		stateTime += delta;
		pickFrames();
	}

	public void friction() {
		if (jumping) {
			body.getFixtureList().get(1).setFriction(0f);
		} else {
			body.getFixtureList().get(1).setFriction(2.5f);
		}
	}

	public void idle() {
		running = false;
		walking = false;
	}

	public void jump() {
		body.applyLinearImpulse(0, 35 / PPM, body.getWorldCenter().x, body.getWorldCenter().y, true);
		running = true;
		jumping = true;
	}

	public void walkLeft() {
		if (Math.abs(body.getLinearVelocity().x) < 1.200f) {
			if (Math.abs(body.getLinearVelocity().x) < 0.8f) { // faster initial acceleration
				body.applyLinearImpulse(-2.8f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			} else {
				body.applyLinearImpulse(-1.4f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			}
			running = false;
			walking = true;
			facingLeft = true;
		}
	}

	public void runLeft() {
		if (Math.abs(body.getLinearVelocity().x) < 2.100f) {
			body.applyLinearImpulse(-2.0f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			running = true;
			walking = false;
			facingLeft = true;
		}
	}

	public void walkRight() {
		if (Math.abs(body.getLinearVelocity().x) < 1.200f) {
			if (Math.abs(body.getLinearVelocity().x) < 0.8) { // faster initial acceleration
				body.applyLinearImpulse(2.8f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			} else {
				body.applyLinearImpulse(1.4f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			}
			running = false;
			walking = true;
			facingLeft = false;
		}
	}

	public void runRight() {
		if (Math.abs(body.getLinearVelocity().x) < 2.100f) {
			body.applyLinearImpulse(2.0f / PPM, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
			running = true;
			walking = false;
			facingLeft = false;
		} else {

		}
	}

	public void pickFrames() {
		TextureRegion[][] textureRegions = null;
		float animationDuration = 0.25f;
		if (running == true) {
			textureRegions = TextureRegion.split(runningSpriteSheet, 32, 32);
			animationDuration = 0.1f;
		} else if (walking == true) {
			textureRegions = TextureRegion.split(runningSpriteSheet, 32, 32); // load frames for walking animation
		} else {
			textureRegions = TextureRegion.split(idleSpriteSheet, 32, 32); // load frames for idle animation
		}
		frames = new TextureRegion[2];
		for (int i = 0; i < 2; i++) {
			frames[i] = textureRegions[0][i];
		}
		animation = new Animation<TextureRegion>(animationDuration, frames);
	}

	public void draw(SpriteBatch batch) {
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		float textureWidth = currentFrame.getRegionWidth() / PPM; // scaled texture width
		float textureHeight = currentFrame.getRegionHeight() / PPM; // scaled texture height
		float drawX = body.getPosition().x - textureWidth / 2; // anchor the texture to the body's center
		float drawY = body.getPosition().y - textureHeight / 2;
		if (facingLeft) {
			batch.draw(currentFrame, drawX + textureWidth, drawY, -textureWidth, textureHeight);
		} else {
			batch.draw(currentFrame, drawX, drawY, textureWidth, textureHeight);
		}

	}

	public void dispose() {
		idleSpriteSheet.dispose();
		runningSpriteSheet.dispose();
		world.dispose();
	}
	
	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public void setBodyPosition(Vector2 position) {
		this.body.setTransform(position.scl(1f / PPM), 0f);
	}
	
	public boolean getGrounded() {
		return grounded;
	}

	public Vector2 getPosition() {
		return body.getPosition().scl(PPM);
	}

	public Body getBody() {
		return body;
	}

}