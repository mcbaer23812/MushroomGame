package com.mushroom.game;

import java.util.HashMap;

public class LevelSelector {
	private LevelOne lvlOne;
	private Player player;
	HashMap<Float, Level> levels;
	
	public LevelSelector(Player player) {
		this.player = player;
		lvlOne = new LevelOne(player);
		levels = new HashMap<Float, Level>();
		levels.put(1.0f, lvlOne);
		
	}
	
	public void renderLevel() {
		levels.get(player.getCurrentLevel()).render();
		
	}
	
	public Level getLevel() {
		return levels.get(player.getCurrentLevel());
	}

}
