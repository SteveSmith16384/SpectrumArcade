package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.levels.ILevelGenerator;
import com.scs.spectrumarcade.models.ArcadeMachineModel;

public class ArcadeMachine extends AbstractPhysicalEntity implements INotifiedOfCollision {

	public static final float START_HEALTH = 5f;

	private Class<? extends ILevelGenerator> level;
	
	public ArcadeMachine(SpectrumArcade _game, float x, float y, float z, Class<? extends ILevelGenerator> _level) {
		super(_game, "ArcadeMachine");

		level = _level;
		
		Node geometry = new ArcadeMachineModel(_game.getAssetManager());
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		//game.bulletAppState.getPhysicsSpace().add(srb);
		srb.setKinematic(true);

	}

	@Override
	public void process(float tpfSecs) {
		// Do nothing
		
	}

	@Override
	public void notifiedOfCollision(IEntity collidedWith) {
		if (collidedWith == game.player) {
			try {
				ILevelGenerator object = level.newInstance();
				this.game.startNewLevel(object);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	
}
	