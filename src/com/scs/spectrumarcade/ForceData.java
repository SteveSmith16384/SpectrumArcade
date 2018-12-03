package com.scs.spectrumarcade;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public class ForceData {
	
	public static final int CENTRAL_FORCE = 1;
	public static final int LINEAR_VELOCITY = 2;
	
	public AbstractPhysicalEntity pe;
	public int type;
	public Vector3f force;

	public ForceData(AbstractPhysicalEntity _pe, int _type, Vector3f _force) {
		pe = _pe;
		type = _type;
		force = _force;
	}

}
