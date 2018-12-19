package com.scs.spectrumarcade.entities.ericandfloaters;

import java.awt.Point;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.jme.JMEModelFunctions;
import com.scs.spectrumarcade.levels.EricAndTheFloatersLevel;

import ssmith.lang.NumberFunctions;

public class Bomb_EATF extends AbstractPhysicalEntity implements IProcessable {//, PhysicsTickListener {

	//public static final float SPEED = 10f;

	private long explodeTime = System.currentTimeMillis() + 3000;
	//private boolean launched = false;
	private int range;

	public Bomb_EATF(SpectrumArcade _game, float x, float y, float z, int _range) {
		super(_game, "Bomb");

		range = _range;
		
		if (y < .3f) {
			y = .3f;
		}

		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry geometry = new Geometry("BombSphere", sphere);
		geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/ericandthefloaters/floater.png");

		this.mainNode.attachChild(geometry);
		mainNode.setLocalTranslation(x, y, z);
		mainNode.updateModelBound();

		srb = new RigidBodyControl(1);
		mainNode.addControl(srb);

	}


	@Override
	public void process(float tpfSecs) {
		if (explodeTime < System.currentTimeMillis()) {
			
			Point p = this.getMapPos(EricAndTheFloatersLevel.SEGMENT_SIZE);
			for (int z=p.y-range ; z<= p.y+range ; z++) {
				for (int x=p.x-range ; x<=p.x+range ; x++) {
					if (x == p.x || z == p.y) {
						ExplosionWall expl = new ExplosionWall(game, x, z);
						game.addEntity(expl);
					}
				}
			}
			
			
			/*for (IEntity e : game.entities) {
				if (e instanceof Floater) {
					checkForHitFloaters((Floater)e);
				} else if (e instanceof DestroyableWall) {
					checkForHitFloaters((DestroyableWall)e);
				}
			}
/*
			// Shards
			for (int i=0 ; i<5 ; i++) {
				float x = this.getMainNode().getWorldTranslation().x + NumberFunctions.rndFloat(-.2f,  .2f);
				float y = this.getMainNode().getWorldTranslation().y + NumberFunctions.rndFloat(-.2f,  .2f);
				float z = this.getMainNode().getWorldTranslation().z + NumberFunctions.rndFloat(-.2f,  .2f);
				ExplosionShard shard = new ExplosionShard(game, x, y, z, .4f, "Textures/ericwall.png");
				game.addEntity(shard);
			}*/
			this.explosion(this.getMainNode());
			this.markForRemoval();
		}
	}

/*
	private void checkForHitFloaters(AbstractPhysicalEntity entity) {		
		Ray r = new Ray(this.mainNode.getWorldTranslation(), entity.getMainNode().getWorldTranslation().subtract(this.mainNode.getWorldTranslation()).normalizeLocal());
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
					} else if (pe == entity) {
						entity.markForRemoval();
						this.explosion(entity.getMainNode());
						break;
					} else if (pe instanceof VoxelTerrainEntity) {
						// Stop checking
						break;
					} else if (pe == game.player) {
						game.playerKilled(this.name);
					}
				}
			}
		}		
	}
*/
	
	private void explosion(Node node) {
		// Shards
		for (int i=0 ; i<5 ; i++) {
			float x = node.getWorldTranslation().x + NumberFunctions.rndFloat(-.2f,  .2f);
			float y = node.getWorldTranslation().y + NumberFunctions.rndFloat(-.2f,  .2f);
			float z = node.getWorldTranslation().z + NumberFunctions.rndFloat(-.2f,  .2f);
			ExplosionShard shard = new ExplosionShard(game, x, y, z, .4f, "Textures/ericandthefloaters/ericwall.png");
			game.addEntity(shard);
		}
		this.markForRemoval();

	}
		

}

