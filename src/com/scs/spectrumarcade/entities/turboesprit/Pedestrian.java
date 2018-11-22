package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.MinerModel;

public class Pedestrian extends AbstractPhysicalEntity implements IProcessable  {
	
	public Pedestrian(SpectrumArcade _game, float x, float z) {
		super(_game, "Pedestrian");
		
		MinerModel model = new MinerModel(game.getAssetManager());
		this.mainNode.attachChild(model);
		this.mainNode.setLocalTranslation(x, 1f, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(10f);
		mainNode.addControl(srb);
		
		model.walkAnim();
	}

	
	@Override
	public void process(float tpfSecs) {
		Vector3f dir = this.getMainNode().getLocalRotation().getRotationColumn(2);
		dir.y = 0;
		Vector3f force = dir.mult(2);
		this.srb.setLinearVelocity(force);
		//Globals.p("Pedestrian pos: " + this.getMainNode().getWorldTranslation());

	}

}
