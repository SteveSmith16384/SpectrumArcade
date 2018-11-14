package com.scs.spectrumarcade.entities;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.components.INotifiedOfCollision;

public class SlidingDoor extends AbstractPhysicalEntity implements INotifiedOfCollision {

	private static final Vector3f MOVE_UP = new Vector3f(0, 1f, 0);
	private static final float STAY_OPEN_DURATION = 3f;

	private Vector3f origPosition;
	private boolean isOpening = false;
	private float timeUntilClose;
	private RigidBodyControl rbc;

	public SlidingDoor(SpectrumArcade _game, int id, float x, float yBottom, float z, float w, float h, String tex, float rotDegrees) {
		super(_game, "Sliding door");

		float depth = 0.1f; // Default thickness

		Box box1 = new Box(w/2, h/2, depth/2);
		box1.scaleTextureCoordinates(new Vector2f(w, 1)); // Don't scale vertically
		Geometry geometry = new Geometry("SlidingDoor", box1);
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		TextureKey key3 = new TextureKey(tex);
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		mat.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(mat);

		this.mainNode.attachChild(geometry);
		if (rotDegrees != 0) {
			float rads = (float)Math.toRadians(rotDegrees);
			mainNode.rotate(0, rads, 0);
		}
		geometry.setLocalTranslation(w/2, h/2, depth/2 + (w/2)); // Never change position of mainNode (unless the whole object is moving)
		mainNode.setLocalTranslation(x, yBottom, z);

		rbc = new RigidBodyControl(1f);
		geometry.addControl(rbc);
		game.bulletAppState.getPhysicsSpace().add(rbc);
		rbc.setKinematic(true);

		this.mainNode.setUserData(Settings.ENTITY, this);

		origPosition = this.getMainNode().getWorldTranslation().clone();

	}


	@Override
	public void process(float tpfSecs) {
		if (this.isOpening) {
			float topPos = 2f;//MoonbaseAssaultServer.CEILING_HEIGHT-.1f;
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
						this.getMainNode().move(new Vector3f(0, -this.getMainNode().getWorldTranslation().y, 0));
					}
				}
			} else {
				timeUntilClose -= tpfSecs;
			}
		}
	}


	@Override
	public void notifiedOfCollision(IEntity pe) {
			this.startOpening();
	}


	private void startOpening() {
		if (!this.isOpening) {
			//server.playSound(-1, MASounds.SFX_SLIDING_DOOR, this.getID(), getWorldTranslation(), Globals.DEFAULT_VOLUME, false);
			this.isOpening = true;
		}
		timeUntilClose = STAY_OPEN_DURATION;

	}


	@Override
	public void remove() {
		this.mainNode.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.rbc);
		
	}

}
