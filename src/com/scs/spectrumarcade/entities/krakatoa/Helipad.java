package com.scs.spectrumarcade.entities.krakatoa;

import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.AbstractSquareCover;

public class Helipad extends AbstractSquareCover implements INotifiedOfCollision {

	public Helipad(SpectrumArcade game, float x, float y, float z) {
		super(game, x, y, z, 4f, "Textures/krakatoa/helipadasph.jpg");
	}

	
	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			// todo - refuel
		}
		
	}

}
