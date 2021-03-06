package plugin.click.button;

import java.util.List;

import io.battlerune.content.DropSimulator;
import io.battlerune.game.plugin.PluginContext;
import io.battlerune.game.world.entity.mob.player.Player;

public class DropSimulatorButtonPlugin extends PluginContext {

	@Override
	protected boolean onClick(Player player, int button) {
		if (button >= 26851 && button <= 26900) {
			int base_button = 26851;
			int modified_button = (base_button - button);
			int index = Math.abs(modified_button);
			List<Integer> npc = player.attributes.get("DROP_SIMULATOR_BUTTON_KEY", List.class);
			if (npc == null)
				return false;
			if (index >= npc.size())
				return false;
			DropSimulator.displaySimulation(player, npc.get(index), 100);
			return true;
		}
		return false;
	}
}
