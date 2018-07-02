package plugin.command.impl.player;

import io.battlerune.content.DropDisplay;
import io.battlerune.content.command.Command;
import io.battlerune.game.world.entity.mob.player.Player;

public class DropsCommand implements Command {

	@Override
	public void execute(Player player, String[] command) {
		 DropDisplay.open(player);
	}

	@Override
	public boolean canUse(Player player) {
		return true;
	}

}
