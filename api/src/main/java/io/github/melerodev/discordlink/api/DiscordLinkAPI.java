package io.github.melerodev.discordlink.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * The DiscordLinkAPI class is the main entry point for accessing the DiscordLink API.
 */
public abstract class DiscordLinkAPI {
    private static DiscordLinkAPI INSTANCE;

    /**
     * Gets the instance of the DiscordLinkAPI.
     *
     * @return the instance of DiscordLinkAPI
     * @since 1.0.0
     */
    public static DiscordLinkAPI getInstance() {
        if (INSTANCE == null)
            throw new RuntimeException("API was accessed before being initialized!");
        return INSTANCE;
    }

    /**
     * Sets the instance of the DiscordLinkAPI.
     * This method is intended for internal use by the api provider only.
     *
     * @param api the instance of DiscordLinkAPI to set
     * @since 1.0.0
     */
    @ApiStatus.Internal
    protected static void setInstance(DiscordLinkAPI api) {
        INSTANCE = api;
    }

    /**
     * Gets the configured Discord invite link.
     *
     * @return the link
     * @since 1.0.0
     */
    public abstract @NotNull String getLink();
}
