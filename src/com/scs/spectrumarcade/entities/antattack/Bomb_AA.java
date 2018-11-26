package com.scs.spectrumarcade.entities.antattack;

import java.util.Iterator;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.entities.ericandfloaters.ExplosionShard;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

import ssmith.lang.NumberFunctions;

public class Bomb_AA extends AbstractPhysicalEntity implements IProcessable {

	public static final float SPEED = 10f;

	private long explodeTime = System.currentTimeMillis() + 3000;

	public Bomb_AA(SpectrumArcade _game, float x, float y, float z) {
		super(_game, "Bomb_AA");
		
		if (y < .3f) {
			y = .3f;
		}

		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry geometry = new Geometry("BombSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/black.png");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(.1f);
		mainNode.addControl(srb);
		
	}


	@Override
	public void process(float tpfSecs) {
		if (explodeTime < System.currentTimeMillis()) {
			for (IEntity e : game.entities) {
				if (e instanceof Ant) {
					checkForHitAnts((Ant)e);
				}
			}

			// Shards
			for (int i=0 ; i<10 ; i++) {
				float x = this.getMainNode().getWorldTranslation().x + NumberFunctions.rndFloat(-.2f,  .2f);
				float y = this.getMainNode().getWorldTranslation().y + NumberFunctions.rndFloat(-.2f,  .2f);
				float z = this.getMainNode().getWorldTranslation().z + NumberFunctions.rndFloat(-.2f,  .2f);
				ExplosionShard shard = new ExplosionShard(game, x, y, z, .1f, "Textures/antattack.png");
				game.addEntity(shard);
			}
			this.markForRemoval();
		}
	}


	private void checkForHitAnts(Ant ant) {		
		Ray r = new Ray(this.mainNode.getWorldTranslation(), ant.getMainNode().getWorldTranslation().subtract(this.mainNode.getWorldTranslation()).normalizeLocal());
		r.setLimit(EricAndTheFloatersLevel.SEGMENT_SIZE*2);
		CollisionResults res = new CollisionResults();
		int c = game.getRootNode().collideWith(r, res);
		//boolean found = false;
		if (c > 0) {
			Iterator<CollisionResult> it = res.iterator();
			while (it.hasNext()) {
				CollisionResult col = it.next();
				if (col.getDistance() > r.getLimit()) { // Keep this in! collideWith() seems to ignore it.
					break;
				}
				Spatial s = col.getGeometry();
				while (s.getUserData(Settings.ENTITY) == null) {
					s = s.getParent();
					if (s == null) {
						break;
					}
				}
				if (s != null && s.getUserData(Settings.ENTITY) != null) {
					AbstractPhysicalEntity pe = (AbstractPhysicalEntity)s.getUserData(Settings.ENTITY);
					if (pe == this) {
						// Continue - ignore bomb
					} else if (pe == ant) {
						ant.hitByBomb(); 
						break;
					} else if (pe instanceof VoxelTerrainEntity) {
						// Stop checking
						break;
					} else if (pe == game.player) {
						game.playerKilled();
					}
				}
			}
		}		
	}


}