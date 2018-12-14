package com.scs.spectrumarcade.abilities;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.gauntlet.ThrowingAxe;

public class GauntletAxeThrower extends AbstractAbility implements IAbility, PhysicsTickListener {

	private static final long SHOT_INTERVAL = 1000;

	private ThrowingAxe axe;
	private long nextShotTime = 0;
	private boolean forceAdded = false;

	public GauntletAxeThrower(SpectrumArcade game) {
		super(game);
	}


	@Override
	public void activate() {
		if (nextShotTime < System.currentTimeMillis()) {
			//Globals.p("Throwing axe");
			nextShotTime = System.currentTimeMillis() + SHOT_INTERVAL;

			Vector3f pos = game.player.getMainNode().getWorldTranslation().clone();
			pos.y += Settings.PLAYER_HEIGHT;
			pos.addLocal(game.getCamera().getDirection().mult(2));

			axe = new ThrowingAxe(game, pos.x, pos.y, pos.z);
			game.addEntity(axe);

		}
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {

	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (!forceAdded) {
			forceAdded = true;
			Vector3f force = game.getCamera().getDirection().mult(20);
			axe.srb.setLinearVelocity(force); // todo - move to Axe code
			//Globals.p("Force=" + force);
		}

	}

}
