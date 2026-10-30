package io.github.melerodev.discordlink.listener;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.Reloadable;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle registration of event listeners.
 */
public class ListenerHandler implements Reloadable {
    private final DiscordLink plugin;
    private final List<Listener> listeners = new ArrayList<>();

    /**
     * Instantiates a the Listener handler.
     *
     * @param plugin the plugin instance
     */
    public ListenerHandler(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        listeners.clear(); // Clear the list to avoid duplicate listeners when reloading the plugin
//        listeners.add(new ExampleListener());

        // Register listeners here
        for (Listener listener : listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
