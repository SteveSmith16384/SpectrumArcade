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

	protected Node mainNode, leftNode, rightNode;
	public RigidBodyControl srb;

	public AbstractPhysicalEntity(SpectrumArcade _game, String _name) {
		super(_game, _name);

		mainNode = new Node(name + "_MainNode");

		leftNode = new Node("left_node");
		mainNode.attachChild(leftNode);
		leftNode.setLocalTranslation(-3, 0, 0);

		rightNode = new Node("right_node");
		mainNode.attachChild(rightNode);
		rightNode.setLocalTranslation(3, 0, 0);

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


	public float distance(AbstractPhysicalEntity o) {
		return distance(o.getMainNode().getWorldTranslation());
	}


	public float distance(Vector3f pos) {
		float dist = this.getMainNode().getWorldTranslation().distance(pos);
		return dist;
	}


}
