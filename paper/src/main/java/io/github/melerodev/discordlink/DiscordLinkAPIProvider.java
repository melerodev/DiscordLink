package io.github.melerodev.discordlink;

import io.github.melerodev.discordlink.api.DiscordLinkAPI;
import io.github.melerodev.discordlink.utility.Cfg;
import org.jetbrains.annotations.NotNull;

class DiscordLinkAPIProvider extends DiscordLinkAPI implements Reloadable {
    private final DiscordLink plugin;

    DiscordLinkAPIProvider(DiscordLink plugin) {
        super();
        this.plugin = plugin;
        setInstance(this);
    }

    @Override
    public @NotNull String getLink() {
        return Cfg.get().link;
    }
}
