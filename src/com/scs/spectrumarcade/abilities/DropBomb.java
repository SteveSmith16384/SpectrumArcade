package com.scs.spectrumarcade.abilities;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.ericandfloaters.Bomb;

public class DropBomb extends AbstractAbility implements IAbility {

	public DropBomb(SpectrumArcade game) {
		super(game);
	}
	

	@Override
	public void acivate() {
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		pos.addLocal(game.getCamera().getDirection().mult(2));
		Bomb bomb = new Bomb(game, pos.x, pos.y, pos.z);
		bomb.srb.applyCentralForce(game.getCamera().getDirection().mult(2));
		game.addEntity(bomb);
		
	}

}
