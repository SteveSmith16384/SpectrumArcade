package com.scs.spectrumarcade.entities;

import java.util.Iterator;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.scs.spectrumarcade.IEntity;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;

public abstract class AbstractPhysicalEntity extends AbstractEntity {

	private static final float TURN_SPEED = 1f;

	protected Node mainNode, left_node, right_node;
	public RigidBodyControl srb;

	public AbstractPhysicalEntity(SpectrumArcade _game, String _name) {
		super(_game, _name);

		mainNode = new Node(name + "_MainNode");

		left_node = new Node("left_node");
		mainNode.attachChild(left_node);
		left_node.setLocalTranslation(-3, 0, 0);

		right_node = new Node("right_node");
		mainNode.attachChild(right_node);
		right_node.setLocalTranslation(3, 0, 0);

		mainNode.setUserData(Settings.ENTITY, this);

	}


	public Node getMainNode() {
		return mainNode;
	}


	@Override
	public void actuallyRemove() {
		this.mainNode.removeFromParent();
		if (srb != null) {
			this.game.bulletAppState.getPhysicsSpace().remove(this.srb);
		}
	}


	public void turnLeft(float tpf) {
		this.getMainNode().rotate(new Quaternion().fromAngleAxis(-1 * TURN_SPEED * tpf, Vector3f.UNIT_Y));
	}


	public void turnRight(float tpf) {
		this.getMainNode().rotate(new Quaternion().fromAngleAxis(1 * TURN_SPEED * tpf, Vector3f.UNIT_Y));
	}


	public float distance(AbstractPhysicalEntity o) {
		return distance(o.getMainNode().getWorldTranslation());
	}


	public float distance(Vector3f pos) {
		float dist = this.getMainNode().getWorldTranslation().distance(pos);
		return dist;
	}


	public boolean canSee(AbstractPhysicalEntity cansee) {
		Ray r = new Ray(this.getMainNode().getWorldTranslation(), cansee.getMainNode().getWorldTranslation().subtract(this.getMainNode().getWorldTranslation()).normalizeLocal());
		//synchronized (module.objects) {
		//if (go.collides) {
		CollisionResults results = new CollisionResults();
		Iterator<IEntity> it = game.entities.iterator();
		while (it.hasNext()) {
			IEntity o = it.next();
			if (o instanceof AbstractPhysicalEntity && o != this) {
				AbstractPhysicalEntity go = (AbstractPhysicalEntity)o;
				// if (go.collides) {
				if (go.getMainNode().getWorldBound() != null) {
					results.clear();
					try {
						go.getMainNode().collideWith(r, results);
					} catch (UnsupportedCollisionException ex) {
						System.out.println("Spatial: " + go.getMainNode());
						ex.printStackTrace();
					}
					if (results.size() > 0) {
						float go_dist = this.distance(cansee)-1;
						/*Iterator<CollisionResult> it = results.iterator();
							while (it.hasNext()) {*/
						CollisionResult cr = results.getClosestCollision();
						if (cr.getDistance() < go_dist) {
							return false;
						}
					}
				}
				//}
			}
		}
		return true;
	}



}
