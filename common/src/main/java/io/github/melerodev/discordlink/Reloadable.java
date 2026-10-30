package io.github.melerodev.discordlink;

/**
 * Implemented in classes that should support being reloaded IE executing the methods during runtime after startup.
 */
public interface Reloadable {
    /**
     * On plugin load.
     */
    default void onLoad(AbstractDiscordLink plugin) {
    }

    /**
     * On plugin enable.
     */
    default void onEnable(AbstractDiscordLink plugin) {
    }

    /**
     * On plugin disable.
     */
    default void onDisable(AbstractDiscordLink plugin) {
    }

}
