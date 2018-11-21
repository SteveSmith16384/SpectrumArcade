package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.RaceCarModel;

public class ParkedCar extends AbstractPhysicalEntity {

	public ParkedCar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "ParkedCar");

		Spatial geometry = new RaceCarModel(game.getAssetManager());

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
	
	}

	
	@Override
	public void process(float tpfSecs) {
		// Do nothing

	}


}
