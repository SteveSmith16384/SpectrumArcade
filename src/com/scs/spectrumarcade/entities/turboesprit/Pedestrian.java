package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.MinerModel;

public class Pedestrian extends AbstractPhysicalEntity {
	
	//private Spatial model;
	
	public Pedestrian(SpectrumArcade _game, float x, float z) {
		super(_game, "Pedestrian");
		
		MinerModel model = new MinerModel(game.getAssetManager());

		this.mainNode.attachChild(model);
		this.mainNode.setLocalTranslation(x, 0f, z);

		srb = new RigidBodyControl(1f);
		model.addControl(srb);
		//srb.setKinematic(true);
		
		model.walkAnim();
	}

	
	@Override
	public void process(float tpfSecs) {
		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		Vector3f force = dir.mult(5);
		this.srb.setLinearVelocity(force);
	}

}
