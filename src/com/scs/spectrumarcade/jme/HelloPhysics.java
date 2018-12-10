package com.scs.spectrumarcade.jme;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

/**
 * Example 12 - how to give objects physical properties so they bounce and fall.
 * @author base code by double1984, updated by zathras
 */
public class HelloPhysics extends SimpleApplication implements PhysicsTickListener {

	public static void main(String args[]) {
		HelloPhysics app = new HelloPhysics();
		app.start();
	}

	/** Prepare the Physics Application State (jBullet) */
	private BulletAppState bulletAppState;

	/** Prepare Materials */
	Material wall_mat;
	Material stone_mat;
	Material floor_mat;

	/** Prepare geometries and physical nodes for bricks and cannon balls. */
	private RigidBodyControl    srb;
	private static final Sphere sphere;
	private RigidBodyControl    floor_phy;
	private static final Box    floor;

	static {
		/** Initialize the cannon ball geometry */
		sphere = new Sphere(32, 32, 0.4f, true, false);
		sphere.setTextureMode(TextureMode.Projected);
		/** Initialize the floor geometry */
		floor = new Box(10f, 0.1f, 5f);
		floor.scaleTextureCoordinates(new Vector2f(3, 6));
	}

	@Override
	public void simpleUpdate(float ftp) {
	}

	@Override
	public void simpleInitApp() {
		/** Set up Physics Game */
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		//bulletAppState.getPhysicsSpace().enableDebug(assetManager);

		/** Configure cam to look at scene */
		cam.setLocation(new Vector3f(0, .5f, 0f));
		cam.lookAt(new Vector3f(2, 2, 0), Vector3f.UNIT_Y);
		
		/** Add InputManager action: Left click triggers shooting. */
		inputManager.addMapping("shoot",
				new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(actionListener, "shoot");
		/** Initialize the scene, materials, and physics space */
		initMaterials();
		initFloor();
	}

	/**
	 * Every time the shoot action is triggered, a new cannon ball is produced.
	 * The ball is set up to fly from the camera position in the camera direction.
	 */
	private ActionListener actionListener = new ActionListener() {
		public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("shoot") && !keyPressed) {
				makeCannonBall();
			}
		}
	};

	/** Initialize the materials used in this scene. */
	public void initMaterials() {
		wall_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
		key.setGenerateMips(true);
		Texture tex = assetManager.loadTexture(key);
		wall_mat.setTexture("ColorMap", tex);

		stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
		key2.setGenerateMips(true);
		Texture tex2 = assetManager.loadTexture(key2);
		stone_mat.setTexture("ColorMap", tex2);

		floor_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = assetManager.loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);
		floor_mat.setTexture("ColorMap", tex3);
	}

	/** Make a solid floor and add it to the scene. */
	public void initFloor() {
		Geometry floor_geo = new Geometry("Floor", floor);
		floor_geo.setMaterial(floor_mat);
		floor_geo.setLocalTranslation(0, -0.1f, 0);
		this.rootNode.attachChild(floor_geo);
		/* Make the floor physical with mass 0.0f! */
		floor_phy = new RigidBodyControl(0.0f);
		floor_geo.addControl(floor_phy);
		bulletAppState.getPhysicsSpace().add(floor_phy);
		bulletAppState.getPhysicsSpace().addTickListener(this);
	}


	/** This method creates one individual physical cannon ball.
	 * By defaul, the ball is accelerated and flies
	 * from the camera position in the camera direction.*/
	public void makeCannonBall() {
		Node node = new Node("ball");
		/** Create a cannon ball geometry and attach to scene graph. */
		Geometry ball_geo = new Geometry("cannon ball", sphere);
		ball_geo.setMaterial(stone_mat);
		node.attachChild(ball_geo);
		rootNode.attachChild(node);
		/** Position the cannon ball  */
		node.setLocalTranslation(cam.getLocation());
		/** Make the ball physcial with a mass > 0.0f */
		
		srb = new RigidBodyControl(1f);
		
		// Works
		//ball_geo.addControl(srb);
		//bulletAppState.getPhysicsSpace().add(ball_geo);

		// Wobbly
		//node.addControl(srb);
		//bulletAppState.getPhysicsSpace().add(node);

		//  No movements
		node.addControl(srb);
		bulletAppState.getPhysicsSpace().add(ball_geo);

	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		
	}

	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (this.srb != null) {
			srb.applyCentralForce(cam.getDirection().mult(4));

		}
		
	}
}