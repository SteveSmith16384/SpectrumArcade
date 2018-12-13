package com.scs.spectrumarcade.entities;

import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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


	public Spatial getPhysicsNode() {
		return mainNode;
	}


	@Override
	public void actuallyAdd() {
		//AbstractPhysicalEntity ape = (AbstractPhysicalEntity)e;
		game.getRootNode().attachChild(getMainNode());
		game.bulletAppState.getPhysicsSpace().add(getPhysicsNode());
		if (this instanceof PhysicsTickListener) {
			game.bulletAppState.getPhysicsSpace().addTickListener((PhysicsTickListener)this);
		}
		
	}


	@Override
	public void actuallyRemove() {
		if (!this.isMarkedForRemoval()) {
			throw new RuntimeException("You must mark an item for removal!");
		}
		
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
