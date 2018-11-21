package com.scs.spectrumarcade.entities.minedout;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector2f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public class Fence extends AbstractPhysicalEntity {

	private static final float WIDTH = 2f;
	private static final float HEIGHT = 1.5f;

	public Fence(SpectrumArcade _game, float x, float z, float rot) {
		super(_game, "Fence");

		Box box1 = new Box(WIDTH/2, HEIGHT/2, .1f);
		box1.scaleTextureCoordinates(new Vector2f(WIDTH, HEIGHT));
		Geometry geometry = new Geometry("Fence", box1);
		//TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		TextureKey key3 = new TextureKey("Textures/bricktex.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		mat.setTexture("DiffuseMap", tex3);
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		geometry.setMaterial(mat);
		geometry.setQueueBucket(Bucket.Transparent);

		this.mainNode.attachChild(geometry);
		float rads = (float)Math.toRadians(rot);
		mainNode.rotate(0, rads, 0);
		mainNode.setLocalTranslation(x+(WIDTH/2), HEIGHT/2, z+0.5f);

		srb = new RigidBodyControl(0);
		srb.setKinematic(true);
		mainNode.addControl(srb);

		geometry.setUserData(Settings.ENTITY, this);

	}


	@Override
	public void process(float tpf) {
		// Do nothing
	}

}
