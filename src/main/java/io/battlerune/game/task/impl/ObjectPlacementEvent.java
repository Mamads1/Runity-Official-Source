package io.battlerune.game.task.impl;

import io.battlerune.game.task.Task;
import io.battlerune.game.world.object.CustomGameObject;

/**
 * An randomevent which places a temporary object.
 *
 * @author Daniel | Obey
 */
public class ObjectPlacementEvent extends Task {

	/** The custom game object. */
	private final CustomGameObject object;

	/** The runnable. */
	private final Runnable onDestination;

	/** Constructs a new <code>ObjectReplacementEvent</code>. */
	public ObjectPlacementEvent(CustomGameObject object, int delay) {
		this(object, delay, () -> {
		});
	}

	/** Constructs a new <code>ObjectReplacementEvent</code>. */
	public ObjectPlacementEvent(CustomGameObject object, int delay, Runnable onDestination) {
		super(false, delay);
		this.object = object;
		this.onDestination = onDestination;
	}

	@Override
	public void onSchedule() {
		if (!object.isRegistered())
			object.register();
	}

	@Override
	public void execute() {
		this.cancel();
	}

	@Override
	public void onCancel(boolean logout) {
		onDestination.run();
		object.unregister();
	}

}
