package com.scs.spectrumarcade.entities.tomahawk;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.shapes.Pyramid;

public class Hill extends AbstractPhysicalEntity {

	public Geometry geometry;
	
	public Hill(SpectrumArcade _game, float x, float y, float z, float width, float height, String tex) {
		super(_game, "Hill");

		//Dome box1 = new Dome(2, 4, size);
		Pyramid box1 = new Pyramid(width, height);
		
		float w = width;
		float h = height;
		float d = width;
/*
		box1.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(new float[]{
				0, h, w, h, w, 0, 0, 0, // back
				0, h, d, h, d, 0, 0, 0, // right
				0, h, w, h, w, 0, 0, 0, // front
				0, h, d, h, d, 0, 0, 0, // left
				w, 0, w, d, 0, d, 0, 0, // top
				w, 0, w, d, 0, d, 0, 0  // bottom
		}));
*/
		geometry = new Geometry("FloorGeom", box1);
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		TextureKey key3 = new TextureKey(tex);
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floorMat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		floorMat.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(floorMat);

		geometry.setLocalTranslation(w/2, 0, d/2); // Move it into position

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z); // Move it into position

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);

		geometry.setUserData(Settings.ENTITY, this);

	}


}
