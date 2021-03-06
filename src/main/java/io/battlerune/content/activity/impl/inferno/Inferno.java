package io.battlerune.content.activity.impl.inferno;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import io.battlerune.content.ActivityLog;
import io.battlerune.content.activity.Activity;
import io.battlerune.content.activity.ActivityListener;
import io.battlerune.content.activity.ActivityType;
import io.battlerune.content.activity.impl.inferno.InfernoWaveData.WaveData;
import io.battlerune.content.activity.panel.ActivityPanel;
import io.battlerune.content.pet.PetData;
import io.battlerune.content.pet.Pets;
import io.battlerune.game.world.entity.mob.Mob;
import io.battlerune.game.world.entity.mob.npc.Npc;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.items.Item;
import io.battlerune.game.world.position.Area;
import io.battlerune.game.world.position.Position;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.util.RandomUtils;
import io.battlerune.util.Utility;

/** @author Adam_#6723 
 *  Handles inferno wave.
 */
public class Inferno extends Activity {

	/** The player in the Inferno */
	private final Player player;

	/** The activity completed flag. */
	private boolean completed;

	/** The time it took to complete the activity. */
	private long time;

	/** The amount of rewards the player has acquired. */
	private int rewards;

	/** A set of npcs in this activity. */
	public final Set<Npc> npcs = new HashSet<>();

	/** The current wave of this activity. */
	private InfernoWaveData.WaveData wave = InfernoWaveData.WaveData.WAVE_1;

	/** The combat listener to add for all mobs. */
	private final InfernoCavesListener listener = new InfernoCavesListener(this);

	/**
	 * Constructs a new {@code Inferno} object for a {@code player} and an
	 * {@code instance}.
	 */
	private Inferno(Player player, int instance) {
		super(10, instance);
		this.player = player;
	}

	public static Inferno create(Player player) {
		Inferno minigame = new Inferno(player, player.playerAssistant.instance());
		player.move(new Position(2273, 5341, player.getHeight()));
		ActivityPanel.update(player, -1, "Inferno", "Activity Completion:", "Good Luck, " + player.getName() + "!");
		player.dialogueFactory.sendNpcChat(5567, "Welcome to the Inferno, #name.",
				"There are a total of 69 waves, TzKal-Zuk being the last.",
				"Use your activity panel (bottom left tab) for wave information.", "Good luck!").execute();
		minigame.time = System.currentTimeMillis();
		minigame.add(player);
		minigame.resetCooldown();
		return minigame;
	}

	/** Handles what happens to a mob when they die in the activity. */
	void handleDeath(Mob dead) {
		if (dead.isPlayer() && dead.equals(player)) {
			finish();
			return;
		}
		if (dead.isNpc() && npcs.contains(dead)) {
			if (dead.id == 3162) {
				remove(dead);
				npcs.remove(dead);
				for (int index = 0; index < 2; index++) {
					Position position = new Position(dead.getX() + (index == 0 ? -1 : +1), dead.getY(),
							dead.getHeight());
					Npc roc = new Npc(763, position);
					add(roc);
					npcs.add(roc);
					roc.getCombat().attack(player);
				}
				return;
			}

			npcs.remove(dead);
			remove(dead);
			rewards += Utility.random(500, 1250);
			if (npcs.isEmpty()) {
				wave = InfernoWaveData.WaveData.getNext(wave.ordinal());
				if (wave == null) {
					completed = true;
					player.send(new SendMessage("You have finished the activity!"));
				} else {
					player.send(new SendMessage("The next wave will commence soon."));
				}
				resetCooldown();
				return;
			}
		}
	}

	@Override
	protected void start() {
		if (wave == null) {
			finish();
			return;
		}
		if (player.locking.locked()) {
			return;
		}

		Position spawn = new Position(2273, 5337, player.getHeight());
		Position[] boundaries = Utility.getInnerBoundaries(spawn, Utility.random(1, 8), Utility.random(1, 8));

		for (int id : wave.getMonster()) {
			Npc npc = new Npc(id, RandomUtils.random(boundaries));
			npc.owner = player;
			add(npc);
			npcs.add(npc);
			npc.getCombat().attack(player);
			npc.face(player);
			npc.attack(player);
			player.face(npc.getPosition());
			npc.locking.unlock();
			//pause();

		}
		if(wave == WaveData.WAVE_2 || wave == WaveData.WAVE_1) {
			player.message("WAVE 69!! HURAHH");
		}
		pause();
	}
	
	public static void finalWave() {
		final int BOSS_ID; 
		WaveData wavee = WaveData.WAVE_69;
	}

	@Override
	public void finish() {
		cleanup();
		remove(player);
		player.move(new Position(3086, 3501, 0));

		if (completed) {
			player.dialogueFactory.sendNpcChat(5567, "You have defeated Inferno, I am most impressed!",
					"Please accept this gift, young thug.").execute();
			rewards += 10000;
            player.inventory.addOrDrop(new Item(7775, rewards));
    		player.message("<img=9>You now have @red@" + rewards + " Inferno Tickets!");
			if(Utility.random(1, 3) == 3) {
			player.inventory.addOrDrop(new Item(20211));
			}
			player.inventory.addOrDrop(new Item(290));
			Pets.onReward(player, PetData.PIRATE_PETE);
			player.send(new SendMessage("You have completed the Inferno activity. Final time: @red@"
					+ Utility.getTime(time) + "</col>."));
			player.activityLogger.add(ActivityLog.INFERNO);
			return;
		}

		if (rewards <= 0)
			rewards = 1;
        player.inventory.addOrDrop(new Item(7775, rewards));
		player.message("<img=9>You now have @red@" + rewards + " Inferno Tickets!");
		player.dialogueFactory.sendNpcChat(5567, "Better luck next time!", "Take these points as a reward.").execute();
	}

	@Override
	public void cleanup() {
		ActivityPanel.clear(player);
		if (!npcs.isEmpty())
			npcs.forEach(this::remove);
	}

	@Override
	public void update() {
		if (wave == null) {
			ActivityPanel.update(player, 100, "Inferno", new Item(22325), "Congratulations, you have",
					"completed the Inferno", "activity!");
			return;
		}
		int progress = (int) Utility.getPercentageAmount(wave.ordinal() + 1, InfernoWaveData.WaveData.values().length);
		if (progress >= 100 && !completed)
			progress = 99;
		ActivityPanel.update(player, progress, "Inferno", new Item(22325),
				"</col>Wave: <col=FF5500>" + (wave.ordinal() + 1) + "/" + (InfernoWaveData.WaveData.values().length),
				"</col>Monsters Left: <col=FF5500>" + npcs.size(),
				"</col>Points Gained: <col=FF5500>" + Utility.formatDigits(rewards),
				"</col>Time: <col=FF5500>" + Utility.getTime());
	}

	@Override
	public boolean canTeleport(Player player) {
		return true;
	}

	@Override
	public void onRegionChange(Player player) {
		if (!Area.inInferno(player)) {
			cleanup();
			remove(player);
			player.send(new SendMessage("You have lost your current progress as you have teleported."));
		}
	}

	@Override
	public void onLogout(Player player) {
		finish();
		remove(player);
	}

	@Override
	public ActivityType getType() {
		return ActivityType.INFERNO;
	}

	@Override
	public Optional<? extends ActivityListener<? extends Activity>> getListener() {
		return Optional.of(listener);
	}
}
