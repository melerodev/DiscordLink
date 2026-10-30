package io.github.melerodev.discordlink;

import io.github.melerodev.discordlink.announcer.Announcer;
import io.github.melerodev.discordlink.api.DiscordLinkAPI;
import io.github.melerodev.discordlink.command.CommandHandler;
import io.github.melerodev.discordlink.config.ConfigHandler;
import io.github.melerodev.discordlink.hook.HookManager;
import io.github.melerodev.discordlink.listener.ListenerHandler;
import io.github.melerodev.discordlink.threadutil.SchedulerHandler;
import io.github.melerodev.discordlink.translation.TranslationHandler;
import io.github.melerodev.discordlink.updatechecker.UpdateHandler;
import io.github.melerodev.discordlink.utility.Logger;
import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.melerodev.discordlink.translation.Translation;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Main class.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class DiscordLink extends AbstractDiscordLink {
    private static DiscordLink instance;

    // Handlers/Managers
    private ConfigHandler configHandler;
    private TranslationHandler translationHandler;
    private HookManager hookManager;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private UpdateHandler updateHandler;
    private SchedulerHandler schedulerHandler;
    private Announcer announcer;
    private DiscordLinkAPIProvider apiHandler;

    // Handlers list (defines order of load/enable/disable)
    private List<? extends Reloadable> handlers;

    @Override
    public void onLoad() {
        instance = this;

        configHandler = new ConfigHandler(this);
        translationHandler = new TranslationHandler(configHandler);
        hookManager = new HookManager(this);
        commandHandler = new CommandHandler(this);
        listenerHandler = new ListenerHandler(this);
        updateHandler = new UpdateHandler(this);
        schedulerHandler = new SchedulerHandler();
        announcer = new Announcer(this);
        apiHandler = new DiscordLinkAPIProvider(this);

        handlers = List.of(
            configHandler,
            translationHandler,
            hookManager,
            commandHandler,
            listenerHandler,
            updateHandler,
            schedulerHandler,
            announcer,
            apiHandler
        );

        for (Reloadable handler : handlers)
            handler.onLoad(instance);
    }

    @Override
    public void onEnable() {
        if (configHandler.getConfig() == null) {
            Logger.get().error(ColorParser.of("<red>config.yml could not be loaded, check the console for errors. Disabling <plugin>.").with("plugin", getName()).build());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        for (Reloadable handler : handlers)
            handler.onEnable(instance);
    }

    @Override
    public void onDisable() {
        if (handlers == null)
            return;

        for (Reloadable handler : handlers.reversed()) // If reverse doesn't work implement a new List with your desired disable order
            handler.onDisable(instance);
    }

    /**
     * Reloads the configuration and translation files without restarting the plugin.
     *
     * @return whether the configuration was reloaded successfully
     */
    public boolean reloadConfiguration() {
        final boolean success = configHandler.reload();
        Translation.setLocale(configHandler.getConfig().language);
        Translation.reload();
        return success;
    }

    /**
     * Gets plugin instance.
     *
     * @return the plugin instance
     */
    public static DiscordLink getPlugin() {
        return instance;
    }

    @Override
    public @NotNull ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public @NotNull HookManager getHookManager() {
        return hookManager;
    }

    public @NotNull Announcer getAnnouncer() {
        return announcer;
    }

    public @NotNull UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    public @NotNull DiscordLinkAPI getApiHandler() {
        return apiHandler;
    }
}
