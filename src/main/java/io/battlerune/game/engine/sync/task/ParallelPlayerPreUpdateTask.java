package io.battlerune.game.engine.sync.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.battlerune.game.world.entity.mob.player.Player;
import io.battlerune.net.session.GameSession;

public class ParallelPlayerPreUpdateTask extends SynchronizationTask {

    private static final Logger logger = LogManager.getLogger();

    private final Player player;

    public ParallelPlayerPreUpdateTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {

        } catch (Exception ex) {
            logger.error(String.format("Error in %s. player=%s", PlayerPreUpdateTask.class.getSimpleName(), player), ex);
        }
    }

}
