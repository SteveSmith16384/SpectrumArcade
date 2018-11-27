package com.scs.spectrumarcade.entities.motos;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.levels.MotosLevel;

public class MotosHeavyEnemy extends AbstractMotosEnemyBall {

	public MotosHeavyEnemy(SpectrumArcade _game, MotosLevel level, float x, float z) {
		super(_game, level, "MotosHeavyEnemy", x, z, 1.2f, 1f, 1.2f);

	}

}
