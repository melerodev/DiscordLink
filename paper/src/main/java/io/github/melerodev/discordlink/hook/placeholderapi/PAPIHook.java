package io.github.melerodev.discordlink.hook.placeholderapi;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.hook.AbstractHook;
import io.github.melerodev.discordlink.hook.Hook;

/**
 * A hook to interface with <a href="https://wiki.placeholderapi.com/">PlaceholderAPI</a>.
 */
public class PAPIHook extends AbstractHook {
    private PAPIExpansion PAPIExpansion;

    /**
     * Instantiates a new PlaceholderAPI hook.
     *
     * @param plugin the plugin instance
     */
    public PAPIHook(DiscordLink plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        if (!isHookLoaded())
            return;

        PAPIExpansion = new PAPIExpansion(super.getPlugin());
        PAPIExpansion.register();
    }

    @Override
    public void onDisable(AbstractDiscordLink plugin) {
        if (PAPIExpansion == null)
            return;

        PAPIExpansion.unregister();
        PAPIExpansion = null;
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginPresent(Hook.PAPI.getPluginName()) && isPluginEnabled(Hook.PAPI.getPluginName());
    }
}
