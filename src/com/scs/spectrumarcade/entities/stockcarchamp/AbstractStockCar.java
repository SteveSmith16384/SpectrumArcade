package com.scs.spectrumarcade.entities.stockcarchamp;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Cylinder;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.models.FordFocusModel;

public abstract class AbstractStockCar extends AbstractPhysicalEntity {

	private static final boolean SHOW_WHEELS = true;

	public VehicleControl vehicle;


	public AbstractStockCar(SpectrumArcade _game, String name, float x, float y, float z, boolean player, int texNum) {
		super(_game, name);

		TextureKey key3 = new TextureKey("Textures/tire.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		//Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
		//mat.getAdditionalRenderState().setWireframe(true);
		//mat.setColor("Color", ColorRGBA.Red);

		Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
		mat.setTexture("DiffuseMap", tex3);

		//create a compound shape and attach the BoxCollisionShape for the car body at 0,1,0
		//this shifts the effective center of mass of the BoxCollisionShape to 0,-1,0
		CompoundCollisionShape compoundShape = new CompoundCollisionShape();
		//BoxCollisionShape box = new BoxCollisionShape(new Vector3f(1.2f, 0.5f, 2.4f));
		BoxCollisionShape box = new BoxCollisionShape(new Vector3f(0.7f, 0.65f, 1.25f));
		compoundShape.addChildShape(box, new Vector3f(0, 1, 0));

		//create vehicle node
		Node vehicleNode = this.mainNode;// new Node("vehicleNode");
		vehicle = new VehicleControl(compoundShape, 400);
		vehicleNode.addControl(vehicle);

		//setting suspension values for wheels, this can be a bit tricky
		//see also https://docs.google.com/Doc?docid=0AXVUZ5xw6XpKZGNuZG56a3FfMzU0Z2NyZnF4Zmo&hl=en
		float stiffness = 70f;//60.0f;//200=f1 car
		float compValue = .6f; //(should be lower than damp)
		float dampValue = .8f;
		vehicle.setSuspensionCompression(compValue * FastMath.sqrt(stiffness));
		vehicle.setSuspensionDamping(dampValue * FastMath.sqrt(stiffness));
		vehicle.setSuspensionStiffness(stiffness);
		vehicle.setMaxSuspensionForce(10000.0f);

		//Create four wheels and add them at their locations
		Vector3f wheelDirection = new Vector3f(0, -1, 0); // was 0, -1, 0
		Vector3f wheelAxle = new Vector3f(-1, 0, 0); // was -1, 0, 0

		float radius = 0.15f;
		float restLength = 0.1f;
		float yOff = 0.2f;
		float xOff = .4f;
		float zOff = .7f;

		Cylinder wheelMesh = new Cylinder(16, 16, radius, radius * 0.6f, true);

		Node node1 = new Node("wheel 1 node");
		Geometry wheels1 = new Geometry("wheel 1", wheelMesh); // Front (steering)
		node1.attachChild(wheels1);
		wheels1.rotate(0, FastMath.HALF_PI, 0);
		wheels1.setMaterial(mat);
		VehicleWheel w1 = vehicle.addWheel(node1, new Vector3f(-xOff, yOff, zOff),
				wheelDirection, wheelAxle, restLength, radius, true);
		if (player) {
			if (Settings.TRY_SKIDDING) {
				w1.setFrictionSlip(3f);
			}
		}

		Node node2 = new Node("wheel 2 node");
		Geometry wheels2 = new Geometry("wheel 2", wheelMesh); // Front (steering)
		node2.attachChild(wheels2);
		wheels2.rotate(0, FastMath.HALF_PI, 0);
		wheels2.setMaterial(mat);
		VehicleWheel w2 = vehicle.addWheel(node2, new Vector3f(xOff, yOff, zOff),
				wheelDirection, wheelAxle, restLength, radius, true);
		if (player) {
			if (Settings.TRY_SKIDDING) {
				w2.setFrictionSlip(3f);
			}
		}

		Node node3 = new Node("wheel 3 node");
		Geometry wheels3 = new Geometry("wheel 3", wheelMesh);
		node3.attachChild(wheels3);
		wheels3.rotate(0, FastMath.HALF_PI, 0);
		wheels3.setMaterial(mat);
		VehicleWheel w3 = vehicle.addWheel(node3, new Vector3f(-xOff, yOff, -zOff),
				wheelDirection, wheelAxle, restLength, radius, false);
		if (player) {
			if (Settings.TRY_SKIDDING) {
				w3.setFrictionSlip(2.35f); // Low = slide.  2 skids too much, 2.5 doesn't
			}
		}

		Node node4 = new Node("wheel 4 node");
		Geometry wheels4 = new Geometry("wheel 4", wheelMesh);
		node4.attachChild(wheels4);
		wheels4.rotate(0, FastMath.HALF_PI, 0);
		wheels4.setMaterial(mat);
		VehicleWheel w4 = vehicle.addWheel(node4, new Vector3f(xOff, yOff, -zOff),
				wheelDirection, wheelAxle, restLength, radius, false);
		if (player) {
			if (Settings.TRY_SKIDDING) {
				w4.setFrictionSlip(2.35f); // Low = slide
			}
		}

		vehicleNode.attachChild(node1);
		vehicleNode.attachChild(node2);
		vehicleNode.attachChild(node3);
		vehicleNode.attachChild(node4);

		if (!SHOW_WHEELS) {
			node1.setCullHint(CullHint.Always);
			node2.setCullHint(CullHint.Always);
			node3.setCullHint(CullHint.Always);
			node4.setCullHint(CullHint.Always);
		}

		this.mainNode.setLocalTranslation(x, y, z);
		vehicle.setPhysicsLocation(new Vector3f(x, y, z));

		this.mainNode.attachChild(new FordFocusModel(game.getAssetManager(), texNum));
	}


	public void process(float tpfSecs) {
		// Check if upside-down
		Vector3f upDir = this.getMainNode().getWorldRotation().getRotationColumn(1);
		if (upDir.y < 0) {
			Globals.p("Car upside down!");
			// todo - right
			this.vehicle.applyTorqueImpulse(new Vector3f(8, 0, 0));
		}

	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		game.getBulletAppState().getPhysicsSpace().remove(this.vehicle);
	}

}
