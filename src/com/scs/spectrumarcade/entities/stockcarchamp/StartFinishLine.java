package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class StartFinishLine extends AbstractPhysicalEntity {

	public StartFinishLine(SpectrumArcade _game, float x, float z) {
		super(_game, "StartFinishLine");

		Box box1 = new Box(.5f, .01f, .5f);
		Geometry geometry = new Geometry("StartFinishLineBox", box1);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/stockcarchamp/startfinish.png");

		this.mainNode.attachChild(geometry);
		geometry.setLocalTranslation(.5f, 0, .5f);
		mainNode.updateModelBound();
		this.mainNode.setLocalTranslation(x, 0.01f, z);

	}

}
