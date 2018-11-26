package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.PlayerCar;
import com.scs.spectrumarcade.models.VWCorradon;

public class StockCarAvatar extends PlayerCar {
	
	private Node camNode;

	public StockCarAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, x, y, z);
		
		//super.vehicle.setFrictionSlip(.1f);
		
		camNode = new Node("CameraNode");
		camNode.setLocalTranslation(0f, 1.2f, -4);
		this.mainNode.attachChild(camNode);
	}


	@Override
	public void process(float tpfSecs) {
		super.process(tpfSecs);
		//Globals.p("Car pos: " + this.getMainNode().getWorldTranslation());
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
