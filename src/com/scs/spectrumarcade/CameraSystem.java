package com.scs.spectrumarcade;

import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

/**
 * This shoots a ray from the avatar backwards, and places the camera somewhere ni that ray depending on collisions.
 * @author StephenCS
 *
 */
public class CameraSystem {

	public enum View {First, Third, TopDown, Cinema };

	private SpectrumArcade game;
	private float followDist = 1f;
	private float shoulderAngleRads = 0f;
	private float fixedHeight = -1;
	private float heightOffset3rdPerson;
	private boolean camInCharge;
	private View currentView = View.First;

	// Temp vars
	private Vector3f dirTmp = new Vector3f();

	public CameraSystem(SpectrumArcade _game) {
		game = _game;
	}


	public void setupCam(float dist, float angleRads, boolean _camInCharge, float _heightOffset3rdPerson) { 
		this.followDist = dist;
		shoulderAngleRads = angleRads;
		camInCharge = _camInCharge;
		heightOffset3rdPerson = _heightOffset3rdPerson;
	}


	public void process(Camera cam, AbstractPhysicalEntity avatar) {
		IAvatar ava = (IAvatar)avatar;
		if (this.currentView == View.First) {
			// Position camera at node
			Vector3f vec = avatar.getMainNode().getWorldTranslation();
			cam.getLocation().x = vec.x;
			cam.getLocation().y = vec.y + ava.getCameraHeight();
			cam.getLocation().z = vec.z;
		} else if (this.currentView == View.Cinema) {
			Vector3f pos = avatar.getMainNode().getWorldTranslation();
			cam.lookAt(pos, Vector3f.UNIT_Y);
			cam.getLocation().x = (int)(pos.x / 15) * 15;
			cam.getLocation().y = (int)(pos.y / 10) * 10 + 5;
			cam.getLocation().z = (int)(pos.z / 15) * 15;
		} else {
			Vector3f avatarPos = avatar.getMainNode().getWorldTranslation().clone(); // todo - don't create each time - todo - use physics node!
			avatarPos.y += heightOffset3rdPerson;//avatar.avatarModel.getCameraHeight() + .1f;
			if (this.currentView == View.Third) {
				if (camInCharge) {
					dirTmp = cam.getDirection().mult(-1, dirTmp);
				} else {
					dirTmp = avatar.getMainNode().getWorldRotation().getRotationColumn(2).mult(-1, dirTmp);
				}
				if (shoulderAngleRads != 0) {
					Quaternion rotQ = new Quaternion();
					rotQ.fromAngleAxis(shoulderAngleRads, Vector3f.UNIT_Y);
					rotQ.multLocal(dirTmp).normalizeLocal();
				}
			} else if (this.currentView == View.TopDown) {
				//dirTmp = avatar.getMainNode().getWorldRotation().getRotationColumn(2).mult(-1, dirTmp);
				dirTmp.set(0, 1, 0);
			}

			Ray r = new Ray(avatarPos, dirTmp);
			if (this.currentView == View.Third) {
				r.setLimit(followDist);
			} else if (this.currentView == View.TopDown) {
				r.setLimit(10f);
			}
			CollisionResults res = new CollisionResults();
			int c = game.getRootNode().collideWith(r, res);
			boolean found = false;
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
						if (pe != avatar) {
							float dist = col.getDistance();
							if (dist > 0.1f) { // Move cam forward slightly
								dist -= 0.1f;
							}
							Vector3f add = dirTmp.multLocal(dist);
							cam.setLocation(avatarPos.add(add));
							found = true;
							break;
						}
					}
				}
			}

			if (!found) {
				Vector3f add = dirTmp.multLocal(r.limit);
				cam.setLocation(avatarPos.add(add));
			}

			if (fixedHeight > 0) {
				cam.getLocation().y = fixedHeight;
			}

			if (!camInCharge) {
				cam.lookAt(avatar.getMainNode().getWorldTranslation(), Vector3f.UNIT_Y);
			}

		}

		cam.update();
	}


	public void setView(View v) {
		currentView = v;
	}

}
