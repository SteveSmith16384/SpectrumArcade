package com.scs.spectrumarcade.entities.androids;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.AbstractSquareCover;

public class SSquare extends AbstractSquareCover implements INotifiedOfCollision {

	public SSquare(SpectrumArcade game, float x, float z) {
		super(game, x, z, 2f, "Textures/androids/s_square.png");
	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			// todo
		}
		
	}

}
