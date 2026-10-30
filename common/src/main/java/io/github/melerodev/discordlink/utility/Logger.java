package io.github.melerodev.discordlink.utility;


import io.github.melerodev.discordlink.AbstractDiscordLink;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides shorthand access to {@link AbstractDiscordLink#getComponentLogger}.
 */
public class Logger {
    /**
     * Get component logger. Shorthand for:
     *
     * @return the component logger {@link AbstractDiscordLink#getComponentLogger}.
     */
    @NotNull
    public static ComponentLogger get() {
        return AbstractDiscordLink.getInstance().getComponentLogger();
    }
}
