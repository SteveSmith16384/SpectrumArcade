package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.IAbility;
import com.scs.spectrumarcade.models.VWCorradon;

public class TurboEspritAvatar extends AbstractTurboEspritCar {
	
	private Node camNode;

	public TurboEspritAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, x, y, z);
		
		camNode = new Node("CameraNode");
		camNode.setLocalTranslation(0f, 1.2f, -4);
		this.mainNode.attachChild(camNode);
		
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Esprit pos: " + this.getMainNode().getWorldTranslation());
	}


	@Override
	protected Node getModel() {
		return new VWCorradon(game.getAssetManager(), 1);
	}

	/*
	@Override
	public void setCameraLocation(Camera cam) {
		//game.getCamera().lookAt(this.mainNode.getWorldTranslation(), Vector3f.UNIT_Y);
		//cam.setLocation(camNode.getWorldTranslation()); //this.mainNode;
		
	}
*/

	@Override
	public void clearForces() {
		vehicle.clearForces();
	}


	@Override
	public void setAvatarVisible(boolean b) {
		if (b) {
			carModel.setCullHint(CullHint.Never);
		} else {
			carModel.setCullHint(CullHint.Always);
		}

	}


	@Override
	public float getCameraHeight() {
		return 1f;
	}


	@Override
	public void showKilledAnim() {
		// TODO Auto-generated method stub
		
	}

}
