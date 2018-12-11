package com.scs.spectrumarcade.entities.androids;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.AbstractSquareCover;

public class GSquare extends AbstractSquareCover implements INotifiedOfCollision {

	public GSquare(SpectrumArcade game, float x, float z) {
		super(game, x, z, 2f, "Textures/androids/g_square.png");
	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			// todo
		}
		
	}

}
