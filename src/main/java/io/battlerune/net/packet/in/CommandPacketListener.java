package io.battlerune.net.packet.in;

import io.battlerune.content.clanchannel.content.ClanTaskKey;
import io.battlerune.content.command.Command;
import io.battlerune.content.command.CommandManager;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.relations.ChatMessage;
import io.battlerune.net.packet.ClientPackets;
import io.battlerune.net.packet.GamePacket;
import io.battlerune.net.packet.PacketListener;
import io.battlerune.net.packet.PacketListenerMeta;
import io.battlerune.util.Utility;

/**
 * The {@code GamePacket} responsible for handling user commands send from the
 * client.
 *
 * @author Michael | Chex
 */
@PacketListenerMeta(ClientPackets.PLAYER_COMMAND)
public final class CommandPacketListener implements PacketListener {

	@Override
	public void handlePacket(Player player, GamePacket packet) {
		final String input = packet.getRS2String().trim().toLowerCase();

		if (input.isEmpty() || input.length() > ChatMessage.CHARACTER_LIMIT) {
			return;
		}

		if (input.startsWith("/")) {
			if (player.punishment.isMuted()) {
				player.message("You can not send clan messages while muted!");
				return;
			}

			player.forClan(channel -> {
				final String line = input;
				channel.chat(player.getName(), Utility.capitalizeSentence(line));

			});
			return;
		}

		player.forClan(channel -> channel.activateTask(ClanTaskKey.SEND_CLAN_MESSAGE, player.getName()));

		String[] command = input.split(" ");
		
		Command plugin = CommandManager.plugin_input.get(input);
		if (player != null && plugin != null) {
			if (plugin.canUse(player)) {
				plugin.execute(player, command);
			}
		}
	}

}
