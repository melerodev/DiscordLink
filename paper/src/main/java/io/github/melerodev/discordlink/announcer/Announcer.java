package io.github.melerodev.discordlink.announcer;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.Reloadable;
import io.github.melerodev.discordlink.command.MessageFormatter;
import io.github.melerodev.discordlink.config.PluginConfig;
import io.github.melerodev.discordlink.utility.Cfg;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * Periodically broadcasts the configured announcements. Uses the global region scheduler so it works on Folia.
 */
public class Announcer implements Reloadable {
    private final DiscordLink plugin;
    private ScheduledTask task;
    private int index = 0;

    public Announcer(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        start();
    }

    @Override
    public void onDisable(AbstractDiscordLink plugin) {
        stop();
    }

    /**
     * Restarts the announcer, picking up config changes.
     */
    public void restart() {
        stop();
        start();
    }

    private void start() {
        final PluginConfig.Announcer settings = Cfg.get().announcer;
        if (!settings.enabled || settings.messages.isEmpty())
            return;

        index = 0;
        final long interval = settings.intervalSeconds;
        task = Bukkit.getAsyncScheduler().runAtFixedRate(plugin, t -> announce(), interval, interval, TimeUnit.SECONDS);
    }

    private void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    private void announce() {
        final PluginConfig.Announcer settings = Cfg.get().announcer;
        if (settings.messages.isEmpty())
            return;

        if (settings.skipWhenEmpty && Bukkit.getOnlinePlayers().isEmpty())
            return;

        final String message = settings.messages.get(index++ % settings.messages.size());
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Format on the player's own thread so PlaceholderAPI placeholders are safe to resolve (Folia support)
            player.getScheduler().run(plugin, t -> {
                for (String line : message.split("\\\\n|\n"))
                    player.sendMessage(MessageFormatter.format(line, player));
            }, null);
        }
    }
}
