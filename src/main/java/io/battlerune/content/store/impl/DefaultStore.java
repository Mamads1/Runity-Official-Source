package io.battlerune.content.store.impl;

import java.util.Arrays;
import java.util.Objects;

import io.battlerune.content.store.SellType;
import io.battlerune.content.store.Store;
import io.battlerune.content.store.StoreConstant;
import io.battlerune.content.store.StoreItem;
import io.battlerune.content.store.StoreType;
import io.battlerune.content.store.currency.CurrencyType;
import io.battlerune.game.task.TickableTask;
import io.battlerune.game.world.World;
import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.game.world.entity.mob.player.PlayerRight;
import io.battlerune.game.world.items.Item;
import io.battlerune.game.world.items.containers.ItemContainer;
import io.battlerune.net.packet.out.SendInputAmount;
import io.battlerune.net.packet.out.SendItemOnInterface;
import io.battlerune.net.packet.out.SendMessage;
import io.battlerune.net.packet.out.SendScrollbar;
import io.battlerune.net.packet.out.SendString;

/**
 * The default shop which are owned by the server.
 * 
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 * @since 4-1-2017.
 */
public final class DefaultStore extends Store {

	/** The items in this shop. */
	public final StoreItem[] items;

	/** The original item container this shop started with. */
	public final ItemContainer original;

	/** Determines if this shop restocks. */
	public final boolean restock;

	public final SellType sellType;

	/**
	 * The shop restock task that will restock the shops.
	 */
	private StoreRestockTask restockTask;

	/**
	 * Creates a new {@link Store}.
	 * 
	 * @param items    the items in this container.
	 * @param name     the name of this current shop.
	 * @param sellType The different ways items can be sold to the shop.
	 * @param restock  the flag that determines if this shop will restock its items.
	 * @param currency the currency that items within this shop will be bought with.
	 */
	public DefaultStore(StoreItem[] items, String name, SellType sellType, boolean restock, CurrencyType currency) {
		super(name, ItemContainer.StackPolicy.ALWAYS, currency, sellType == SellType.ANY ? 80 : items.length);
		this.items = items;
		this.restock = restock;
		this.sellType = sellType;
		this.original = new ItemContainer(items.length, ItemContainer.StackPolicy.ALWAYS);
		this.original.setItems(items, false);
		this.container.setItems(items, false);
		Arrays.stream(items).filter(Objects::nonNull).forEach(item -> itemCache.put(item.getId(), item.getAmount()));
	}

	/**
	 * Determines if the items in the container need to be restocked.
	 * 
	 * @return {@code true} if the items need to be restocked, {@code false}
	 *         otherwise.
	 */
	protected boolean needsRestock() {
		return container.stream().filter(Objects::nonNull).anyMatch(i -> !itemCache.containsKey(i.getId())
				|| (itemCache.containsKey(i.getId()) && i.getAmount() < itemCache.get(i.getId())));
	}

	/**
	 * Determines if the items in the container no longer need to be restocked.
	 * 
	 * @return {@code true} if the items don't to be restocked, {@code false}
	 *         otherwise.
	 */
	protected boolean restockCompleted() {
		return container.stream().filter(Objects::nonNull).allMatch(i -> {
			if (itemCache.containsKey(i.getId()) && i.getAmount() >= itemCache.get(i.getId())) {// shop item.
				return true;
			} else if (!itemCache.containsKey(i.getId())) {// unique item.
				return false;
			}
			return false;
		});
	}

	@Override
	public void itemContainerAction(Player player, int id, int slot, int action, boolean purchase) {
		switch (action) {
		case 1:
			if (purchase) {
				this.sendPurchaseValue(player, slot);
			} else {
				this.sendSellValue(player, slot);
			}
			break;
		case 5:
			player.send(new SendInputAmount("Enter amount", 10, amount -> {
				if (purchase) {
					this.purchase(player, new Item(id, Integer.parseInt(amount)), slot);
				} else {
					this.sell(player, new Item(id, Integer.parseInt(amount)), slot, true);
				}
			}));
			break;
		default:
			int amount = 0;

			if (action == 2) {
				amount = 1;
			}
			if (action == 3) {
				amount = 10;
			}
			if (action == 4) {
				amount = 100;
			}

			if (purchase) {
				this.purchase(player, new Item(id, amount), slot);
			} else {
				this.sell(player, new Item(id, amount), slot, false);
			}
			break;
		}
	}

