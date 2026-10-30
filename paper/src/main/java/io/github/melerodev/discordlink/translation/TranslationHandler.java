package io.github.melerodev.discordlink.translation;

import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.Reloadable;
import io.github.melerodev.discordlink.config.ConfigHandler;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.milkdrinkers.colorparser.paper.engine.PaperParserEngine;
import io.github.milkdrinkers.wordweaver.Translation;
import io.github.milkdrinkers.wordweaver.config.TranslationConfig;

import java.nio.file.Path;

/**
 * A wrapper handler class for handling WordWeaver lifecycle.
 */
public class TranslationHandler implements Reloadable {
    private final ConfigHandler configHandler;

    public TranslationHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        Translation.initialize(TranslationConfig.builder() // Initialize word-weaver
            .namespace("discordlink")
            .translationDirectory(plugin.getDataPath().resolve("lang"))
            .resourcesDirectory(Path.of("lang"))
            .extractBundles(true)
            .updateBundles(true)
            .locale(configHandler.getConfig().language)
            .defaultLocale("en_US")
            .componentConverter(s -> ColorParser.of(s).build()) // Use color parser for components by default
            .miniMessage( // Use a MiniMessage parser from ColorParser when parsing translation entries invoked from "<lang:...>" tags in minimessage or translatable components
                PaperParserEngine.builder()
                    .parseMiniPlaceholders(true)
                    .parsePlaceholderAPI(true)
                    .parseLegacy(true)
                    .parseDefaultAddonTags(true)
                    .parseDefaultTags(true)
                    .build()
                    .getMiniMessage()
            )
            .build()
        );
    }
}
