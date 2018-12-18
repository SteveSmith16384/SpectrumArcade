package com.scs.spectrumarcade.entities.turboesprit;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public class SkyScraper extends AbstractPhysicalEntity {

	public SkyScraper(SpectrumArcade _game, float leftX, float backZ, float w, float h, float d) {
		super(_game, "SkyScraper");

		String tex = "Textures/turboesprit/building_wall.jpg"; // todo - change

		Box box1 = new Box(w/2, h/2, d/2);

		box1.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(new float[]{ // Ensure texture is tiled correctly
				0, h, w, h, w, 0, 0, 0, // back
				0, h, d, h, d, 0, 0, 0, // right
				0, h, w, h, w, 0, 0, 0, // front
				0, h, d, h, d, 0, 0, 0, // left
				w, 0, w, d, 0, d, 0, 0, // top
				w, 0, w, d, 0, d, 0, 0  // bottom
		}));

		box1.scaleTextureCoordinates(new Vector2f(.1f, .1f));

		Geometry geometry = new Geometry("SkyScraper", box1);
		TextureKey key3 = new TextureKey(tex);
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material material = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		material.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(material);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(leftX+(w/2), h/2, backZ+(d/2));

		srb = new RigidBodyControl(0);
		mainNode.addControl(srb);

		geometry.setUserData(Settings.ENTITY, this);

	}


}