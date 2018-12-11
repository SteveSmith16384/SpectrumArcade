package com.scs.spectrumarcade.entities.ericandfloaters;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.ForceData;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class Floater extends AbstractPhysicalEntity implements ICausesHarmOnContact, INotifiedOfCollision, IProcessable, PhysicsTickListener {

	public static final float SPEED = 5f;
	public static final long TURN_INTERVAL = 2000;

	private Geometry geometry;
	private Vector3f dir;
	private long timeUntilNextTurn = 0;
	private Vector3f prevPos = new Vector3f();
	private RealtimeInterval checkPosInterval = new RealtimeInterval(2000);

	private boolean killed = false;
	private long timeToRemove = 0;

	private boolean isAngry = false;
	private RealtimeInterval checkAngerInterval = new RealtimeInterval(3000);

	public Floater(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Floater");

		Mesh sphere = new Sphere(16, 16, EricAndTheFloatersLevel.SEGMENT_SIZE*.4f, true, false);
		geometry = new Geometry("FloaterSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/floater.png");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(2);
		mainNode.addControl(srb);

		dir = JMEAngleFunctions.getRandomDirection_4();

	}


	@Override
	public void process(float tpfSecs) {
		if (killed) {
			if (this.timeToRemove < System.currentTimeMillis()) {
				this.markForRemoval();
			}
			return;
		}

		if (!isAngry) {
			if (checkPosInterval.hitInterval()) {
				if (this.mainNode.getWorldTranslation().distance(this.prevPos) < .5f) {
					dir = dir.mult(-1);//JMEAngleFunctions.getRandomDirection_8();
					Globals.p("Floater stuck, changing dir to "  + dir);
				}
				prevPos.set(this.mainNode.getWorldTranslation());
			}
			if (timeUntilNextTurn < System.currentTimeMillis()) {
				dir = JMEAngleFunctions.getRandomDirection_4();//.mult(0.5f);
				timeUntilNextTurn = System.currentTimeMillis() + TURN_INTERVAL;
			}
			if (NumberFunctions.rnd(1, 100) == 1) {
				this.isAngry = true;
				JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/floater_red.png");
				checkAngerInterval.restartTimer();
			}
		} else {
			this.dir = game.player.getMainNode().getWorldTranslation().subtract(this.mainNode.getWorldTranslation().normalizeLocal());
			if (checkAngerInterval.hitInterval()) {
				this.isAngry = false;
				JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/floater.png");
			}
		}

	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		/*if (collidedWith instanceof FloorOrCeiling == false) {
			//Globals.p("Floater collided with " + collidedWith + " and is turning");
		}		*/
	}


	public void killed() {
		if (killed) {
			return;
		}
		killed = true;
		this.timeToRemove = System.currentTimeMillis() + 3000; 
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		
	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		Vector3f force = dir.mult(SPEED);
		this.srb.setLinearVelocity(force);
	}

}
