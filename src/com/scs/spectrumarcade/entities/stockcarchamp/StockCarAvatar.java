package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.abilities.IAbility;

public class StockCarAvatar extends AbstractStockCar implements IAvatar {
	
	private Node camNode;

	protected static final float accelerationForce = 1000.0f;
	protected static final float brakeForce = 100.0f;
	
	protected float steeringValue = 0;
	protected float accelerationValue = 0;

	public StockCarAvatar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, x, y, z);

		camNode = new Node("CameraNode");
		camNode.setLocalTranslation(0f, 1.2f, -4);
		this.mainNode.attachChild(camNode);
	}


	@Override
	public void process(float tpfSecs) {
		//Globals.p("Car pos: " + this.getMainNode().getWorldTranslation());
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
		} else if (binding.equals("Up")) {
			if (value) {
				accelerationValue += accelerationForce;
			} else {
				accelerationValue -= accelerationForce;
			}
			vehicle.accelerate(accelerationValue);
		} else if (binding.equals("Down")) {
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
		} else if (binding.equals("Jump")) {
			if (value) {
				System.out.println("Reset");
				vehicle.setPhysicsLocation(Vector3f.ZERO);
				vehicle.setPhysicsRotation(new Matrix3f());
				vehicle.setLinearVelocity(Vector3f.ZERO);
				vehicle.setAngularVelocity(Vector3f.ZERO);
				vehicle.resetSuspension();
			}
		}
	}
	

	@Override
	public void warp(Vector3f vec) {
		vehicle.setPhysicsLocation(vec);

	}

	
	@Override
	public void setCameraLocation(Camera cam) {
		game.getCamera().lookAt(this.mainNode.getWorldTranslation(), Vector3f.UNIT_Y);
		//cam.setLocation(camNode.getWorldTranslation()); //this.mainNode;
		cam.setLocation(new Vector3f(10, 10, 10));
	}

	/*
	@Override
	public void setAbility(int num, IAbility a) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void activateAbility(int num) {
		// TODO Auto-generated method stub
		
	}
*/

	@Override
	public void clearForces() {
		vehicle.clearForces();
	}


}
