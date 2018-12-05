package com.scs.spectrumarcade.entities.antattack;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.levels.ArcadeRoom;
import com.scs.spectrumarcade.models.MinerModel;

import ssmith.util.RealtimeInterval;

public class Damsel extends AbstractPhysicalEntity implements INotifiedOfCollision, IProcessable {

	private static final float TURN_SPEED = 1f;

	private MinerModel model;
	private boolean followingPlayer;
	private BetterCharacterControl playerControl;

	private RealtimeInterval checkPosInterval = new RealtimeInterval(2000);
	private Vector3f prevPos = new Vector3f();
	private long dontWalkUntil = 0;

	public Damsel(SpectrumArcade _game, float x, float z) {
		super(_game, "Damsel");

		model = new MinerModel(game.getAssetManager());
		model.setLocalTranslation(0, 0, 0);
		this.mainNode.attachChild(model);

		mainNode.setLocalTranslation(x, 11, z);
		mainNode.updateModelBound();

		BoundingBox bb = (BoundingBox)model.getWorldBound();
		playerControl = new BetterCharacterControl(bb.getZExtent(), bb.getYExtent()*2, 10f);
		playerControl.setJumpForce(new Vector3f(0, 7f, 0)); 
		playerControl.setGravity(new Vector3f(0, 1f, 0));
		this.getMainNode().addControl(playerControl);

		this.model.idleAnim();

	}


	@Override
	public void process(float tpfSecs) {
		this.turnTowardsPlayer();
		if (followingPlayer) {
			if (this.distance(game.player) > 3f) {
				if (dontWalkUntil < System.currentTimeMillis()) {
					this.model.walkAnim();
					moveFwds();
					if (checkPosInterval.hitInterval()) {
						if (this.mainNode.getWorldTranslation().distance(this.prevPos) < .5f) {
							Globals.p("Damsel stuck; jumping");
							//playerControl.setWalkDirection(new Vector3f());

							Vector3f walkDirection = this.playerControl.getViewDirection();
							playerControl.setWalkDirection(walkDirection.mult(-2.4f));

							playerControl.setJumpForce(new Vector3f(0, 50f, 0)); 
							this.playerControl.jump();
							//this.model.jumpAnim();
							//dontWalkUntil = System.currentTimeMillis() + 500;
						} 
						prevPos.set(this.mainNode.getWorldTranslation());
					}
				}
				// Check if reached the exit
				Vector3f pos = this.getMainNode().getWorldTranslation();
				if (pos.z > 127) {
					// Game complete
					Globals.p("Damsel has reached the end");
					game.setNextLevel(ArcadeRoom.class, -1);
				}
			} else {
				playerControl.setWalkDirection(new Vector3f());
				this.model.idleAnim();
			}
		}
	}


	private void turnTowardsPlayer() {
		float leftDist = this.leftNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		float rightDist = this.rightNode.getWorldTranslation().distance(game.player.getMainNode().getWorldTranslation()); 
		if (leftDist > rightDist) {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
		} else {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
		}
		this.playerControl.setViewDirection(mainNode.getWorldRotation().getRotationColumn(2));
	}


	private void moveFwds() {
		Vector3f walkDirection = this.playerControl.getViewDirection();
		playerControl.setWalkDirection(walkDirection.mult(2.4f));

	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == game.player) {
			followingPlayer = true;
		} else if (collidedWith instanceof VoxelTerrainEntity) {
			//this.playerControl.jump();
			//this.model.jumpAnim();
		}

	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		if (playerControl != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.playerControl);
		}

	}


}
