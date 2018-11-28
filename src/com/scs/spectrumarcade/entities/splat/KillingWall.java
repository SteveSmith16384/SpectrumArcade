package com.scs.spectrumarcade.entities.splat;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public class KillingWall extends AbstractPhysicalEntity implements IProcessable  {
	
	private static final float RAD = 8f;

	public KillingWall(SpectrumArcade _game, float x, float z) {
		super(_game, "KillingWall");
		/*
		float w = RAD;
		float h = RAD;
		float d = RAD;
*/
		Mesh sphere = new Sphere(10, 10, 10, true, true);
		Geometry geometry = new Geometry("KillingWallGeom", sphere);

		TextureKey key3 = new TextureKey("Textures/mm_bricks.png");
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floorMat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		floorMat.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(floorMat);

		//geometry.setLocalTranslation(w/2, 0, d/2); // Move it into position

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, 0, z); // Move it into position

		geometry.setUserData(Settings.ENTITY, this);

	}


	@Override
	public void process(float tpf) {
		if (game.player.distance(this) > RAD) {
			Globals.p("Player toom far away");
		}
	}


}
