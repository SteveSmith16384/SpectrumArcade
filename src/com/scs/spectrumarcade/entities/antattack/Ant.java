package com.scs.spectrumarcade.entities.antattack;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.AntModel;

public class Ant extends AbstractPhysicalEntity implements ICausesHarmOnContact {

	public static final float START_HEALTH = 5f;

	public Ant(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Ant");

		Node geometry = new AntModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		geometry.addControl(srb);
		game.bulletAppState.getPhysicsSpace().add(srb);
		//srb.setKinematic(true);

	}

	
	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void process(float tpfSecs) {
		// TODO Auto-generated method stub
		
	}

}
