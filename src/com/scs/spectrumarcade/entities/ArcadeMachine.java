package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.levels.ArcadeRoom;
import com.scs.spectrumarcade.levels.ILevelGenerator;
import com.scs.spectrumarcade.models.ArcadeMachineModel;

public class ArcadeMachine extends AbstractPhysicalEntity implements INotifiedOfCollision {

	private ArcadeRoom room;
	private Class<? extends ILevelGenerator> level;
	
	public ArcadeMachine(SpectrumArcade _game, ArcadeRoom _room, float x, float y, float z, String folder, Class<? extends ILevelGenerator> _level) {
		super(_game, "ArcadeMachine");

		room = _room;
		level = _level;
		
		Node geometry = new ArcadeMachineModel(_game.getAssetManager(), folder);
		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		//srb.setKinematic(true);

	}

	
	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			try {
				room.lastPos = game.player.getMainNode().getWorldTranslation();
				game.setNextLevel(level, game.gameData.getLevelNum(level));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	
}
	