package com.scs.spectrumarcade.entities;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public abstract class AbstractSquareCover extends AbstractPhysicalEntity {

	public AbstractSquareCover(SpectrumArcade _game, float x, float z, String tex) {
		super(_game, "AbstractSquareCover");

		Box box1 = new Box(.5f, .01f, .5f);
		Geometry geometry = new Geometry("AbstractSquareCoverBox", box1);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, tex);

		this.mainNode.attachChild(geometry);
		geometry.setLocalTranslation(.5f, 0, .5f);
		mainNode.updateModelBound();
		this.mainNode.setLocalTranslation(x, 0.01f, z);

	}

}
