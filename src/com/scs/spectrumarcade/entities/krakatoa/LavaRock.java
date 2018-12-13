package com.scs.spectrumarcade.entities.krakatoa;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;

public class LavaRock extends AbstractPhysicalEntity implements ICausesHarmOnContact, PhysicsTickListener, INotifiedOfCollision {

	private boolean launched = false;

	public LavaRock(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "LavaRock");

		Spatial geometry = game.getAssetManager().loadModel("Models/RocksFlowersGrassPack/OBJ/rock3.obj");
		//JMEAngleFunctions.turnOnXAxis(geometry, (float)Math.PI);
		JMEModelFunctions.scaleModelToWidth(geometry, 1f);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/blocks/lava.png");
		geometry.setShadowMode(ShadowMode.CastAndReceive);

		JMEModelFunctions.moveYOriginTo(geometry, 0f);

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);
		
	}


	@Override
	public float getDamageCaused() {
		return 1;
	}


	@Override
	public void physicsTick(PhysicsSpace arg0, float arg1) {
		// TODO Auto-generated method stub

	}


	@Override
	public void prePhysicsTick(PhysicsSpace arg0, float arg1) {
		if (!launched) {
			launched = true;
			Vector3f force = new Vector3f(0.1f, 1, 0).mult(10); // todo
			srb.setLinearVelocity(force);
			//Globals.p("Force=" + force);
		}

	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity collidedWith) {
		this.markForRemoval();
	}

}
