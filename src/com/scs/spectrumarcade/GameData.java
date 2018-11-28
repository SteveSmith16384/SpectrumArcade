package com.scs.spectrumarcade;

import java.util.HashMap;

import com.scs.spectrumarcade.levels.ILevelGenerator;

public class GameData {

	public int numKeys;
	private HashMap<Class<? extends ILevelGenerator>, Integer> currentLevel = new HashMap<>();

	public GameData() {
	}

	
	public int getLevelNum(Class<? extends ILevelGenerator> clazz) {
		while (!this.currentLevel.containsKey(clazz)) {
			this.currentLevel.put(clazz, 1);
		}
		return this.currentLevel.get(clazz);
	}


	public void setLevel(Class<? extends ILevelGenerator> clazz, int levelNum) {
		this.currentLevel.remove(clazz);
		this.currentLevel.put(clazz, levelNum);
	}
	
}
