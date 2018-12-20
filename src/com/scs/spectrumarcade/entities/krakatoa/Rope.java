package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.shapes.MyCylinder;

public class Rope extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision {

	public Rope(SpectrumArcade _game, float length) {
		super(_game, "Rope");

		Vector3f start = new Vector3f(0, 0, 0);
		Vector3f end = new Vector3f(start.x, start.y-length, start.z);

		MyCylinder geometry = new MyCylinder(game.getAssetManager(), start, end, (int)length, 8, .1f, "Textures/krakatoa/rope.png");
		//box1.scaleTextureCoordinates(new Vector2f(WIDTH, HEIGHT));
		//Geometry geometry = new Geometry("Fence", box1);
		//TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.jpg");
		//TextureKey key3 = new TextureKey("Textures/street001.jpg");//fence.png");
		//key3.setGenerateMips(true);
		//Texture tex3 = game.getAssetManager().loadTexture(key3);
		//tex3.setWrap(WrapMode.Repeat);

		//Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		//mat.setTexture("DiffuseMap", tex3);
		//geometry.setMaterial(mat);

		geometry.setShadowMode(ShadowMode.CastAndReceive);
		this.mainNode.attachChild(geometry);
		// omainNode.setLocalTranslation(x+(WIDTH/2), HEIGHT/2, z+0.5f);

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		srb.setKinematic(true);

		geometry.setUserData(Settings.ENTITY, this);

	}

	@Override
	public void process(float tpfSecs) {
		mainNode.setLocalTranslation(game.player.getMainNode().getLocalTranslation());
	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		Settings.p("Rope has hit " + collidedWith);

	}


}
