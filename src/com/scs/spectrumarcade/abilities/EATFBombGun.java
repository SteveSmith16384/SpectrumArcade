package com.scs.spectrumarcade.abilities;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.ericandfloaters.Bomb;

public class EATFBombGun extends AbstractAbility implements IAbility {

	private static final long SHOT_INTERVAL = 4000;

	private long nextShotTime = 0;

	public EATFBombGun(SpectrumArcade game) {
		super(game);
	}


	@Override
	public void activate() {
		if (nextShotTime < System.currentTimeMillis()) {
			Globals.p("Throwing bomb");
			nextShotTime = System.currentTimeMillis() + SHOT_INTERVAL;
			
			Vector3f pos = game.player.getMainNode().getWorldTranslation();
			pos.addLocal(game.getCamera().getDirection().mult(2));
			Bomb bomb = new Bomb(game, pos.x, pos.y, pos.z);
			bomb.srb.applyCentralForce(game.getCamera().getDirection().mult(4));
			game.addEntity(bomb);
		}
	}

}
