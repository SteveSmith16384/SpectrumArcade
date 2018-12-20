package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

public class ExplosionWall extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision { // todo - use ghost control

	private long removeTime;
	//private boolean checkedForCollisions = false;

	public ExplosionWall(SpectrumArcade _game, float xGrid, float zGrid) {
		super(_game, "ExplosionWall");

		Box box1 = new Box(EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f);
		Geometry geometry = new Geometry("ExplosionWallGeom", box1);
		Texture tex = JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/smoke.png", true);
		tex.setMagFilter(MagFilter.Nearest);
		geometry.setLocalTranslation(EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2);
		//geometry.getMaterial().setParam(arg0, arg1, arg2);
		this.mainNode.attachChild(geometry);

		mainNode.setLocalTranslation(((xGrid)*EricAndTheFloatersLevel.SEGMENT_SIZE), 0, ((zGrid) * EricAndTheFloatersLevel.SEGMENT_SIZE));
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		
		removeTime = System.currentTimeMillis() + 2000;

	}

	@Override
	public void process(float tpfSecs) {
		if (removeTime < System.currentTimeMillis()) {
			this.markForRemoval();
		}

	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith instanceof Floater) {
			collidedWith.markForRemoval();
		} else if (collidedWith instanceof IAvatar) {
			//todo - re-add game.playerKilled("Own bomb");
		}
		
	}


}
