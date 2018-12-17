package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.models.SimpleManModel;

public class SimpleMan extends AbstractPhysicalEntity implements IProcessable  { // todo - remove?
	
	private Spatial model;
	
	public SimpleMan(SpectrumArcade _game, float x, float z) {
		super(_game, "SimpleMan");
		
		model = new SimpleManModel(game.getAssetManager());
		//model.setLocalTranslation(x, 0, z);
		//floor_geo.rotate(0, (float)(SCPGame.rnd.nextFloat() * Math.PI * .1f), 0); // rotate random amount, and maybe scale slightly

		this.mainNode.attachChild(model);
		this.mainNode.setLocalTranslation(x, 0f, z);

		srb = new RigidBodyControl(1f);
		model.addControl(srb);
		srb.setKinematic(true);
	}

	
	@Override
	public void process(float tpfSecs) {
		//this.getMainNode().lookAt(game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
		
	}

}
