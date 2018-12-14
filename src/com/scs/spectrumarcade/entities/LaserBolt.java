package com.scs.spectrumarcade.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.models.BeamLaserModel;

public class LaserBolt extends AbstractPhysicalEntity implements IProcessable, INotifiedOfCollision {

	private static final float SPEED = 10f;
	private static final float LASER_DIAM = 0.1f;
	private static final float LENGTH = .5f;

	private long removeTime = System.currentTimeMillis() + 4000;

	public LaserBolt(SpectrumArcade _game, float x, float y, float z, Vector3f dir) {
		super(_game, "LaserBolt");

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
		Spatial laserNode = BeamLaserModel.Factory(game.getAssetManager(), origin, origin.add(dir.mult(LENGTH)), ColorRGBA.Pink, true, "Textures/cells3.png", LASER_DIAM, true);
		//laserNode.setShadowMode(ShadowMode.Cast);
		this.mainNode.attachChild(laserNode);

		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1f);
		mainNode.addControl(srb);
		srb.setGravity(new Vector3f());
	}


	@Override
	public void process(float tpfSecs) {
		if (removeTime < System.currentTimeMillis()) {
			this.markForRemoval();
		}
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		this.markForRemoval();
	}

}
