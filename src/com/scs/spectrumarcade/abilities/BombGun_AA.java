package com.scs.spectrumarcade.abilities;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.ForceData;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.antattack.Bomb_AA;

public class BombGun_AA extends AbstractAbility implements IAbility, PhysicsTickListener {

	private static final long SHOT_INTERVAL = 4000;

	private long nextShotTime = 0;
	private Bomb_AA bomb;

	public BombGun_AA(SpectrumArcade game) {
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
			bomb = new Bomb_AA(game, pos.x, pos.y, pos.z);
			game.addEntity(bomb);

		}
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (bomb != null) {
			Vector3f force = game.getCamera().getDirection().mult(10);
			bomb.srb.setLinearVelocity(force);
			//game.addForce(bomb, ForceData.LINEAR_VELOCITY, force);
			Globals.p("Force=" + force);

			bomb = null;
		}
		
	}

}
