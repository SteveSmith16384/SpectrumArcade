package com.scs.spectrumarcade.entities.stockcarchamp;

import java.util.ArrayList;
import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Globals;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.components.IProcessable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.VoxelTerrainEntity;
import com.scs.spectrumarcade.jme.JMEAngleFunctions;
import com.scs.spectrumarcade.levels.StockCarChamp3DLevel;

import ssmith.util.RealtimeInterval;

public class StockCarAICar extends AbstractStockCar implements IProcessable {

	private final static float accelerationForce = 500.0f;

	private StockCarChamp3DLevel level;
	private RealtimeInterval checkNodesInt = new RealtimeInterval(200);
	private ArrayList<Vector3f> waypoints;

	private int currentWayPoint = 0;
	private boolean reversing = false;
	private Vector3f prevPos = new Vector3f();
	private long reverseUntil;

	public StockCarAICar(SpectrumArcade _game, StockCarChamp3DLevel _level, float x, float y, float z, Vector3f lookAt, int texNum) {
		super(_game, "StockCarAICar", x, y, z, lookAt, false, texNum);

		level = _level;
		waypoints = level.waypoints;
	}


	@Override
	public void process(float tpfSecs) {
		super.process(tpfSecs);

		if (checkNodesInt.hitInterval()) {
			
			if (!reversing) {
				boolean canSeeWP = this.isValidWaypoint(this.currentWayPoint);
				if (!canSeeWP) {
					this.emergencySetNextWaypoint();
					return;
				}
				float dist = this.getDistToWP(this.currentWayPoint);
				if (dist < 8) {
					int nextWP = this.getNextWaypoint(this.currentWayPoint);
					boolean canSeenextWP = this.isValidWaypoint(nextWP);
					if (canSeenextWP) {
						Globals.p("AI now going to WP #" + (currentWayPoint+2));
						this.currentWayPoint = nextWP;
					}
				}

				// Check if we're stuck
				float distMoved = prevPos.distance(this.mainNode.getWorldTranslation()); 
				if (distMoved < 0.01f) {
					Globals.p("Stuck!  Reversing...");
					this.startReversing();
				} else {
					vehicle.accelerate(accelerationForce);
					this.turnTowardsPoint(this.waypoints.get(this.currentWayPoint));

				}
				prevPos.set(this.mainNode.getWorldTranslation());

			} else {
				if (this.reverseUntil < System.currentTimeMillis()) {
					this.reversing = false;
				} else {
					vehicle.accelerate(-accelerationForce);
				}
			}
		}
	}
	
	
	private int getNextWaypoint(int wp) {
		if (wp < this.waypoints.size()-1) {
			return wp+1;
		} else {
			return 0;
		}
		
	}


	private int getPrevWaypoint(int wp) {
		if (wp > 0) {
			return wp - 1;
		} else {
			return this.waypoints.size()-1;
		}
		
	}


	private void emergencySetNextWaypoint() {
		// Get highest node we can see, starting with the last one
		boolean found = false;
		for (int i=waypoints.size()-1; i>=0 ; i--) {
			if (this.isValidWaypoint(i)) {
				// If we can see the last waypoint, check if we can see the first
				/*if (i == waypoints.length-1) {
					if (this.isValidWaypoint(0)) {
						//Globals.p("Going to the first waypoint");
						i = 0;
					}
				}
				turnTowardsPoint(waypoints[i]);
				vehicle.accelerate(accelerationForce);*/
				found = true;
				this.currentWayPoint = i;
				break;
			}
		}
		if (!found) {
			Globals.p("Can't see any waypoints!");
			this.startReversing();
		}
	}

	
	private void startReversing() {
		Globals.p("Reversing");
		this.reversing = true;
		this.reverseUntil = System.currentTimeMillis() + 3000;

	}

	
	private boolean isValidWaypoint(int i) {
		Vector3f pos = this.waypoints.get(i);
		if (this.canSeeWaypoint(pos)) {
			float ang = JMEAngleFunctions.getAngleBetween(this.getMainNode(), pos);
			if (ang < Math.PI) { // It's on front of us
				Globals.p("AI can see waypoint #" + (i+2) + " (array " + i + ")");
				return true;
			}
		}
		return false;
	}


	private float getDistToWP(int i) {
		Vector3f pos = this.waypoints.get(i);
		return this.mainNode.getWorldTranslation().distance(pos); 

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
