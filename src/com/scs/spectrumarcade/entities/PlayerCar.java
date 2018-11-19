package com.scs.spectrumarcade.entities;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.scs.spectrumarcade.Avatar;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.SpectrumArcade;

public class PlayerCar extends Avatar {

	private VehicleControl vehicle;
	private final float accelerationForce = 1000.0f;
	private final float brakeForce = 100.0f;
	private float steeringValue = 0;
	private float accelerationValue = 0;
	private Vector3f jumpForce = new Vector3f(0, 3000, 0);

	public PlayerCar(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "PlayerCar");

		Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		mat.getAdditionalRenderState().setWireframe(true);
		mat.setColor("Color", ColorRGBA.Red);

		//create a compound shape and attach the BoxCollisionShape for the car body at 0,1,0
		//this shifts the effective center of mass of the BoxCollisionShape to 0,-1,0
		CompoundCollisionShape compoundShape = new CompoundCollisionShape();
		BoxCollisionShape box = new BoxCollisionShape(new Vector3f(1.2f, 0.5f, 2.4f));
		compoundShape.addChildShape(box, new Vector3f(0, 1, 0));

		//create vehicle node
		Node vehicleNode = this.mainNode;// new Node("vehicleNode");
		vehicle = new VehicleControl(compoundShape, 400);
		vehicleNode.addControl(vehicle);

		//setting suspension values for wheels, this can be a bit tricky
		//see also https://docs.google.com/Doc?docid=0AXVUZ5xw6XpKZGNuZG56a3FfMzU0Z2NyZnF4Zmo&hl=en
		float stiffness = 60.0f;//200=f1 car
		float compValue = .3f; //(should be lower than damp)
		float dampValue = .4f;
		vehicle.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
		vehicle.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
		vehicle.setSuspensionStiffness(stiffness);
		vehicle.setMaxSuspensionForce(10000.0f);

		//Create four wheels and add them at their locations
		Vector3f wheelDirection = new Vector3f(0, -1, 0); // was 0, -1, 0
		Vector3f wheelAxle = new Vector3f(-1, 0, 0); // was -1, 0, 0
		float radius = 0.5f;
		float restLength = 0.3f;
		float yOff = 0.5f;
		float xOff = 1f;
		float zOff = 2f;

		Cylinder wheelMesh = new Cylinder(16, 16, radius, radius * 0.6f, true);

		Node node1 = new Node("wheel 1 node");
		Geometry wheels1 = new Geometry("wheel 1", wheelMesh);
		node1.attachChild(wheels1);
		wheels1.rotate(0, FastMath.HALF_PI, 0);
		wheels1.setMaterial(mat);
		vehicle.addWheel(node1, new Vector3f(-xOff, yOff, zOff),
				wheelDirection, wheelAxle, restLength, radius, true);

		Node node2 = new Node("wheel 2 node");
		Geometry wheels2 = new Geometry("wheel 2", wheelMesh);
		node2.attachChild(wheels2);
		wheels2.rotate(0, FastMath.HALF_PI, 0);
		wheels2.setMaterial(mat);
		vehicle.addWheel(node2, new Vector3f(xOff, yOff, zOff),
				wheelDirection, wheelAxle, restLength, radius, true);

		Node node3 = new Node("wheel 3 node");
		Geometry wheels3 = new Geometry("wheel 3", wheelMesh);
		node3.attachChild(wheels3);
		wheels3.rotate(0, FastMath.HALF_PI, 0);
		wheels3.setMaterial(mat);
		vehicle.addWheel(node3, new Vector3f(-xOff, yOff, -zOff),
				wheelDirection, wheelAxle, restLength, radius, false);

		Node node4 = new Node("wheel 4 node");
		Geometry wheels4 = new Geometry("wheel 4", wheelMesh);
		node4.attachChild(wheels4);
		wheels4.rotate(0, FastMath.HALF_PI, 0);
		wheels4.setMaterial(mat);
		vehicle.addWheel(node4, new Vector3f(xOff, yOff, -zOff),
				wheelDirection, wheelAxle, restLength, radius, false);

		vehicleNode.attachChild(node1);
		vehicleNode.attachChild(node2);
		vehicleNode.attachChild(node3);
		vehicleNode.attachChild(node4);

		//this.mainNode.addControl(vehicle);
		//this.mainNode.attachChild(vehicleNode);
		this.mainNode.setLocalTranslation(x,  y,  z);
		vehicle.setPhysicsLocation(new Vector3f(x, y, z));
	}

	
	@Override
	public void process(float tpfSecs) {
		/*Vector3f pos = new Vector3f();
		vehicle.getPhysicsLocation(pos);
		Globals.p("Car pos:" + pos);
*/
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
				vehicle.brake(brakeForce);
			} else {
				vehicle.brake(0f);
			}
			/*} else if (binding.equals("Space")) {
	            if (value) {
	                vehicle.applyImpulse(jumpForce, Vector3f.ZERO);
	            }*/
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

}
