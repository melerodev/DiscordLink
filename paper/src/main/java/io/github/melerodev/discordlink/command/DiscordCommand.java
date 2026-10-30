package io.github.melerodev.discordlink.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.config.PluginConfig;
import io.github.melerodev.discordlink.utility.Cfg;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.milkdrinkers.wordweaver.Translation;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.github.melerodev.discordlink.command.CommandHandler.BASE_PERM;

/**
 * The /discord command.
 */
final class DiscordCommand extends Command {
    private static final String RELOAD_PERM = BASE_PERM + ".reload";
    private static final String BROADCAST_PERM = BASE_PERM + ".broadcast";

    private final DiscordLink plugin;

    DiscordCommand(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandAPICommand command() {
        final PluginConfig.CommandSettings settings = Cfg.get().command;

        final CommandAPICommand command = new CommandAPICommand(settings.name)
            .withHelp("Shows the Discord link.", "Shows the Discord invite link of this server.")
            .withSubcommands(
                new CommandAPICommand("reload")
                    .withPermission(RELOAD_PERM)
                    .executes(this::executorReload),
                new CommandAPICommand("broadcast")
                    .withPermission(BROADCAST_PERM)
                    .executes(this::executorBroadcast)
            )
            .executes(this::executorDiscord);

        if (settings.permission != null && !settings.permission.isBlank())
            command.withPermission(settings.permission);

        return command;
    }

    private void executorDiscord(CommandSender sender, CommandArguments args) {
        for (String line : Cfg.get().messages)
            sender.sendMessage(MessageFormatter.format(line, sender));
    }

    private void executorBroadcast(CommandSender sender, CommandArguments args) {
        final var lines = Cfg.get().broadcastMessages;
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (String line : lines)
                player.sendMessage(MessageFormatter.format(line, player));
        }

        sender.sendMessage(ColorParser.of(Translation.of("commands.broadcast.sent"))
            .with("players", String.valueOf(Bukkit.getOnlinePlayers().size()))
            .build());
    }

    private void executorReload(CommandSender sender, CommandArguments args) {
        if (plugin.reloadConfiguration()) {
            plugin.getAnnouncer().restart();
            sender.sendMessage(Translation.as("commands.reload.success"));
        } else {
            sender.sendMessage(Translation.as("commands.reload.failure"));
        }
    }
}
