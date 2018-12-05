package com.scs.spectrumarcade.entities;

import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;

public class TextBillboardEntity extends AbstractPhysicalEntity implements IProcessable {

	private TextBillboard ab;
	
	public TextBillboardEntity(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "TextBillboardEntity");

		ab = new TextBillboard(game.getAssetManager(), 2f, .5f, 200, 30);
		ab.setLocalTranslation(0, 0, 0);
		this.mainNode.attachChild(ab);

		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();
	}


	@Override
	public void process(float tpfSecs) {
		ab.lookAt(game.getCamera().getLocation(), Vector3f.UNIT_Y); // todo - mainnode.lookat
	}

}