package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.models.ArcadeMachineModel;

public class ArcadeMachine extends AbstractPhysicalEntity implements INotifiedOfCollision {

	public static final float START_HEALTH = 5f;

	public ArcadeMachine(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "ArcadeMachine");

		Node geometry = new ArcadeMachineModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		geometry.addControl(srb);
		game.bulletAppState.getPhysicsSpace().add(srb);
		srb.setKinematic(true);

	}

	@Override
	public void process(float tpfSecs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		// TODO Auto-generated method stub
		
	}

	
}
	