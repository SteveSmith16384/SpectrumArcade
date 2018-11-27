package com.scs.spectrumarcade.entities.motos;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.levels.MotosLevel;

public class MotosSimpleEnemy extends AbstractMotosEnemyBall {

	public MotosSimpleEnemy(SpectrumArcade _game, MotosLevel level, float x, float z) {
		super(_game, level, "SimpleEnemy", x, z, 1f, .5f, 1);
		
	}


}