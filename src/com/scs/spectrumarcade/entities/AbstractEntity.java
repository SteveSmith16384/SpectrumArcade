package com.scs.spectrumarcade.entities;

import java.io.IOException;
import java.util.Iterator;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IEntity;

public abstract class AbstractEntity implements IEntity, Savable {

	protected SpectrumArcade game;
	public String name;
	private boolean markedForRemoval = false;

	public AbstractEntity(SpectrumArcade _game, String _name) {
		super();

		this.game = _game;
		name = _name;

	}


	public void markForRemoval() {
		if (!markedForRemoval) {
			markedForRemoval = true;
			game.markEntityForRemoval(this);
		}
	}


	public boolean isMarkedForRemoval() {
		return markedForRemoval;
	}
	
	
	
	@Override
	public String toString() {
		return "Entity:" + name;
	}


	public String getName() {
		return name;
	}


	@Override
	public void write(JmeExporter ex) throws IOException {
		// Do nothing
	}


	@Override
	public void read(JmeImporter im) throws IOException {
		// Do nothing
	}

}
