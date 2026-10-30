package io.github.melerodev.discordlink.utility;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.config.ConfigHandler;
import io.github.melerodev.discordlink.config.PluginConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Convenience class for accessing {@link ConfigHandler#getConfig}
 */
public final class Cfg {
    /**
     * Convenience method for {@link ConfigHandler#getConfig} to getConnection {@link PluginConfig}
     *
     * @return the config
     */
    @NotNull
    public static PluginConfig get() {
        return AbstractDiscordLink.getInstance().getConfigHandler().getConfig();
    }
}
