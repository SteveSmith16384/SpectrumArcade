package com.scs.spectrumarcade.abilities;

import java.awt.Point;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IEntity;
import com.scs.spectrumarcade.entities.ericandfloaters.Bomb_EATF;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

public class BombGun_EATF extends AbstractAbility implements IAbility {

	private static final long SHOT_INTERVAL = 4000;

	private long nextShotTime = 0;
	private Bomb_EATF bomb;

	public BombGun_EATF(SpectrumArcade game) {
		super(game);
	}


	@Override
	public void activate() {
		if (nextShotTime < System.currentTimeMillis()) {
			if (areaFreeOfBombs()) {
				Globals.p("Throwing bomb");
				nextShotTime = System.currentTimeMillis() + SHOT_INTERVAL;

				Vector3f pos = game.player.getMainNode().getWorldTranslation().clone();
				pos.y += Settings.PLAYER_HEIGHT;
				pos.addLocal(game.getCamera().getDirection().mult(2));
				bomb = new Bomb_EATF(game, pos.x, pos.y, pos.z, 3);
				game.addEntity(bomb);
			}
		}
	}


	private boolean areaFreeOfBombs() {
		Point p = this.game.player.getMapPos(EricAndTheFloatersLevel.SEGMENT_SIZE);
		for (IEntity e : game.entities) {
			if (e instanceof Bomb_EATF) {
				Bomb_EATF bomb = (Bomb_EATF)e;
				Point p2 = bomb.getMapPos(EricAndTheFloatersLevel.SEGMENT_SIZE);
				if (p.equals(p2)) {
					return false;
				}
			}
		}
		return true;

	}

}
