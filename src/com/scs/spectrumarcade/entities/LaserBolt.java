package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.models.BeamLaserModel;

public class LaserBolt extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision, ICausesHarmOnContact {

	private static final float SPEED = 10f;
	private static final float LASER_DIAM = 0.1f;
	private static final float LENGTH = .5f;

	private AbstractPhysicalEntity shooter; 
	private long removeTime = System.currentTimeMillis() + 4000;
	private Vector3f dir;

	public LaserBolt(SpectrumArcade _game, AbstractPhysicalEntity _shooter, float x, float y, float z, Vector3f _dir) {
		super(_game, "LaserBolt");
		
		shooter = _shooter;
		dir = _dir.normalize();
/*
		if (y < .3f) {
			y = .3f;
		}
/*	
		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry geometry = new Geometry("ThrowingAxe", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/black.png");
*/
		Vector3f origin = Vector3f.ZERO;
		Spatial laserNode = BeamLaserModel.Factory(game.getAssetManager(), origin, origin.add(dir.mult(LENGTH)), ColorRGBA.Pink, true, "Textures/yellowsun.jpg", LASER_DIAM, true);
		//laserNode.setShadowMode(ShadowMode.Cast);
		this.mainNode.attachChild(laserNode);

		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);
		//srb.setGravity(new Vector3f());
		srb.setKinematic(true);
	}


	@Override
	public void process(float tpfSecs) {
		if (removeTime < System.currentTimeMillis()) {
			this.markForRemoval();
		} else {
			this.getMainNode().move(dir.mult(10 * tpfSecs));
		}
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		if (collidedWith == shooter) {
			return;
		}
		this.markForRemoval();
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}

}
