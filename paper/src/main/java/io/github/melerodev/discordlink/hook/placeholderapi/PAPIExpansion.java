package io.github.melerodev.discordlink.hook.placeholderapi;

import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.utility.Cfg;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * PlaceholderAPI expansion: {@code %discordlink_link%} and {@code %discordlink_<placeholder>%} for every extra placeholder.
 */
public class PAPIExpansion extends PlaceholderExpansion {
    private final DiscordLink plugin;

    public PAPIExpansion(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull String getIdentifier() {
        return plugin.getPluginMeta().getName().replace(' ', '_').toLowerCase();
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getPluginMeta().getAuthors());
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This needs to be true, or PlaceholderAPI will unregister the expansion during a plugin reload.
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer p, @NotNull String params) {
        if (params.equalsIgnoreCase("link"))
            return Cfg.get().link;

        return Cfg.get().placeholders.get(params);
    }
}
