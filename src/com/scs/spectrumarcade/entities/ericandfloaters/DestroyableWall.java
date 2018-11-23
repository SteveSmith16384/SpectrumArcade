package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

import ssmith.lang.NumberFunctions;

public class DestroyableWall extends AbstractPhysicalEntity {

	public DestroyableWall(SpectrumArcade _game, float xGrid, float zGrid) {
		super(_game, "DestroyableWall");

		Box box1 = new Box(EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f);
		Geometry geometry = new Geometry("DestroyableWall", box1);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericwall.png");
		geometry.setLocalTranslation(EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2);
		//geometry.getMaterial().setParam(arg0, arg1, arg2);
		this.mainNode.attachChild(geometry);
		
		//mainNode.setLocalTranslation(((xGrid+1)*EricAndTheFloatersLevel.SEGMENT_SIZE)-1, 0, ((zGrid+1) * EricAndTheFloatersLevel.SEGMENT_SIZE)-1);
		mainNode.setLocalTranslation(((xGrid)*EricAndTheFloatersLevel.SEGMENT_SIZE), 0, ((zGrid) * EricAndTheFloatersLevel.SEGMENT_SIZE));
		mainNode.updateModelBound();

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);
		
		//this.mainNode.getWorldBound();
		
	}


}
