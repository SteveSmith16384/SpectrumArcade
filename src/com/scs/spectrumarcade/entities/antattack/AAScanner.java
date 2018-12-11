package com.scs.spectrumarcade.entities.antattack;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IHudItem;

import ssmith.util.RealtimeInterval;

public class AAScanner extends Geometry implements IEntity, IHudItem, IProcessable {

	private RealtimeInterval checkInt = new RealtimeInterval(1000);
	private float prevDist = 0;
	private Damsel damsel;

	private SpectrumArcade game;
	private Material mat;

	public AAScanner(SpectrumArcade _game, Damsel _damsel) {
		super("AAScanner", new Quad(50, 50));

		game = _game;
		damsel = _damsel;

		this.setLocalTranslation(50, 0, 0);

		mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		this.setMaterial(mat);
	}

	
	@Override
	public void markForRemoval() {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void actuallyRemove() {
		this.removeFromParent();

	}

	@Override
	public void process(float tpfSecs) {
		if (checkInt.hitInterval()) {
			float dist = game.player.distance(damsel);
			if (dist > prevDist) {
				mat.setColor("Color", ColorRGBA.Red);
			} else if (dist < prevDist) {
				mat.setColor("Color", ColorRGBA.Green);
			}
			prevDist = dist;
		}

	}

/*
	@Override
	public Spatial getSpatial() {
		return this;
	}
*/

	@Override
	public void actuallyAdd() {
		game.getGuiNode().attachChild(this);
		
	}

}
