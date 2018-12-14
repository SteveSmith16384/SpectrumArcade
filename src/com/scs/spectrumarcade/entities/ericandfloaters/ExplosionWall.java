package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.MagFilter;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

public class ExplosionWall extends AbstractPhysicalEntity implements IProcessable {

	public ExplosionWall(SpectrumArcade _game, float xGrid, float zGrid) {
		super(_game, "ExplosionWall");

		Box box1 = new Box(EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f, EricAndTheFloatersLevel.SEGMENT_SIZE/2f);
		Geometry geometry = new Geometry("ExplosionWallGeom", box1);
		//geometry.setShadowMode(ShadowMode.CastAndReceive);
		Texture tex = JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/ericwall.png");
		tex.setMagFilter(MagFilter.Nearest);
		geometry.setLocalTranslation(EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2, EricAndTheFloatersLevel.SEGMENT_SIZE/2);
		//geometry.getMaterial().setParam(arg0, arg1, arg2);
		this.mainNode.attachChild(geometry);
		
		mainNode.setLocalTranslation(((xGrid)*EricAndTheFloatersLevel.SEGMENT_SIZE), 0, ((zGrid) * EricAndTheFloatersLevel.SEGMENT_SIZE));
		mainNode.updateModelBound();

	}

	@Override
	public void process(float tpfSecs) {
		// TODO Remove after a time
		
	}


}
