package plugin.command.impl.player;

import io.battlerune.Config;
import io.battlerune.content.command.Command;
import io.battlerune.content.skill.impl.magic.teleport.Teleportation;
import io.battlerune.game.world.entity.mob.player.Player;

/**
 * @author Adam_#6723
 */

public class TarnCommand implements Command {

	@Override
	public void execute(Player player, String command, String[] parts) {

		if (player.pet != null) {
			player.dialogueFactory
					.sendNpcChat(player.pet.id, "I'm sorry #name,", "but I can not enter the wilderness with you!")
					.execute();
			return;
		}
		player.dialogueFactory.sendOption("@red@Teleport me", () -> {

				Teleportation.teleport(player, Config.TARN_ZONE);
				player.message("You have teleported to Mutant Tarn!");

		}, "Cancel", () -> {

			player.dialogueFactory.clear();
		}).execute();
	}
	

	@Override
	public boolean canUse(Player player) {
		return true;
	}

}
