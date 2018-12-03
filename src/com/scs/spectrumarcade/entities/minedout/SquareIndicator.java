package com.scs.spectrumarcade.entities.minedout;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class SquareIndicator extends AbstractPhysicalEntity  implements IProcessable {

	public SquareIndicator(SpectrumArcade _game) {
		super(_game, "SquareIndicator");

		Box box1 = new Box(.5f, .01f, .5f);
		Geometry geometry = new Geometry("BombSphere", box1);
		//geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg");

		this.mainNode.attachChild(geometry);
		geometry.setLocalTranslation(.5f, 0, .5f);
		mainNode.updateModelBound();

	}


	@Override
	public void process(float tpfSecs) {
		Vector3f pos = game.player.getMainNode().getWorldTranslation();
		int x = (int)pos.x;
		int z = (int)pos.z;
		
		this.mainNode.setLocalTranslation(x, 1.01f, z);
	}


}
