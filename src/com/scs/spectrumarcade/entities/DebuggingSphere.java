package com.scs.spectrumarcade.entities;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.scs.spectrumarcade.SpectrumArcade;

public class DebuggingSphere extends AbstractPhysicalEntity {

	public DebuggingSphere(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "DebuggingSphere");

		Mesh sphere = new Sphere(8, 8, .11f, true, false);
		Geometry ball_geo = new Geometry("DebuggingSphere", sphere);
		ball_geo.setShadowMode(ShadowMode.CastAndReceive);
		TextureKey key3 = new TextureKey( "Textures/sun.jpg");
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		Material floor_mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");
		floor_mat.setTexture("DiffuseMap", tex3);
		ball_geo.setMaterial(floor_mat);

		this.mainNode.attachChild(ball_geo);
		this.mainNode.setLocalTranslation(x, y, z);

	}


	@Override
	public void process(float tpf) {
		// Do nothing
	}


	@Override
	public void remove() {
		this.mainNode.removeFromParent();
	}


}
