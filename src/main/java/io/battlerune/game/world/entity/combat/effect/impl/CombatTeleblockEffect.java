package io.battlerune.game.world.entity.combat.effect.impl;

import io.battlerune.game.world.entity.combat.effect.CombatEffect;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.prayer.Prayer;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.net.packet.out.SendWidget;

/**
 * The combat effect applied when a player needs to be teleblocked.
 *
 * @author lare96 <http://github.com/lare96>
 */
public final class CombatTeleblockEffect extends CombatEffect {

	/** Creates a new {@link CombatTeleblockEffect}. */
	public CombatTeleblockEffect() {
		super(50);
	}

	@Override
	public boolean apply(Mob mob) {
		if (mob.isPlayer()) {
			Player player = (Player) mob;
			if (player.isTeleblocked()) {
				return false;
			}

			if (player.prayer.isActive(Prayer.PROTECT_FROM_MAGIC)) {
				player.teleblock(250);
				player.send(new SendWidget(SendWidget.WidgetType.TELEBLOCK, 150));
			} else {
				player.teleblock(500);
				player.send(new SendWidget(SendWidget.WidgetType.TELEBLOCK, 300));
			}

			player.send(new SendMessage("You have just been tele-blocked!"));
			return true;
		}
		return false;
	}

	@Override
	public boolean removeOn(Mob mob) {
		return false;
	}

	@Override
	public void process(Mob mob) {
	}

	@Override
	public boolean onLogin(Mob mob) {
		if (mob.isPlayer()) {
			Player player = (Player) mob;
			if (player.isTeleblocked()) {
				return true;
			}
		}
		return false;
	}
}
