package com.scs.spectrumarcade.entities.androids;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.AbstractSquareCover;

public class ExitSquare extends AbstractSquareCover implements INotifiedOfCollision {

	public ExitSquare(SpectrumArcade game, float x, float z) {
		super(game, x, 0, z, 2f, "Textures/androids/exit.png");
	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			// todo
		}
		
	}

}
