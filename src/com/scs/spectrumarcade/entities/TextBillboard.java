package com.scs.spectrumarcade.entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.scs.spectrumarcade.TextTexture;

public class TextBillboard extends Geometry {

	private TextTexture tex;

	public TextBillboard(AssetManager assetManager, float quadW, float quadH, int pixelsW, int pixelsH) {
		super("TextBillboard", new Quad(quadW, quadH));

		Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // create a simple material
		mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

		Texture2D texture = new Texture2D(pixelsW, pixelsH, Format.ABGR8);
		texture.setMinFilter(Texture.MinFilter.Trilinear);
		texture.setMagFilter(Texture.MagFilter.Bilinear);
		tex = new TextTexture(pixelsW, pixelsH);
		texture.setImage(tex);

		mat.setTexture("DiffuseMap", texture);

		this.setMaterial(mat);

		this.setQueueBucket(Bucket.Transparent);

	}

	public void refreshImage() {
		tex.refreshImage();
	}

}