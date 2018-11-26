package com.scs.spectrumarcade.components;

import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public interface INotifiedOfCollision {

	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith);

}
