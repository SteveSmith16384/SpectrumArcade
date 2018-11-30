package com.scs.spectrumarcade.entities.stockcarchamp;

import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.IProcessable;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.levels.StockCarChamp3DLevel;

import ssmith.util.RealtimeInterval;

public class StockCarAICar extends AbstractStockCar implements IProcessable {

	private final static float accelerationForce = 500.0f;

	private StockCarChamp3DLevel level;
	private RealtimeInterval checkNodesInt = new RealtimeInterval(200);

	private boolean reversing = false;
	private Vector3f prevPos = new Vector3f();
	private long reverseUntil;

	public StockCarAICar(SpectrumArcade _game, StockCarChamp3DLevel _level, float x, float y, float z) {
		super(_game, "StockCarAICar", x, y, z);

		level = _level;
	}


	@Override
	public void process(float tpfSecs) {
		if (checkNodesInt.hitInterval()) {

			if (!reversing) {
				// Get highest node we can see, starting with the last one
				boolean found = false;
				Vector3f[] waypoints = level.waypoints;
				for (int i=waypoints.length-1; i>=0 ; i--) {
					if (this.isValidWaypoint(i)) {
						// If we can see the last waypoint, check if we can see the first
						if (i == waypoints.length-1) {
							if (this.isValidWaypoint(0)) {
								Globals.p("Going to the first waypoint");
								i = 0;
							}
						}
						turnTowardsPoint(waypoints[i]);
						vehicle.accelerate(accelerationForce);
						found = true;
						break;
					}
				}
				if (!found) {
					Globals.p("Can't see any waypoints!");
				}
				
				if (prevPos.distance(this.mainNode.getWorldTranslation()) < 0.1f) {
					Globals.p("Stuck!  Reversing...");
					this.reversing = true;
					this.reverseUntil = System.currentTimeMillis() + 3000;
				}
				prevPos.set(this.mainNode.getWorldTranslation());
				
			} else {
				if (this.reverseUntil < System.currentTimeMillis()) {
					this.reversing = false;
					vehicle.accelerate(accelerationForce * 2);
				} else {
				vehicle.accelerate(-accelerationForce);
			}
			}
		}
	}


	private boolean isValidWaypoint(int i) {
		Vector3f pos = level.waypoints[i];
		if (this.canSeeWaypoint(pos)) {
			float ang = JMEAngleFunctions.getAngleBetween(this.getMainNode(), pos);
			if (ang < Math.PI) { // It's on front of us
				Globals.p("AI can see waypoint #" + (i+2));
				return true;
			}
		}
		return false;
	}


	private void turnTowardsPoint(Vector3f o) {
		float ang = JMEAngleFunctions.getAngleBetween(this.getMainNode(), o);
		if (ang > Math.PI) { // It's behind us!
			Globals.p("Waypoint is behind AI!");
			vehicle.steer(1f);
		} else {
			float leftDist = this.leftNode.getWorldTranslation().distance(o); 
			float rightDist = this.rightNode.getWorldTranslation().distance(o); 
			float diff = Math.abs(leftDist - rightDist);
			if (diff != 0) {
				if (leftDist > rightDist) { // Turn right
					Globals.p("Steering right");
					vehicle.steer(.3f);
				} else { // Turn left
					Globals.p("Steering left");
					vehicle.steer(-.3f);
				}
			}
		}
	}


	private boolean canSeeWaypoint(Vector3f o) { // Ray is from the waypoint to the car
		Ray r = new Ray(o, this.getMainNode().getWorldTranslation().add(0,  .5f,  0).subtract(o).normalizeLocal());
		//Ray r = new Ray(o, o.subtract(this.getMainNode().getWorldTranslation()).normalizeLocal());
		r.setLimit(this.getMainNode().getWorldTranslation().subtract(o).length());
		CollisionResults res = new CollisionResults(); //level.terrainUDG.getMainNode().getWorldBound();
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
