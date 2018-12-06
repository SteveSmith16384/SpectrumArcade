package com.scs.spectrumarcade.entities;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;

public class TextBillboardEntity extends AbstractPhysicalEntity implements IProcessable { // todo - delete

	private TextBillboard ab;
	
	public TextBillboardEntity(SpectrumArcade _game, String text, float x, float y, float z) {
		super(_game, "TextBillboardEntity");

		ab = new TextBillboard(game.getAssetManager(), text);//, 2f, .5f, 200, 30);
		ab.setLocalTranslation(0, 0, 0);
		this.mainNode.attachChild(ab);

		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();
	}


	@Override
	public void process(float tpfSecs) {
		Vector3f pos = game.player.getMainNode().getWorldTranslation().clone();  // todo - dcet
		pos.y += 1.2f;
		BoundingBox bb = (BoundingBox)ab.getWorldBound();
		//pos.x -= bb.getXExtent();
		Vector3f dir = game.getCamera().getDirection();// game.player.getMainNode().getWorldRotation().getRotationColumn(2);
		
		Vector3f newPos = pos.addLocal(dir.mult(1));
		this.getMainNode().setLocalTranslation(newPos);
		
		ab.lookAt(game.getCamera().getLocation(), Vector3f.UNIT_Y); // todo - mainnode.lookat
	}

}