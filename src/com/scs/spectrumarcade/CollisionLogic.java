package com.scs.spectrumarcade;

import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IPlayerCollectable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.WalkingPlayer;

public class CollisionLogic {

	public static void collision(SpectrumArcade game, AbstractPhysicalEntity a, AbstractPhysicalEntity b) {
		if (a.getMainNode().getParent() == null) { // Prevent collisions after changing level
			return;
		}
		if (b.getMainNode().getParent() == null) { // Prevent collisions after changing level
			return;
		}
		if (a instanceof INotifiedOfCollision) {
			INotifiedOfCollision anoc = (INotifiedOfCollision)a;
			anoc.notifiedOfCollision(b);
		}
		if (b instanceof INotifiedOfCollision) {
			INotifiedOfCollision anoc = (INotifiedOfCollision)b;
			anoc.notifiedOfCollision(a);
		}

		if (a instanceof WalkingPlayer && b instanceof IPlayerCollectable) {
			Player_Collectable(game, (WalkingPlayer)a, (IPlayerCollectable)b);
		}
		if (a instanceof IPlayerCollectable && b instanceof WalkingPlayer) {
			Player_Collectable(game, (WalkingPlayer)b, (IPlayerCollectable)a);
		}

		if (a instanceof WalkingPlayer && b instanceof ICausesHarmOnContact) {
			Player_Harm(game, (WalkingPlayer)a, (ICausesHarmOnContact)b);
		}
		if (a instanceof ICausesHarmOnContact && b instanceof WalkingPlayer) {
			Player_Harm(game, (WalkingPlayer)b, (ICausesHarmOnContact)a);
		}
	}
	
	
	private static void Player_Collectable(SpectrumArcade game, WalkingPlayer player, IPlayerCollectable col) {
		col.collected(player);
		col.markForRemoval();
	}
	
	
	private static void Player_Harm(SpectrumArcade game, WalkingPlayer player, ICausesHarmOnContact col) {
		game.playerKilled();
	}
	
	
}
