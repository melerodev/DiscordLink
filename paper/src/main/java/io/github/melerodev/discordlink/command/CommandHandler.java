package io.github.melerodev.discordlink.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIPaperConfig;
import io.github.melerodev.discordlink.AbstractDiscordLink;
import io.github.melerodev.discordlink.DiscordLink;
import io.github.melerodev.discordlink.Reloadable;
import io.github.melerodev.discordlink.config.PluginConfig;
import io.github.melerodev.discordlink.utility.Cfg;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

/**
 * A class to handle registration of commands.
 */
public class CommandHandler implements Reloadable {
    public static final String BASE_PERM = "discordlink.command";
    private final DiscordLink plugin;
    private String registeredPermission;

    /**
     * Instantiates the Command handler.
     *
     * @param plugin the plugin
     */
    public CommandHandler(DiscordLink plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(AbstractDiscordLink plugin) {
        CommandAPI.onLoad(
            new CommandAPIPaperConfig(plugin)
                .silentLogs(true)
        );
    }

    @Override
    public void onEnable(AbstractDiscordLink plugin) {
        if (!CommandAPI.isLoaded())
            return;

        CommandAPI.onEnable();

        final PluginConfig.CommandSettings settings = Cfg.get().command;
        registerPermission(plugin.getServer().getPluginManager(), settings);

        // Register commands here
        new DiscordCommand(this.plugin)
            .command()
            .withAliases(settings.aliases.toArray(String[]::new))
            .register();
    }

    @Override
    public void onDisable(AbstractDiscordLink plugin) {
        if (registeredPermission != null) {
            plugin.getServer().getPluginManager().removePermission(registeredPermission);
            registeredPermission = null;
        }

        if (!CommandAPI.isLoaded())
            return;

        CommandAPI.onDisable();
    }

    /**
     * Registers the configurable command permission so its default can be controlled from the config.
     */
    private void registerPermission(PluginManager pluginManager, PluginConfig.CommandSettings settings) {
        if (settings.permission == null || settings.permission.isBlank())
            return;

        if (pluginManager.getPermission(settings.permission) != null)
            return;

        pluginManager.addPermission(new Permission(
            settings.permission,
            "Allows using /" + settings.name,
            settings.grantedByDefault ? PermissionDefault.TRUE : PermissionDefault.OP
        ));
        registeredPermission = settings.permission;
    }
}
