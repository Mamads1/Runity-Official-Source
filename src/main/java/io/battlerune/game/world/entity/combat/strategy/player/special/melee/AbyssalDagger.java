package io.battlerune.game.world.entity.combat.strategy.player.special.melee;

import io.battlerune.game.Animation;
import io.battlerune.game.Graphic;
import io.battlerune.game.UpdatePriority;
import io.battlerune.game.world.entity.combat.hit.CombatHit;
import io.battlerune.game.world.entity.combat.hit.Hit;
import io.battlerune.game.world.entity.combat.strategy.player.PlayerMeleeStrategy;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.player.Player;

/**
 * Handles the abyssal whip weapon special attack.
 *
 * @author Daniel
 */
public class AbyssalDagger extends PlayerMeleeStrategy {
	private static final AbyssalDagger INSTANCE = new AbyssalDagger();

	private static final Animation ANIMATION = new Animation(1062, UpdatePriority.HIGH);
	private static final Graphic GRAPHIC = new Graphic(1283, true, UpdatePriority.HIGH);

	public String name() {
		return "Abyssal dagger";
	}

	@Override
	public void start(Player attacker, Mob defender, Hit[] hits) {
		super.start(attacker, defender, hits);
	}

	@Override
	public void attack(Player attacker, Mob defender, Hit hit) {
		super.attack(attacker, defender, hit);
		attacker.graphic(GRAPHIC);
	}

	@Override
	public CombatHit[] getHits(Player attacker, Mob defender) {
		return new CombatHit[] { nextMeleeHit(attacker, defender), nextMeleeHit(attacker, defender) };
	}

	@Override
	public Animation getAttackAnimation(Player attacker, Mob defender) {
		return ANIMATION;
	}

	@Override
	public int modifyAccuracy(Player attacker, Mob defender, int roll) {
		return roll * 5 / 3;
	}

	@Override
	public int modifyDamage(Player attacker, Mob defender, int damage) {
		return (int) (damage - damage * 0.14);
	}

	public static AbyssalDagger get() {
		return INSTANCE;
	}

}
