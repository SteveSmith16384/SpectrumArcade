package com.scs.spectrumarcade;

import com.scs.spectrumarcade.components.IAvatar;
import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.INotifiedOfCollision;
import com.scs.spectrumarcade.components.IPlayerCollectable;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;

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

		if (a instanceof IAvatar && b instanceof IPlayerCollectable) {
			Player_Collectable(game, (IAvatar)a, (IPlayerCollectable)b);
		}
		if (a instanceof IPlayerCollectable && b instanceof IAvatar) {
			Player_Collectable(game, (IAvatar)b, (IPlayerCollectable)a);
		}

		if (a instanceof IAvatar && b instanceof ICausesHarmOnContact) {
			Player_Harm(game, (IAvatar)a, (ICausesHarmOnContact)b);
		}
		if (a instanceof ICausesHarmOnContact && b instanceof IAvatar) {
			Player_Harm(game, (IAvatar)b, (ICausesHarmOnContact)a);
		}
	}
	
	
	private static void Player_Collectable(SpectrumArcade game, IAvatar player, IPlayerCollectable col) {
		col.collected(player);
		col.markForRemoval();
	}
	
	
	private static void Player_Harm(SpectrumArcade game, IAvatar player, ICausesHarmOnContact col) {
		Globals.p("Player hit by " + col);
		game.playerKilled(col.toString());
	}
	
	
}
