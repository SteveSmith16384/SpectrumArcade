package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IAvatar;

public class StockCarAvatar extends AbstractStockCar implements IAvatar {

	//private Node camNode; // todo - remove?

	protected static final float accelerationForce = 1000.0f;
	protected static final float brakeForce = 100.0f;

	protected float steeringValue = 0;
	protected float accelerationValue = 0;

	public StockCarAvatar(SpectrumArcade _game, float x, float y, float z, Vector3f lookAt) {
		super(_game, "StockCarAvatar", x, y, z, lookAt, true, 1);
/*
		camNode = new Node("CameraNode");
		camNode.setLocalTranslation(0f, 1.8f, -4);
		this.mainNode.attachChild(camNode);*/
	}


	@Override
	public void process(float tpfSecs) {
		super.process(tpfSecs);
	}


	@Override
	public void onAction(String binding, boolean value, float tpf) {
		if (binding.equals("Left")) {
			if (value) {
				steeringValue += .5f;
			} else {
				steeringValue += -.5f;
			}
			vehicle.steer(steeringValue);
		} else if (binding.equals("Right")) {
			if (value) {
				steeringValue += -.5f;
			} else {
				steeringValue += .5f;
			}
			vehicle.steer(steeringValue);
		} else if (binding.equals("Fwd")) {
			if (value) {
				accelerationValue += accelerationForce;
			} else if (accelerationValue > 0) {
				accelerationValue -= accelerationForce;

			}
			vehicle.accelerate(accelerationValue);
		} else if (binding.equals("Backwards")) {
			if (value) {
				accelerationValue -= accelerationForce;
			} else {
				accelerationValue += accelerationForce;
			}
			vehicle.accelerate(accelerationValue);
		} else if (binding.equals("Space")) {
			if (value) {
				vehicle.brake(brakeForce);
			} else {
				vehicle.brake(0f);
			}
		} else if (binding.equals("Test")) {
			if (value) {
				this.vehicle.applyTorque(new Vector3f(0, 1, 0).multLocal(10f));
			}
		}
	}


	@Override
	public void warp(Vector3f vec) {
		vehicle.setPhysicsLocation(vec);

	}
/*

	@Override
	public void setCameraLocation(Camera cam) {
		// Top down view
		//cam.setLocation(new Vector3f(30, 100, 30));
		//game.getCamera().lookAt(new Vector3f(31, 0, 31), Vector3f.UNIT_Y);
	}

*/
	@Override
	public void clearForces() {
		vehicle.setLinearVelocity(Vector3f.ZERO);
		vehicle.setAngularVelocity(Vector3f.ZERO);
		vehicle.resetSuspension();
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

}
