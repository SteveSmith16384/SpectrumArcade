package com.scs.spectrumarcade.abilities;

import java.util.Iterator;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.spectrumarcade.Settings;
import com.scs.spectrumarcade.SpectrumArcade;
import com.scs.spectrumarcade.entities.AbstractPhysicalEntity;
import com.scs.spectrumarcade.entities.LaserBolt;

public class LaserRifle extends AbstractAbility implements IAbility {

	private static final long SHOT_INTERVAL = 1000;

	private long nextShotTime = 0;
	private AbstractPhysicalEntity shooter; 

	public LaserRifle(SpectrumArcade game, AbstractPhysicalEntity _shooter) {
		super(game);

		shooter = _shooter;
	}


	@Override
	public void activate() {
		if (nextShotTime < System.currentTimeMillis()) {
			nextShotTime = System.currentTimeMillis() + SHOT_INTERVAL;

			// Ray to see where the target is
			Ray r = new Ray(game.getCamera().getLocation(), game.getCamera().getDirection());
			//Ray r = new Ray(this.mainNode.getWorldTranslation(), ant.getMainNode().getWorldTranslation().subtract(this.mainNode.getWorldTranslation()).normalizeLocal());
			//r.setLimit(DAM_RANGE);
			CollisionResults res = new CollisionResults();
			int c = game.getRootNode().collideWith(r, res);
			if (c > 0) {
				Iterator<CollisionResult> it = res.iterator();
				CollisionResult col = null;
				while (it.hasNext()) {
					col = it.next();
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
						if (pe == shooter) {
							// Continue
						} else {
							break;
						}
						/*						} else if (pe instanceof FloorOrCeiling) {
							break;
						} else if (pe instanceof VoxelTerrainEntity) {
							// Stop checking
							break;
						} else if (pe == game.player) {
							//game.playerKilled();  not for now
						}*/
					}
				}

				Vector3f startPos = game.player.getMainNode().getWorldTranslation().clone();
				startPos.y += Settings.PLAYER_HEIGHT;
				Vector3f dir = col.getContactPoint().subtract(startPos).normalizeLocal();
				startPos.addLocal(dir.mult(2));

				LaserBolt laser = new LaserBolt(game, shooter, startPos.x, startPos.y, startPos.z, dir);
				game.addEntity(laser);
			}

		}
	}


}
