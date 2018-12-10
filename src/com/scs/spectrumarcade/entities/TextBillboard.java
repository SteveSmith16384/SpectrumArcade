package com.scs.spectrumarcade.entities;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.TextTexture;
import com.scs.spectrumarcade.components.IHudItem;

public class TextBillboard extends Geometry implements IEntity, IHudItem, IProcessable { // todo - delete

	private TextTexture tex;

	public TextBillboard(AssetManager assetManager, String text, int size) {//, float quadW, float quadH, int pixelsW, int pixelsH) {
		//super("TextBillboard", new Quad(text.length()/4, .5f));
		super("TextBillboard", new Quad(text.length() * 5 * (size/24), 35f));
		
		this.setLocalTranslation(20, 20, 0);

		int pixelsW = (int)(text.length() * size * .5);
		int pixelsH = 30;
		tex = new TextTexture(text, size, pixelsW, pixelsH);

		//Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md"); // create a simple material
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		//mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
		mat.setColor("Color", new ColorRGBA(0, 0, 0, 255));

		Texture2D texture = new Texture2D(pixelsW, pixelsH, Format.ABGR8);
		texture.setMinFilter(Texture.MinFilter.Trilinear);
		texture.setMagFilter(Texture.MagFilter.Bilinear);
		texture.setImage(tex);

		mat.setTexture("ColorMap", texture);

		this.setMaterial(mat);
		
		//this.setQueueBucket(Bucket.Transparent);

	}

	public void refreshImage() {
		tex.refreshImage();
	}
	

	@Override
	public void process(float tpfSecs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markForRemoval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actuallyRemove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spatial getSpatial() {
		return this;
	}

}