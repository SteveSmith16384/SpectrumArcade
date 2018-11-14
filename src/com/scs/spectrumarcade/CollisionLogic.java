package com.scs.spectrumarcade;

import com.scs.spectrumarcade.components.ICausesHarmOnContact;
import com.scs.spectrumarcade.components.IPlayerCollectable;
import com.scs.spectrumarcade.entities.AbstractEntity;
import com.scs.spectrumarcade.entities.Player;

public class CollisionLogic {

	public static void collision(SpectrumArcade game, AbstractEntity a, AbstractEntity b) {
		if (a instanceof Player && b instanceof IPlayerCollectable) {
			Player_Collectable(game, (Player)a, (IPlayerCollectable)b);
		}
		if (a instanceof IPlayerCollectable && b instanceof Player) {
			Player_Collectable(game, (Player)b, (IPlayerCollectable)a);
		}

		if (a instanceof Player && b instanceof ICausesHarmOnContact) {
			Player_Harm(game, (Player)a, (ICausesHarmOnContact)b);
		}
		if (a instanceof ICausesHarmOnContact && b instanceof Player) {
			Player_Harm(game, (Player)b, (ICausesHarmOnContact)a);
		}
	}
	
	
	private static void Player_Collectable(SpectrumArcade game, Player player, IPlayerCollectable col) {
		col.remove();
	}
	
	
	private static void Player_Harm(SpectrumArcade game, Player player, ICausesHarmOnContact col) {
		//Settings.p()
		// todo
	}
	
	
}
