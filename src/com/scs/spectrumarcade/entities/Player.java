package com.scs.spectrumarcade.entities;

import java.util.ArrayList;
import java.util.List;

import com.jme3.audio.AudioNode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.Settings;

public class Player extends AbstractPhysicalEntity {

	private static final float FOOTSTEP_INTERVAL = .3f;

	private Geometry playerGeometry;
	public BetterCharacterControl playerControl;

	private List<AudioNode> audio_node_footsteps = new ArrayList<>();
	private float time_until_next_footstep_sfx = 1;
	private int next_footstep_sound = 0;
	public boolean walking = false;

	public Player(SpectrumArcade _game, float x, float z) {
		super(_game, "Player");

		/** Create a box to use as our player model */
		Box box1 = new Box(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, Settings.PLAYER_RAD);
		playerGeometry = new Geometry("Player", box1);
		//playerGeometry.setLocalTranslation(new Vector3f(x, 2f, z));
		playerGeometry.setCullHint(CullHint.Always);
		this.getMainNode().attachChild(playerGeometry);
		this.getMainNode().setLocalTranslation(x, 2, z);

		// create character control parameters (Radius,Height,Weight)
		// Radius and Height determine the size of the collision bubble
		// Weight determines how much gravity effects the control
		playerControl = new BetterCharacterControl(Settings.PLAYER_RAD, Settings.PLAYER_HEIGHT, 1f);
		// set basic physical properties:
		playerControl.setJumpForce(new Vector3f(0, 5f, 0)); 
		playerControl.setGravity(new Vector3f(0, 1f, 0));
		//playerControl.warp(new Vector3f(x, 6, z)); // So we drop
		this.getMainNode().addControl(playerControl);

		game.bulletAppState.getPhysicsSpace().add(playerControl);

		//this.getMainNode().setUserData(Settings.ENTITY, this);

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
		//SCPGame.p("Player:" + this.getMainNode().getWorldTranslation());
		if (!game.isGameOver()) {
			if (walking) {
				time_until_next_footstep_sfx -= tpf;
				if (time_until_next_footstep_sfx <= 0) {
					AudioNode an = this.audio_node_footsteps.get(next_footstep_sound);
					try {
						an.playInstance();
					} catch (Exception ex) {
						// No speakers?
					}
					//SCPGame.p("Footstep " + next_footstep_sound);
					next_footstep_sound++;
					if (next_footstep_sound >= audio_node_footsteps.size()) {
						next_footstep_sound = 0;
					}
					time_until_next_footstep_sfx = FOOTSTEP_INTERVAL + (SpectrumArcade.rnd.nextFloat()/3);
				}
			} else {
				//time_until_next_footstep_sfx = 0;
			}
		} else {
			if (game.hasPlayerWon() == false) {
			}
		}

	}

/*
	@Override
	public void remove() {
		this.mainNode.removeFromParent();
		this.game.bulletAppState.getPhysicsSpace().remove(this.playerControl);

	}
*/
}
