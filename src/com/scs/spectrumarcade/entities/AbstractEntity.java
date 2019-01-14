package com.scs.spectrumarcade.entities;

import java.io.IOException;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
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
