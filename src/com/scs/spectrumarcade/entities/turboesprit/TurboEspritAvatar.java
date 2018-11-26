package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.models.VWCorradon;

public class TurboEspritAvatar extends AbstractTurboEspritCar {
	
	private Node camNode;

	public TurboEspritAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, x, y, z);
		
		camNode = new Node("CameraNode");
		camNode.setLocalTranslation(0f, 1.2f, -4);
		this.mainNode.attachChild(camNode);
		
		//game.getCamera().setLocation(camNode.getWorldTranslation()); // todo -re3move
	}


	@Override
	public void process(float tpfSecs) {
		super.process(tpfSecs);
		//Globals.p("Esprit pos: " + this.getMainNode().getWorldTranslation());
	}


	@Override
	protected Node getModel() {
		return new VWCorradon(game.getAssetManager());
	}

	
	@Override
	public void setCameraLocation(Camera cam) {
		game.getCamera().lookAt(this.mainNode.getWorldTranslation(), Vector3f.UNIT_Y);
		cam.setLocation(camNode.getWorldTranslation()); //this.mainNode;
		
	}


}
