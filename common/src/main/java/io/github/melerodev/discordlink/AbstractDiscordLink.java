package io.github.melerodev.discordlink;

import io.github.melerodev.discordlink.config.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDiscordLink extends JavaPlugin {
    private static AbstractDiscordLink instance;

    /**
     * Gets plugin instance.
     *
     * @return the plugin instance
     */
    public static AbstractDiscordLink getInstance() {
        return AbstractDiscordLink.instance;
    }

    AbstractDiscordLink() {
        AbstractDiscordLink.instance = this;
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    public abstract @NotNull ConfigHandler getConfigHandler();
}
