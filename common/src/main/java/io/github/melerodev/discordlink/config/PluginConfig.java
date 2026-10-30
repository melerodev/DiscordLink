package io.github.melerodev.discordlink.config;

import io.github.melerodev.discordlink.config.exception.ConfigValidationException;
import io.github.melerodev.discordlink.config.migration.Migration;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.interfaces.meta.Exclude;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ConfigSerializable
public class PluginConfig implements VersionedConfig {
    public static final String HEADER = """
        DiscordLink configuration
        
        All messages support MiniMessage (https://docs.advntr.dev/minimessage/format),
        legacy color codes (&a, &l...), PlaceholderAPI (%placeholder%) and MiniPlaceholders.
        
        Built-in placeholders: <link>, <player>, <online>, <max_players> and every key in `placeholders`.""";

    @Comment("Do not change this value!")
    public int configVersion = 1;

    @Override
    @Exclude
    public int configVersion() {
        return configVersion;
    }

    @Override
    @Exclude
    public @NotNull Map<Integer, Migration> migrations() {
        return Map.of();
    }

    @Override
    @Exclude
    public void validate() throws ConfigValidationException {
        if (link == null || link.isBlank())
            throw new ConfigValidationException("`link` cannot be empty");

        if (command.name == null || command.name.isBlank() || command.name.contains(" "))
            throw new ConfigValidationException("`command.name` must be a single word");

        if (announcer.intervalSeconds < 10)
            throw new ConfigValidationException("`announcer.interval-seconds` must be at least 10");
    }

    @Comment("Your Discord invite link. Use it in any message with <link>.")
    public String link = "https://discord.gg/your-invite";

    @Comment("Extra placeholders you can use in every message, for example <server> or <website>.")
    public Map<String, String> placeholders = defaultPlaceholders();

    private static Map<String, String> defaultPlaceholders() {
        final Map<String, String> placeholders = new LinkedHashMap<>();
        placeholders.put("server", "My Server");
        placeholders.put("website", "https://example.com");
        return placeholders;
    }

    @Comment("Command settings. Changing the name or aliases requires a server restart.")
    public CommandSettings command = new CommandSettings();

    @ConfigSerializable
    public static class CommandSettings {
        @Comment("Main command name (without the slash).")
        public String name = "discord";

        @Comment("Command aliases.")
        public List<String> aliases = List.of("dc", "disc");

        @Comment("Permission needed to use the command. Leave empty so everyone can use it.")
        public String permission = "discordlink.command.discord";

        @Comment("Should players have the permission above by default?")
        public boolean grantedByDefault = true;
    }

    @Comment("Lines sent to the player when they run the command. Every entry is sent as a separate line.")
    public List<String> messages = List.of(
        "<dark_gray><strikethrough>                                        </strikethrough>",
        "<#5865F2><bold>Discord</bold> <gray>» <white>Join the <server> community!",
        "<gray>Click here: <click:open_url:'<link>'><hover:show_text:'<#5865F2>Open <link>'><aqua><underlined><link></underlined></aqua></hover></click>",
        "<dark_gray><strikethrough>                                        </strikethrough>"
    );

    @Comment("Lines broadcast to everyone with `/discord broadcast`.")
    public List<String> broadcastMessages = List.of(
        "<#5865F2><bold>Discord</bold> <gray>» <white>Have you joined our Discord yet? <click:open_url:'<link>'><hover:show_text:'<#5865F2>Open <link>'><aqua><underlined><link></underlined></aqua></hover></click>"
    );

    @Comment("Automatic announcements of your Discord link.")
    public Announcer announcer = new Announcer();

    @ConfigSerializable
    public static class Announcer {
        @Comment("Enable automatic announcements?")
        public boolean enabled = false;

        @Comment("Seconds between announcements (minimum 10).")
        public int intervalSeconds = 900;

        @Comment("Skip the announcement when no players are online.")
        public boolean skipWhenEmpty = true;

        @Comment("Announcements, sent in order (one entry per announcement). Use \\n inside an entry for multiple lines.")
        public List<String> messages = List.of(
            "<#5865F2><bold>Discord</bold> <gray>» <white>Events, giveaways and support at <click:open_url:'<link>'><aqua><underlined><link></underlined></aqua></click>",
            "<#5865F2><bold>Discord</bold> <gray>» <white>Use <yellow>/discord</yellow> to get the invite link!"
        );
    }

    @Comment("Update Checker Settings")
    public UpdateChecker updateChecker = new UpdateChecker();

    @ConfigSerializable
    public static class UpdateChecker {
        @Comment("Should the plugin check for plugin updates on startup?")
        public boolean enabled = true;

        @Comment("Send update notifications to the console?")
        public boolean console = true;

        @Comment("Send update notifications to opped players on join?")
        public boolean op = true;
    }

    @Comment("Language, specify the language file to use, for example `en_US` or `es_ES` which will load `/lang/<language>.json`")
    public String language = "en_US";
}
