package com.scs.spectrumarcade.misc;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bounding.BoundingBox;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class ModelViewer_SpectrumArcade extends SimpleApplication implements AnimEventListener {

	private AnimControl control;

	public static void main(String[] args) {
		ModelViewer_SpectrumArcade app = new ModelViewer_SpectrumArcade();
		app.showSettings = false;

		app.start();
	}


	@Override
	public void simpleInitApp() {
		assetManager.registerLocator("assets/", FileLocator.class); // default

		//assetManager.registerLocator("assets/Textures/", FileLocator.class);

		cam.setFrustumPerspective(60, settings.getWidth() / settings.getHeight(), .1f, 100);

		super.getViewPort().setBackgroundColor(ColorRGBA.Black);

		setupLight();

		//Spatial model = assetManager.loadModel("Models/QuaterniusCars2/OBJ/Cop.obj");
		//JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/Car pack by Quaternius/CopTexture.png");

		Node model = (Node)assetManager.loadModel("Models/helic.obj");
		model.setLocalScale(3);
		
		//JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/suburb_assets_pt1/models/textures/buildings-houses_v1.jpg");

		String animNode = "Woman (Node)";
		String animToUse = "Walking";

		if (model instanceof Node) {
			SpectrumArcade.p("Listing anims:");
			JMEModelFunctions.listAllAnimations((Node)model);
			SpectrumArcade.p("Finished listing anims");

			control = JMEModelFunctions.getNodeWithControls(animNode, (Node)model);
			if (control != null) {
				control.addListener(this);
				System.out.println("Animations for selected control: " + control.getAnimationNames());
				AnimChannel channel = control.createChannel();
				try {
					channel.setLoopMode(LoopMode.Loop);
					channel.setAnim(animToUse);
					SpectrumArcade.p("Running anim " + animToUse);
				} catch (IllegalArgumentException ex) {
					SpectrumArcade.pe("Try running the right anim code!");
				}
			} else {
				SpectrumArcade.p("No animation control on selected node '" + animNode + "'");
			}
		}

		rootNode.attachChild(model);

		//this.rootNode.attachChild(JMEModelFunctions.getGrid(assetManager, 10));

		rootNode.updateGeometricState();

		model.updateModelBound();
		BoundingBox bb = (BoundingBox)model.getWorldBound();
		System.out.println("Model w/h/d: " + (bb.getXExtent()*2) + " / " + (bb.getYExtent()*2) + " / " + (bb.getZExtent()*2));
		System.out.println("Model centre: " + bb.getCenter());

		this.flyCam.setMoveSpeed(12f);

		//fpp = new FilterPostProcessor(assetManager);
		//viewPort.addProcessor(fpp);	
	}


	private void setupLight() {
		// Remove existing lights
		this.rootNode.getWorldLightList().clear();
		LightList list = this.rootNode.getWorldLightList();
		for (Light it : list) {
			this.rootNode.removeLight(it);
		}

		// We add light so we see the scene
		AmbientLight al = new AmbientLight();
		al.setColor(ColorRGBA.White.mult(1f));
		rootNode.addLight(al);

		DirectionalLight dirlight = new DirectionalLight(); // FSR need this for textures to show
		rootNode.addLight(dirlight);

	}


	@Override
	public void simpleUpdate(float tpf) {
		//System.out.println("Pos: " + this.cam.getLocation());
		//this.rootNode.rotate(0,  tpf,  tpf);
	}


	@Override
	public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {

	}


	@Override
	public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {

	}


}