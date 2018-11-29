package com.scs.spectrumarcade.entities.stockcarchamp;

import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.levels.StockCarChamp3DLevel;

import ssmith.util.RealtimeInterval;

public class StockCarAICar extends AbstractStockCar implements IProcessable {

	private StockCarChamp3DLevel level;
	private RealtimeInterval checkNodesInt = new RealtimeInterval(2000);

	protected final static float accelerationForce = 1000.0f;
	protected final float brakeForce = 100.0f;

	public StockCarAICar(SpectrumArcade _game, StockCarChamp3DLevel _level, float x, float y, float z) {
		super(_game, "StockCarAICar", x, y, z);

		level = _level;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkNodesInt.hitInterval()) {

			//this.canSeeWaypoint(new Vector3f(this.getMainNode().getWorldTranslation()).add(.01f, 0, 0)); // todo - delete
			//this.canSeeWaypoint(game.player.getMainNode().getWorldTranslation()); // todo - delete

			// Get highest node we can see
			Vector3f[] waypoints = level.waypoints;
			//for (int i=waypoints.length-1; i>=0 ; i--) { // todo -
			int i=0;
			if (this.canSeeWaypoint(waypoints[i])) {
				turnTowardsPoint(waypoints[i]);
				vehicle.accelerate(accelerationForce);
				//break;
			}
			//}

		}
	}


	private void turnTowardsPoint(Vector3f o) {
		float ang = JMEAngleFunctions.getAngleBetween(this.getMainNode(), o);
		if (ang > Math.PI/2) { // It's behind us!
			vehicle.steer(1f);
		} else {
			float leftDist = this.leftNode.getWorldTranslation().distance(o); 
			float rightDist = this.rightNode.getWorldTranslation().distance(o); 
			float diff = Math.abs(leftDist - rightDist);
			if (diff != 0) {
				if (leftDist > rightDist) { // Turn right
					vehicle.steer(-.5f);
				} else { // Turn left
					vehicle.steer(.5f);
				}
			}
		}
	}


	private boolean canSeeWaypoint(Vector3f o) {
		Ray r = new Ray(o, this.getMainNode().getWorldTranslation().add(0,  .5f,  0).subtract(o).normalizeLocal());
		//Ray r = new Ray(o, o.subtract(this.getMainNode().getWorldTranslation()).normalizeLocal());
		r.setLimit(this.getMainNode().getWorldTranslation().subtract(o).length());
		CollisionResults res = new CollisionResults();
		int c = game.getRootNode().collideWith(r, res);
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
						return true;
					} else if (pe instanceof VoxelTerrainEntity) {
						return false;
					}
				}
			}
		}
		return true;
	}




}
