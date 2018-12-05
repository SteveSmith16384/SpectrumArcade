package com.scs.spectrumarcade;

import com.atr.jme.font.TrueTypeFont;
import com.atr.jme.font.TrueTypeMesh;
import com.atr.jme.font.asset.TrueTypeKeyMesh;
import com.atr.jme.font.asset.TrueTypeLoader;
import com.atr.jme.font.shape.TrueTypeContainer;
import com.atr.jme.font.util.StringContainer;
import com.atr.jme.font.util.Style;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.ui.Picture;

import ssmith.util.RealtimeInterval;

public class HUD extends Node {

	private static final int MAX_LINES = 6;

	private RealtimeInterval updateHudTextInterval = new RealtimeInterval(1000);

	private Camera cam;
	private Geometry damage_box;
	private ColorRGBA dam_box_col = new ColorRGBA(1, 0, 0, 0.0f);
	private boolean process_damage_box;
	private SpectrumArcade game;

	//private BitmapText textArea; 
	private TrueTypeContainer textArea; // For showing all other stats 

	public HUD(SpectrumArcade _game, Camera _cam, ColorRGBA col) {
		super("HUD");

		game = _game;
		cam = _cam;

		super.setLocalTranslation(0, 0, 0);

		//textArea = new BitmapText(font);//ttfSmall.getFormattedText(new StringContainer(ttfSmall, ""), ColorRGBA.Green);
		game.getAssetManager().registerLoader(TrueTypeLoader.class, "ttf");
		TrueTypeKeyMesh ttkSmall = new TrueTypeKeyMesh("Fonts/zx_spectrum-7.ttf", Style.Bold, (int)30);
		TrueTypeFont ttfSmall = (TrueTypeMesh)_game.getAssetManager().loadAsset(ttkSmall);
		textArea = ttfSmall.getFormattedText(new StringContainer(ttfSmall, "HELLO!"), col);
		//textArea.setLocalTranslation(xPos, (int)(cam.getHeight()*.6f), 0);
		//this.attachChild(textArea);
		//textArea.setColor(ColorRGBA.White);
		textArea.setLocalTranslation(10, (int)(cam.getHeight()*1), 0);
		this.attachChild(textArea);

		// Damage box
		{
			Material mat = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
			mat.setColor("Color", this.dam_box_col);
			mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			damage_box = new Geometry("damagebox", new Quad(cam.getWidth(), cam.getHeight()));
			damage_box.move(0, 0, 0);
			damage_box.setMaterial(mat);
			this.attachChild(damage_box);
		}

		this.updateGeometricState();

		this.updateModelBound();

	}


	public void processByClient(float tpf) {
		if (updateHudTextInterval.hitInterval()) {
			this.updateTextArea();

		}

		if (process_damage_box) {
			this.dam_box_col.a -= (tpf/2);
			if (dam_box_col.a <= 0) {
				dam_box_col.a = 0;
				process_damage_box = false;
			}
		}

	}


	private void updateTextArea() {
		this.textArea.setText(game.getHUDText());
		this.textArea.updateGeometry();
	}


	public void showDamageBox() {
		process_damage_box = true;
		this.dam_box_col.a = .5f;
		this.dam_box_col.r = 1f;
		this.dam_box_col.g = 0f;
		this.dam_box_col.b = 0f;
	}


	public void showCollectBox() {
		process_damage_box = true;
		this.dam_box_col.a = .3f;
		this.dam_box_col.r = 0f;
		this.dam_box_col.g = 1f;
		this.dam_box_col.b = 1f;
	}


	private void addTargetter() {
		Picture targetting_reticule = new Picture("HUD Picture");
		targetting_reticule.setImage(game.getAssetManager(), "Textures/centre_crosshairs.png", true);
		float crosshairs_w = cam.getWidth()/10;
		targetting_reticule.setWidth(crosshairs_w);
		float crosshairs_h = cam.getHeight()/10;
		targetting_reticule.setHeight(crosshairs_h);
		targetting_reticule.setLocalTranslation((cam.getWidth() - crosshairs_w)/2, (cam.getHeight() - crosshairs_h)/2, 0);
		this.attachChild(targetting_reticule);
	}

}
