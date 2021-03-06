package io.battlerune.content.activity.impl.allvsone;

import io.battlerune.content.activity.ActivityListener;
import io.battlerune.game.world.entity.combat.CombatType;
import io.battlerune.game.world.entity.combat.hit.Hit;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.npc.Npc;
import io.battlerune.util.Utility;

/**
 * The {@link Adam_#6723} combat listener for all mobs in the activity.
 *
 * @author Adam_#6723
 */
public class AllVsOneCavesListener extends ActivityListener<AllVsOne> {

	/**
	 * Constructs a new {@code FightCavesListener} object for a specific
	 * {@link FightCaves} activity.
	 */
	public AllVsOneCavesListener(AllVsOne minigame) {
		super(minigame);
	}

	@Override
	public void block(Mob attacker, Mob defender, Hit hit, CombatType combatType) {
		if (!defender.isNpc())
			return;
		if (defender.id != 3127)
			return;
		if (Utility.getPercentageAmount(defender.getCurrentHealth(), defender.getMaximumHealth()) > 49)
			return;
		for (Npc npc : activity.npcs) {
			if (npc.id == 3128
					&& (npc.getCombat().inCombatWith(attacker) || Utility.withinDistance(defender, npc, 5))) {
				defender.heal(1);
			}
		}
	}

	@Override
	public void onDeath(Mob attacker, Mob defender, Hit hit) {
		activity.handleDeath(defender);
	}
}
