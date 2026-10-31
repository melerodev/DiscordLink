<h1 align="center">DiscordLink</h1>
<p align="center">
    <img alt="License" src="https://img.shields.io/github/license/melerodev/DiscordLink?style=for-the-badge&color=blue&labelColor=141417">
    <img alt="Latest version" src="https://img.shields.io/github/v/release/melerodev/DiscordLink?include_prereleases&sort=semver&style=for-the-badge&label=VERSION&labelColor=141417">
    <img alt="CI" src="https://img.shields.io/github/actions/workflow/status/melerodev/DiscordLink/ci.yml?style=for-the-badge&labelColor=141417">
    <img alt="Paper" src="https://img.shields.io/badge/Paper%20%2F%20Folia-1.20.6%20%E2%86%92%2026.3-5865F2?style=for-the-badge&labelColor=141417">
    <a href="https://discord.gg/rMuWwZzCW2"><img alt="Discord" src="https://img.shields.io/badge/Discord-Join-5865F2?style=for-the-badge&logo=discord&logoColor=white&labelColor=141417"></a>
</p>

---

## Description

**DiscordLink** adds a `/discord` command that shows your Discord server's invite link.
What starts as "a command that shows a fixed link" is fully configurable: the link, the command name,
aliases, permissions, multiple **MiniMessage** lines (clicks, hover, gradients…) and even automatic
announcements.

Built on top of [Minecraft-Plugin-Template](https://github.com/milkdrinkers/Minecraft-Plugin-Template).

## Features

- 🔗 **Configurable link**, available as `<link>` in every message.
- 💬 **Multiple messages**: as many lines as you want for `/discord`, plus a separate message to broadcast to everyone.
- 🎨 Full **MiniMessage** support (`<click>`, `<hover>`, `<gradient>`, hex colors…) and legacy codes (`&a`, `&l`).
- 🏷️ **Configurable command name and aliases** (`/discord`, `/dc`, `/disc`…).
- 🔐 **Permissions**: configurable permission, and you decide whether players have it by default.
- 🧩 **Your own placeholders** (`<server>`, `<website>`… defined by you), **PlaceholderAPI** and **MiniPlaceholders**.
- 📢 Optional **automatic announcer** that reminds players of the link every X seconds.
- 🔄 `/discord reload` without restarting the server.
- 🌍 Plugin messages in English and Spanish (`language: es_ES`).
- ⚡ **Folia** supported.

## Requirements

- **Paper** or **Folia** server, **1.20.6 to 26.3**.
- **Java 21** (Minecraft 1.20.6 – 1.21.x) or **Java 25** (Minecraft 26.x).
- Does not run on Spigot or on versions older than 1.20.6.
- Optional: [PlaceholderAPI](https://modrinth.com/plugin/placeholderapi), [MiniPlaceholders](https://modrinth.com/plugin/miniplaceholders).

Tested versions: 1.20.6, 1.21.1, 1.21.4, 1.21.8, 1.21.11, 26.1.2, 26.2 and 26.3.

## Installation

1. Download the `.jar` from [Releases](https://github.com/melerodev/DiscordLink/releases) (or build it, see below).
2. Put it in your `plugins/` folder and restart the server.
3. Edit `plugins/DiscordLink/config.yml`, set `link` to your invite and run `/discord reload`.

## Commands

| Command              | Description                                          | Permission                                 |
|----------------------|------------------------------------------------------|--------------------------------------------|
| `/discord`           | Shows the Discord link                               | `discordlink.command.discord` (configurable, everyone by default) |
| `/discord broadcast` | Sends `broadcast-messages` to every player           | `discordlink.command.broadcast` (OP)       |
| `/discord reload`    | Reloads `config.yml` and the language files          | `discordlink.command.reload` (OP)          |

Default aliases: `/dc`, `/disc`. The name and aliases are set in `command.name` / `command.aliases`
(requires a server restart).

## Placeholders

In messages:

| Placeholder       | Value                                         |
|-------------------|-----------------------------------------------|
| `<link>`          | The configured link                           |
| `<player>`        | Name of the player receiving the message      |
| `<online>`        | Online players                                |
| `<max_players>`   | Max players                                   |
| `<key>`           | Any key from the `placeholders` section       |
| `%...%`           | Any PlaceholderAPI placeholder                |

PlaceholderAPI (for other plugins such as scoreboards or tab):

- `%discordlink_link%` → the link.
- `%discordlink_<key>%` → any value from `placeholders`, e.g. `%discordlink_website%`.

## Configuration

<details>
<summary>Default <code>config.yml</code></summary>

```yaml
# Your Discord invite link. Use it in any message with <link>.
link: https://discord.gg/your-invite

# Extra placeholders you can use in every message, for example <server> or <website>.
placeholders:
  server: My Server
  website: https://example.com

# Command settings. Changing the name or aliases requires a server restart.
command:
  name: discord
  aliases:
    - dc
    - disc
  # Permission needed to use the command. Leave empty so everyone can use it.
  permission: discordlink.command.discord
  # Should players have the permission above by default?
  granted-by-default: true

# Lines sent to the player when they run the command.
messages:
  - <dark_gray><strikethrough>                                        </strikethrough>
  - <#5865F2><bold>Discord</bold> <gray>» <white>Join the <server> community!
  - '<gray>Click here: <click:open_url:''<link>''><hover:show_text:''<#5865F2>Open <link>''><aqua><underlined><link></underlined></aqua></hover></click>'
  - <dark_gray><strikethrough>                                        </strikethrough>

# Lines broadcast to everyone with `/discord broadcast`.
broadcast-messages:
  - <#5865F2><bold>Discord</bold> <gray>» <white>Have you joined our Discord yet? <click:open_url:'<link>'><aqua><underlined><link></underlined></aqua></click>

# Automatic announcements of your Discord link.
announcer:
  enabled: false
  interval-seconds: 900
  skip-when-empty: true
  messages:
    - <#5865F2><bold>Discord</bold> <gray>» <white>Events, giveaways and support at <click:open_url:'<link>'><aqua><underlined><link></underlined></aqua></click>
    - <#5865F2><bold>Discord</bold> <gray>» <white>Use <yellow>/discord</yellow> to get the invite link!

update-checker:
  enabled: true
  console: true
  op: true

# en_US or es_ES
language: en_US
```

</details>

## Developer API

```java
String link = DiscordLinkAPI.getInstance().getLink();
```

## Building

```bash
./gradlew build
```

The jar ends up in `paper/build/libs/`. To try it on a local server:

```bash
./gradlew runServer                      # Paper 1.21.11
./gradlew runServer -PmcVersion=1.20.6   # any other version
```

## Support

Questions, suggestions or problems? Join our Discord:

<a href="https://discord.gg/rMuWwZzCW2"><img alt="Join our Discord" src="docs/assets/discord.png" width="300"></a>

You can also open an [issue](https://github.com/melerodev/DiscordLink/issues).

## License

[GNU General Public License v3.0](LICENSE). Based on [milkdrinkers/Minecraft-Plugin-Template](https://github.com/milkdrinkers/Minecraft-Plugin-Template).
