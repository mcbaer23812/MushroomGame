package com.mushroom.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;

public interface Level {
	public void render();
	public TiledMap getMap();
	public OrthographicCamera getCamera();
	public void resize(int width, int height);
}
