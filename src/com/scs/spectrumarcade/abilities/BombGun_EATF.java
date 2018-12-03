package com.scs.spectrumarcade.abilities;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.ForceData;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.antattack.Bomb_AA;
import com.scs.spectrumarcade.entities.ericandfloaters.Bomb_EATF;

public class BombGun_EATF extends AbstractAbility implements IAbility {

	private static final long SHOT_INTERVAL = 4000;

	private long nextShotTime = 0;

	public BombGun_EATF(SpectrumArcade game) {
		super(game);
	}


	@Override
	public void activate() {
		if (nextShotTime < System.currentTimeMillis()) {
			Globals.p("Throwing bomb");
			nextShotTime = System.currentTimeMillis() + SHOT_INTERVAL;
			
			Vector3f pos = game.player.getMainNode().getWorldTranslation().clone();
			pos.y += Settings.PLAYER_HEIGHT;
			pos.addLocal(game.getCamera().getDirection().mult(2));
			Bomb_EATF bomb = new Bomb_EATF(game, pos.x, pos.y, pos.z);
			game.addEntity(bomb);

			Vector3f force = game.getCamera().getDirection().mult(10);
			//bomb.srb.setLinearVelocity(force);
			game.addForce(bomb, ForceData.LINEAR_VELOCITY, force);
			Globals.p("Force=" + force);

		}
	}

}
