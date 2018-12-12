package com.scs.spectrumarcade.entities.androids;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

public class SlidingDoor extends AbstractPhysicalEntity implements INotifiedOfCollision, IProcessable, PhysicsTickListener {

	private static final Vector3f MOVE_UP = new Vector3f(0, 1f, 0);
	private static final float STAY_OPEN_DURATION = 3f;
	private static final float CEILING_HEIGHT = 1f;

	private boolean isOpening = false;
	private float timeUntilClose;

	public SlidingDoor(SpectrumArcade _game, float x, float yBottom, float z, float w, float h, int tex) {
		super(_game, "SlidingDoor");

		float depth = 0.1f; // Default thickness

		Box box1 = new Box(w/2, h/2, depth/2);
		box1.scaleTextureCoordinates(new Vector2f(w, 1)); // Don't scale vertically
		Geometry geometry = new Geometry("SlidingDoor", box1);
			geometry.setShadowMode(ShadowMode.CastAndReceive);

			TextureKey key3 = new TextureKey("todo");
			key3.setGenerateMips(true);
			Texture tex3 = game.getAssetManager().loadTexture(key3);
			tex3.setWrap(WrapMode.Repeat);

			Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
			mat.setTexture("DiffuseMap", tex3);
			geometry.setMaterial(mat);

		this.mainNode.attachChild(geometry);

		geometry.setLocalTranslation(w/2, h/2, depth/2 + (w/2)); // Never change position of mainNode (unless the whole object is moving)
		mainNode.setLocalTranslation(x, yBottom, z);

		srb = new RigidBodyControl(1);
		srb.setKinematic(true);
		mainNode.addControl(srb);

	}


	@Override
	public void process(float tpfSecs) {
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity pe) {
			this.startOpening();
	}


	private void startOpening() {
		if (!this.isOpening) {
			this.isOpening = true;
		}
		timeUntilClose = STAY_OPEN_DURATION;

	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float tpfSecs) {
		if (this.isOpening) {
			float topPos = CEILING_HEIGHT-.1f;
			if (this.getMainNode().getWorldTranslation().y < topPos) {
				this.getMainNode().move(MOVE_UP.mult(tpfSecs));
				//this.getMainNode().move(MOVE_UP.mult(tpf_secs));
				// position accurately at top in case of large jump
				if (this.getMainNode().getWorldTranslation().y > topPos) {
					this.getMainNode().getWorldTranslation().y = topPos;
				}
			} else {
				this.isOpening = false;
			}
		} else {
			if (timeUntilClose <= 0) {
				if (this.getMainNode().getWorldTranslation().y > 0) {
					this.getMainNode().move(MOVE_UP.mult(tpfSecs).mult(-1));
					// position accurately at top in case of large jump
					if (this.getMainNode().getWorldTranslation().y < 0) {
						this.getMainNode().getWorldTranslation().y = 0;
					}
				}
			} else {
				timeUntilClose -= tpfSecs;
			}
		}
		
	}


}