	@Override
	public void open(Player player) {
		if (PlayerRight.isIronman(player)) {
			if (Arrays.stream(StoreConstant.IRON_MAN_STORES).noneMatch(s -> s.equalsIgnoreCase(name))) {
				player.send(new SendMessage("As an iron man you do not have access to this store!"));
				return;
			}
		}

		player.attributes.set("SHOP", name);

		if (!STORES.containsKey(name)) {
			STORES.put(name, this);
		}

		players.add(player);
		player.inventory.refresh();
		refresh(player);
		player.send(new SendString(name, 40002));
		player.interfaceManager.openInventory(StoreConstant.INTERFACE_ID, 3822);
	}

	@Override
	public void close(Player player) {
		players.remove(player);
		player.attributes.remove("SHOP");
	}

	@Override
	public void refresh(Player player) {
		player.send(new SendString("Store size: " + items.length, 40007));
		player.send(new SendString((currencyType == CurrencyType.COINS ? "Coins" : "Points") + ": "
				+ CurrencyType.getValue(player, currencyType), 40008));

		final Item[] items = container.toArray();

		int lastItem = 0;
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];

			if (item == null) {
				continue;
			}

			if (item instanceof StoreItem) {
				StoreItem storeItem = (StoreItem) items[i];
				//System.out.println("refreshing... here");
				player.send(new SendString(storeItem.getShopValue() + "," + storeItem.getShopCurrency(this).getId(),
						40052 + i));
				lastItem = i;
			}
		}

		final int scrollBarSize = lastItem <= 32 ? 0 : (lastItem / 8) * 72;
		player.send(new SendScrollbar(40050, scrollBarSize));
		player.send(new SendItemOnInterface(3823, player.inventory.toArray()));
		players.stream().filter(Objects::nonNull)
				.forEach(p -> player.send(new SendItemOnInterface(40051, container.toArray())));
		if (restock) {
			if (restockTask != null && restockTask.isRunning()) {
				return;
			}
			if (!needsRestock()) {
				return;
			}
			restockTask = new StoreRestockTask(this);
			World.schedule(restockTask);
		}
	}

	@Override
	public StoreType type() {
		return StoreType.DEFAULT;
	}

	@Override
	public SellType sellType() {
		return sellType;
	}

	/**
	 * The task that will restock items in shop containers when needed.
	 * 
	 * @author lare96 <http://github.com/lare96>
	 */
	private static final class StoreRestockTask extends TickableTask {

		/**
		 * The container that will be restocked.
		 */
		private final DefaultStore container;

		/**
		 * Creates a new {@link StoreRestockTask}.
		 * 
		 * @param container the container that will be restocked.
		 */
		public StoreRestockTask(DefaultStore container) {
			super(false, 0);
			this.container = container;
		}

		@Override
		protected void tick() {
			if (container.restockCompleted() || !container.restock) {
				this.cancel();
				return;
			}

			if (tick >= 1) {

				final Item[] items = container.container.toArray();

				boolean restocked = false;

				for (Item item : items) {
					if (item == null) {
						continue;
					}

					if (item instanceof StoreItem) {
						if (restock((StoreItem) item)) {
							restocked = true;
						}
					}

				}

				if (restocked) {
					for (Player player : container.players) {
						if (player != null) {
							player.send(new SendItemOnInterface(40051, container.container.toArray()));
						}
					}
				}

				tick = 0;
			}

		}

		/**
		 * Attempts to restock {@code item} for the container.
		 * 
		 * @param item the item to attempt to restock.
		 */
		private boolean restock(StoreItem item) {
			if (!item.canReduce()) {
				return false;
			}

			final int reduceAmount = item.getAmount() > 100 ? (int) ((double) item.getAmount() * 0.05D) : 1;

			// if the item is not an original item
			if (!container.original.contains(item.getId())) {

				if (item.getAmount() - 1 <= 0) {
					container.container.remove(item);
				} else {
					item.decrementAmountBy(reduceAmount);
				}
				return true;
			} else {

				// the item is an original item

				final boolean originalItem = container.itemCache.containsKey(item.getId());
				final int originalAmount = container.itemCache.get(item.getId());

				// increment the original item if its not fully stocked
				if (originalItem && item.getAmount() < originalAmount) {
					item.incrementAmount();
					return true;
				} else if (originalItem && item.getAmount() > originalAmount) { // decrement original item if its over
																				// stocked
					item.decrementAmountBy(reduceAmount);
					return true;
				}
			}

			return false;
		}

	}
}
