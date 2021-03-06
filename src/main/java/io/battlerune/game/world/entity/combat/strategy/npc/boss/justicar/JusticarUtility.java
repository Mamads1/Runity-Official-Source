package io.battlerune.game.world.entity.combat.strategy.npc.boss.justicar;

import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.Direction;
import io.battlerune.game.world.entity.mob.npc.Npc;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.position.Position;
import io.battlerune.util.Utility;

/**
 * Created by Adam_#6723 Jusiticar Utility Class, handles the spawning &
 * generate
 */
public class JusticarUtility {

	public static SpawnData2 spawn;

	public static boolean activated = false;

	public static boolean justicarbutton;

	public static Npc generateSpawn() {
		activated = true;
		spawn = SpawnData2.generate();
		Npc jusiticar = new Npc(7858, spawn.position, 10, Direction.NORTH);
		World.sendMessage("<col=8714E6> Justicar has just spawned! He is located at " + spawn.location + "!");
		World.sendBroadcast(1, "The Justicar boss has spawned!" + spawn.location + "!", true);
		World.sendJusticarInformation();
		jusiticar.register();
		jusiticar.definition.setRespawnTime(-1);
		jusiticar.definition.setAggressive(true);
		jusiticar.speak("Darkness is here to penetrate your souls!");
		return jusiticar;
	}

	/** Identification of all loot, it selects the loot */

	public static int[] ALWAYSLOOT = { 1, 1, 1, 1, 1, 2, 3, 4, 5 };
	public static int[] COMMONLOOT = { 6199, 989, 3140, 4087, 11732, 989, 12878, 6585, 4675 };
	public static int[] RARELOOT = { 11834, 11832, 11828, 11830, 11836, 11773, 13239, 13237, 13235, 11772, 11771, 11770,
			20143, 20002 };
	public static int[] SUPERRARELOOT = { 11862, 12817, 12825, 12821, 20997, 13652, 11802, 13576, 11785, 19481,
			11791, 12904, };

	public static void defeated(Npc justicar, Player player) {
		boolean hasClan = player.clanChannel != null;

		if (hasClan) {
			player.clanChannel.getDetails().points += 5;
			player.clanChannel.addExperience(10000);
			World.sendMessage("<col=8714E6> Skotizo has been defeated by " + player.getName() + " !");
			player.clanChannel.message("Hell yeah boys! We just killed Skotizo!! We earned 10,000 EXP & 5 CP.");
		} else {
			World.sendMessage("<col=8714E6> Skotizo has been defeated by " + player.getName()
					+ ", a solo individual with balls of steel!");
		}

		justicar.unregister();
		activated = false;
	}

	public enum SpawnData2 {
		LEVEL_46("lvl 46 wild near Spider Hill", new Position(3135, 3888, 0), new Position(3132, 3881, 0)),
		LEVEL_16("lvl 16 wild near Bone Yard", new Position(3273, 3648, 0), new Position(3267, 3654, 0)),
		LEVEL_51("lvl 51 wild near Rogues Castle", new Position(3266, 3924, 0), new Position(3266, 3927, 0)),
		LEVEL_41("lvl 19 wild near graveyard of shadows", new Position(3197, 3670, 0), new Position(3194, 3666, 0)),
		LEVEL_47("lvl 47 wild near obelisk", new Position(3308, 3892, 0), new Position(3305, 3888, 0)),
		LEVEL_53("lvl 53 wild near scorpia's cave entrance", new Position(3211, 3944, 0), new Position(3208, 3940, 0));

		public final String location;
		public final Position position;
		public final Position tsunami;

		SpawnData2(String location, Position position, Position tsunami) {
			this.location = location;
			this.position = position;
			this.tsunami = tsunami;
		}

		public static SpawnData2 generate() {
			return Utility.randomElement(values());
		}

		public String getLocation() {
			return location;
		}

		public Position getPosition() {
			return position;
		}

		public Position getTsunami() {
			return tsunami;
		}

	}

}