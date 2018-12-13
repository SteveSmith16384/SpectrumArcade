package com.scs.spectrumarcade.entities;

import java.util.ArrayList;
import java.util.List;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.IAvatar;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.models.GenericWalkingAvatar;

public class WalkingPlayer extends AbstractPhysicalEntity implements IAvatar {

	private static final float FOOTSTEP_INTERVAL = .3f;

	// Our movement speed
	public static final float speed = 4;
	private static final float strafeSpeed = 4f;

	private GenericWalkingAvatar minerModel; 
	public BetterCharacterControl playerControl;
	private Vector3f walkDirection = new Vector3f();
	private boolean left = false, right = false, up = false, down = false;
	private boolean followCam;

	//Temporary vectors used on each frame.
	private Vector3f camDir = new Vector3f();
	private Vector3f camLeft = new Vector3f();
	private Vector3f tempAvatarDir = new Vector3f();

	// Footsteps
	private List<AudioNode> audio_node_footsteps = new ArrayList<>();
	private float time_until_next_footstep_sfx = 1;
	private int next_footstep_sound = 0;
	public boolean walking = false;
	private boolean canJump;

	public WalkingPlayer(SpectrumArcade _game, float x, float y, float z, float jumpPower, boolean _followCam, String tex) {
		super(_game, "Player");

		canJump = jumpPower > 0;
		followCam = _followCam;

		/** Create a box to use as our player model */
		Box box1 = new Box(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, Settings.PLAYER_RAD);
		Geometry playerGeometry = new Geometry("Player", box1);
		playerGeometry.setCullHint(CullHint.Always);
		this.getMainNode().attachChild(playerGeometry);
		this.getMainNode().setLocalTranslation(x, y, z);

		// create character control parameters (Radius,Height,Weight)
		// Radius and Height determine the size of the collision bubble
		// Weight determines how much gravity effects the control
		playerControl = new BetterCharacterControl(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, 1f);
		playerControl.setJumpForce(new Vector3f(0, jumpPower, 0)); 
		playerControl.setGravity(new Vector3f(0, 1f, 0));
		this.getMainNode().addControl(playerControl);

		if (followCam) {
			minerModel = new GenericWalkingAvatar(game.getAssetManager(), tex);
			//if (!Settings.TEST_BILLBOARD) {
			this.getMainNode().attachChild(minerModel);
			//}
		}

		for (int i=1 ; i<=8 ; i++) {
			AudioNode an = new AudioNode(game.getAssetManager(), "Sounds/jute-dh-steps/stepdirt_" + i + ".ogg", false);
			an.setPositional(false);
			an.setVolume(.2f);
			this.getMainNode().attachChild(an);
			this.audio_node_footsteps.add(an);
		}

	}


	@Override
	public void process(float tpf) {
		Camera cam = game.getCamera();

		if (this.minerModel != null) {
			// Set position and direction of avatar model, which doesn't get moved automatically
			//this.container.setLocalTranslation(this.getWorldTranslation());
			tempAvatarDir.set(game.getCamera().getDirection());
			tempAvatarDir.y = 0;
			JMEAngleFunctions.rotateToWorldDirection(this.minerModel, tempAvatarDir);

		}

		camDir.set(cam.getDirection()).multLocal(speed, 0.0f, speed);
		camLeft.set(cam.getLeft()).multLocal(strafeSpeed);
		walkDirection.set(0, 0, 0);
		walking = up || down || left || right;
		if (left) {
			walkDirection.addLocal(camLeft);
		}
		if (right) {
			walkDirection.addLocal(camLeft.negate());
		}
		if (up) {
			walkDirection.addLocal(camDir);
		}
		if (down) {
			walkDirection.addLocal(camDir.negate());
		}
		playerControl.setWalkDirection(walkDirection);

		if (walking) {
			if (this.minerModel != null) {
				this.minerModel.walkAnim();
			}
			/*
				time_until_next_footstep_sfx -= tpf;
				if (time_until_next_footstep_sfx <= 0) {
					AudioNode an = this.audio_node_footsteps.get(next_footstep_sound);
					try {
						an.playInstance();
					} catch (Exception ex) {
						// No speakers?
					}
					next_footstep_sound++;
					if (next_footstep_sound >= audio_node_footsteps.size()) {
						next_footstep_sound = 0;
					}
					time_until_next_footstep_sfx = FOOTSTEP_INTERVAL + (SpectrumArcade.rnd.nextFloat()/3);
				}
			 */
		} else {
			//time_until_next_footstep_sfx = 0;
			if (this.minerModel != null) {
				this.minerModel.idleAnim();
			}
		}

	}


	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		if (binding.equals("Left")) {
			left = isPressed;
		} else if (binding.equals("Right")) {
			right = isPressed;
		} else if (binding.equals("Fwd")) {
			up = isPressed;
		} else if (binding.equals("Backwards")) {
			down = isPressed;
		} else if (binding.equals("Jump")) {
			if (canJump) {
				if (isPressed) { 
					playerControl.jump();
					this.minerModel.jumpAnim();
				}
			}
		}

	}


	@Override
	public void warp(Vector3f v) {
		playerControl.warp(v);
	}


	@Override
	public void setCameraLocation(Camera cam) {
		//Vector3f vec = getMainNode().getWorldTranslation();
		//if (!Settings.FREE_CAM) {
			/*if (!followCam) {
				cam.setLocation(new Vector3f(vec.x, vec.y + Settings.PLAYER_HEIGHT * .8f, vec.z)); // Drop cam slightly so we're looking out of our eye level - todo - don't create each time
			} else {
				// Camera system in level handles it
			}*/
		/*} else {
			cam.setLocation(new Vector3f(vec.x, vec.y + 15f, vec.z));
		}*/

	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		if (playerControl != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.playerControl);
		}

	}


	@Override
	public void clearForces() {
	}


}
