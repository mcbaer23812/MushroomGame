package com.mushroom.game;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class MapPolygons {
	private World world;
    private float PPM = 100f; // Pixels Per Meter
	
	public MapPolygons(World world) {
		this.world = world;
	}
	
	public void parseMapObjects(MapObjects mapObjects) {
		for(MapObject mapObject: mapObjects) {
			
			if(mapObject instanceof PolygonMapObject) {
				createStaticBody((PolygonMapObject)mapObject);
			}
		}
	}
	
	public void createStaticBody(PolygonMapObject polygonMapObject) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		Shape shape = createPolygonShape(polygonMapObject);
		Fixture fixture = body.createFixture(shape, 0 /*1000*/);
		fixture.setUserData("mapObject");
		shape.dispose();
	}
	
	
	private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
		float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length/2];
		Vector2 current = new Vector2();
		for(int i = 0; i < vertices.length/2 ; i++) {
			current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
			worldVertices[i] = current;
		}
		PolygonShape shape = new PolygonShape();
		shape.set(worldVertices);
		return shape;
	}
	
}
