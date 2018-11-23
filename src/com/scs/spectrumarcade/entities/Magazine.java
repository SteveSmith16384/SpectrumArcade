package com.scs.spectrumarcade.entities;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;

public class Magazine extends AbstractPhysicalEntity {

	public Magazine(SpectrumArcade _game, float x, float y, float z, String tex) {
		super(_game, "Magazine");
		
		float w = 0.35f;
		float h = 0.45f;
		float d = 0.04f;

		Box box1 = new Box(w/2, h/2, d/2);

		Geometry geometry = new Geometry("FloorGeom", box1);
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		TextureKey key3 = new TextureKey(tex);
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floorMat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		floorMat.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(floorMat);

		geometry.setLocalTranslation(w/2, h/2, d/2);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);

		srb = new RigidBodyControl(20);
		mainNode.addControl(srb);
	}


}
