package com.scs.spectrumarcade.entities.motos;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.levels.MotosLevel;

public class MotosSuperHeavyEnemy extends AbstractMotosEnemyBall {

	public MotosSuperHeavyEnemy(SpectrumArcade _game, MotosLevel level, float x, float z) {
		super(_game, level, "MotosSuperHeavyEnemy", x, z, 1.4f, 2f, 1.4f);

	}

}
