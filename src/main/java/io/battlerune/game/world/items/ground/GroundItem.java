package io.battlerune.game.world.items.ground;

import java.util.Objects;

import io.battlerune.game.event.impl.PickupItemEvent;
import io.battlerune.game.event.impl.log.DropItemLogEvent;
import io.battlerune.game.plugin.PluginManager;
import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.Entity;
import io.battlerune.game.world.entity.EntityType;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.PlayerRight;
import io.battlerune.game.world.items.Item;
import io.battlerune.game.world.position.Position;
import io.battlerune.game.world.region.Region;
import io.battlerune.game.world.region.RegionManager;
import io.battlerune.net.packet.out.SendGroundItem;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.net.packet.out.SendRemoveGroundItem;

/**
 * Represents a single Ground item on the world map.
 *
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 * @author Daniel
 * @author Michael | Chex
 * @since 27-12-2016.
 */
public final class GroundItem extends Entity {

	/** The item that represents this ground item. */
	public final Item item;

	/** The optional player whom owns this item. */
	public final Player player;

	/** The randomevent for ground item. */
	public final GroundItemEvent event = new GroundItemEvent(this);

	/** The policy for this ground item. */
	public GroundItemPolicy policy;

	public boolean canIronMenPickThisItemUp = true;

	/**
	 * Creates a new {@link GroundItem} object for a {@code player} and an
	 * {@code item}.
	 */
	public static void createGlobal(Player player, Item item) {
		GroundItem groundItem = new GroundItem(player, item, player.getPosition());
		groundItem.policy = GroundItemPolicy.GLOBAL;
		groundItem.register();
		World.getDataBus().publish(new DropItemLogEvent(player, groundItem));
	}

	/**
	 * Creates a new {@link GroundItem} object for a {@code player} and an
	 * {@code item}.
	 */
	public static void createGlobal(Player player, Item item, Position position) {
		GroundItem groundItem = new GroundItem(player, item, position);
		groundItem.policy = GroundItemPolicy.GLOBAL;
		groundItem.register();
	}

	/**
	 * Creates a new {@link GroundItem} object for a {@code player} and an
	 * {@code item}.
	 */
	public static GroundItem create(Player player, Item item) {
		GroundItem groundItem = new GroundItem(player, item, player.getPosition());
		groundItem.policy = GroundItemPolicy.ONLY_OWNER;
		groundItem.register();
		World.getDataBus().publish(new DropItemLogEvent(player, groundItem));
		return groundItem;
	}

	/**
	 * Creates a new {@link GroundItem} object for a {@code player}, an {@code item}
	 * and {@code position}.
	 */
	public static GroundItem create(Player player, Item item, Position position) {
		GroundItem groundItem = new GroundItem(player, item, position);
		groundItem.policy = GroundItemPolicy.ONLY_OWNER;
		groundItem.register();
		return groundItem;
	}

	/**
	 * Constructs a new {@code GroundItem} object for a {@code player}, an
	 * {@code item}, and a {@code position}.
	 */
	private GroundItem(Player player, Item item, Position position) {
		super(position);
		this.item = item;
		this.player = player;
		this.instance = player.instance;
	}

	public boolean canSee(Player other) {
		if (item.isTradeable() && policy.equals(GroundItemPolicy.GLOBAL)) {
			return true;
		}
		return player.usernameLong == other.usernameLong;
	}

	/** Attempts to pick the specified {@code item} up. */
	public static void pickup(Player player, Item item, Position position) {
		GroundItem result = position.getRegion().getGroundItem(item.getId(), position);

		if (result == null) {
			return;
		}

		if (PlayerRight.isIronman(player)
				&& (!result.canIronMenPickThisItemUp || result.player.usernameLong != player.usernameLong)) {
			player.send(new SendMessage("As an iron man you may not pick up this item."));
			return;
		}

		if (!player.inventory.hasCapacityFor(item)) {
			player.send(new SendMessage("You don't have enough inventory space."));
			return;
		}

		result.event.cancel();
		player.inventory.add(result.item);
		PluginManager.getDataBus().publish(player, new PickupItemEvent(result));
	}

	@Override
	public void register() {
		if (isRegistered()) {
			return;
		}

		setRegistered(true);
		setPosition(getPosition());
	}

	@Override
	public void unregister() {
		if (!isRegistered()) {
			return;
		}

		setRegistered(false);
		removeFromRegion(getRegion());
	}

	@Override
	public void addToRegion(Region region) {
		if (item.getId() == 11283) {
			item.setId(11284);
			player.dragonfireCharges = 0;
		}

		GroundItem groundItem = region.getGroundItem(item.getId(), getPosition());
		if (groundItem != null && groundItem.item.isStackable() && policy.equals(groundItem.policy)
				&& (policy == GroundItemPolicy.GLOBAL || player.usernameLong == groundItem.player.usernameLong)) {
			groundItem.event.cancel();
			item.incrementAmountBy(groundItem.item.getAmount());
		}

		Region[] regions = RegionManager.getSurroundingRegions(getPosition());
		for (Region reg : regions) {
			reg.getPlayers(getHeight()).forEach(player -> {
				if (canSee(player)) {
					player.send(new SendGroundItem(this));
				}
			});
		}

		region.addGroundItem(this);
		World.schedule(event);
	}

	@Override
	public void removeFromRegion(Region region) {
		Region[] regions = RegionManager.getSurroundingRegions(getPosition());
		for (Region reg : regions) {
			reg.getPlayers(getHeight()).forEach(player -> {
				if (canSee(player)) {
					player.send(new SendRemoveGroundItem(this));
				}
			});
		}
		region.removeGroundItem(this);
	}

	@Override
	public String getName() {
		return item.getName();
	}

	@Override
	public EntityType getType() {
		return EntityType.GROUND_ITEM;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof GroundItem) {
			GroundItem other = (GroundItem) obj;
			return other.getPosition().equals(getPosition()) && other.item.equals(item)
					&& player.usernameLong == other.player.usernameLong;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPosition(), getIndex());
	}

	@Override
	public String toString() {
		return "GroundItem[owner=" + player.getUsername() + ", position=" + getPosition() + ", index=" + getIndex()
				+ ", item=" + item.getName() + "]";
	}
}
