package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.models.SimpleWomanModel;

public class SimpleWoman extends AbstractPhysicalEntity {
	
	private Spatial model;
	
	public SimpleWoman(SpectrumArcade _game, float x, float z) {
		super(_game, "SimpleWoman");
		
		model = new SimpleWomanModel(game.getAssetManager());
		//model.setLocalTranslation(x, 0, z);
		//floor_geo.rotate(0, (float)(SCPGame.rnd.nextFloat() * Math.PI * .1f), 0); // rotate random amount, and maybe scale slightly

		this.mainNode.attachChild(model);
		this.mainNode.setLocalTranslation(x, 0f, z);

		srb = new RigidBodyControl(1f);
		model.addControl(srb);
		game.bulletAppState.getPhysicsSpace().add(srb);
		srb.setKinematic(true);
	}

	
	@Override
	public void process(float tpfSecs) {
		this.getMainNode().lookAt(game.player.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
		
	}

	/*
	@Override
	public void remove() {
		this.mainNode.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.srb);
		
	}*/
}
