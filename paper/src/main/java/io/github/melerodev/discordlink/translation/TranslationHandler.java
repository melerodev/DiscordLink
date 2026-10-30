package io.github.melerodev.discordlink.translation;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.Reloadable;
import io.github.melerodev.discordlink.config.ConfigHandler;

/**
 * A wrapper handler class for handling the {@link Translation} lifecycle.
 */
public class TranslationHandler implements Reloadable {
    private final ConfigHandler configHandler;

    public TranslationHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        Translation.initialize(plugin, configHandler.getConfig().language);
    }
}
