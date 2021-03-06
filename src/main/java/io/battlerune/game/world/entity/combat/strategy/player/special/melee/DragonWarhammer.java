package io.battlerune.game.world.entity.combat.strategy.player.special.melee;

import io.battlerune.game.Animation;
import io.battlerune.game.Graphic;
import io.battlerune.game.UpdatePriority;
import io.battlerune.game.world.entity.combat.attack.FightType;
import io.battlerune.game.world.entity.combat.hit.Hit;
import io.battlerune.game.world.entity.combat.strategy.player.PlayerMeleeStrategy;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.skill.Skill;
import io.battlerune.net.packet.out.SendMessage;

/** @author Daniel | Obey | Adam_#6723 */
public class DragonWarhammer extends PlayerMeleeStrategy {
	private static final Animation ANIMATION = new Animation(1378, UpdatePriority.HIGH);
	private static final Graphic GRAPHIC = new Graphic(1292);
	private static final DragonWarhammer INSTANCE = new DragonWarhammer();

	private DragonWarhammer() {
	}

	@Override
	public void attack(Player attacker, Mob defender, Hit h) {
		super.attack(attacker, defender, h);
		attacker.graphic(GRAPHIC);

		if (defender.isPlayer() && h.isAccurate()) {
			Player victim = defender.getPlayer();
			int damage = h.getDamage();
			int[] skillOrder = { Skill.DEFENCE, Skill.STRENGTH, Skill.ATTACK, Skill.PRAYER, Skill.MAGIC, Skill.RANGED };

			for (int s : skillOrder) {

				// Getting the skill value to decrease.
				int removeFromSkill;

				if (h.getDamage() > victim.skills.getLevel(s)) {
					int difference = damage - victim.skills.getLevel(s);
					removeFromSkill = damage - difference;
				} else
					removeFromSkill = damage;
				if (removeFromSkill <= 0) {
					return; // ADAM ADDED THIS INCASE IT CAUSES ISSUES
				}

				// Decreasing the skill.
				victim.skills.get(s).removeLevel(removeFromSkill);
				victim.skills.refresh(s);

				// Changing the damage left to decrease.
				damage -= removeFromSkill;
				String skill = Skill.getName(s);
				attacker.send(new SendMessage("You've drained " + victim.getUsername() + "'s " + skill + " level by "
						+ removeFromSkill + "."));
				victim.send(new SendMessage("Your " + skill + " level has been drained."));
			}
		}
	}

	@Override
	public int getAttackDelay(Player attacker, Mob defender, FightType fightType) {
		return 4;
	}

	@Override
	public Animation getAttackAnimation(Player attacker, Mob defender) {
		return ANIMATION;
	}

	@Override
	public int modifyDamage(Player attacker, Mob defender, int damage) {
		return damage * 3 / 2;
	}

	public static DragonWarhammer get() {
		return INSTANCE;
	}

}
