package io.github.melerodev.discordlink.command;

import io.github.melerodev.discordlink.config.PluginConfig;
import io.github.melerodev.discordlink.utility.Cfg;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Parses configured messages, applying built-in placeholders, PlaceholderAPI and MiniPlaceholders.
 */
public final class MessageFormatter {
    private MessageFormatter() {
    }

    /**
     * Format a configured message for a receiver.
     *
     * @param message  the raw message
     * @param receiver the receiver, used for player specific placeholders
     * @return the parsed component
     */
    public static @NotNull Component format(@NotNull String message, @NotNull CommandSender receiver) {
        final PluginConfig cfg = Cfg.get();

        final var parser = ColorParser.of(message)
            .legacy()
            .with("link", cfg.link)
            .with("player", receiver.getName())
            .with("online", String.valueOf(Bukkit.getOnlinePlayers().size()))
            .with("max_players", String.valueOf(Bukkit.getMaxPlayers()));

        cfg.placeholders.forEach(parser::with);

        if (receiver instanceof Player player)
            parser.papi(player).mini(player);

        return parser.build();
    }
}
