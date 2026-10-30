<h1 align="center">DiscordLink</h1>
<p align="center">
    <img alt="Licencia" src="https://img.shields.io/github/license/melerodev/DiscordLink?style=for-the-badge&color=blue&labelColor=141417">
    <img alt="Última versión" src="https://img.shields.io/github/v/release/melerodev/DiscordLink?include_prereleases&sort=semver&style=for-the-badge&label=VERSI%C3%93N&labelColor=141417">
    <img alt="CI" src="https://img.shields.io/github/actions/workflow/status/melerodev/DiscordLink/ci.yml?style=for-the-badge&labelColor=141417">
    <img alt="Paper" src="https://img.shields.io/badge/Paper%20%2F%20Folia-1.21.11-5865F2?style=for-the-badge&labelColor=141417">
</p>

---

## Descripción

**DiscordLink** añade un comando `/discord` que muestra el enlace de invitación de tu servidor de Discord.
Lo que empieza como "un comando que muestra un enlace fijo" es totalmente configurable: enlace, nombre del
comando, alias, permisos, varios mensajes con formato **MiniMessage** (clics, hover, degradados…) e incluso
anuncios automáticos.

Construido sobre la plantilla [Minecraft-Plugin-Template](https://github.com/milkdrinkers/Minecraft-Plugin-Template).

## Características

- 🔗 **Enlace configurable** disponible como `<link>` en todos los mensajes.
- 💬 **Varios mensajes**: tantas líneas como quieras al ejecutar `/discord`, más un mensaje distinto para difundir a todos.
- 🎨 **MiniMessage** completo (`<click>`, `<hover>`, `<gradient>`, colores hex…) y códigos legacy (`&a`, `&l`).
- 🏷️ **Alias y nombre del comando** configurables (`/discord`, `/dc`, `/disc`…).
- 🔐 **Permisos**: permiso configurable, y decides si los jugadores lo tienen por defecto.
- 🧩 **Placeholders propios** (`<server>`, `<website>`… definidos por ti), **PlaceholderAPI** y **MiniPlaceholders**.
- 📢 **Anunciador automático** opcional que recuerda el enlace cada X segundos.
- 🔄 `/discord reload` sin reiniciar el servidor.
- 🌍 Mensajes del plugin en inglés y español (`language: es_ES`).
- ⚡ Compatible con **Folia**.

## Requisitos

- Servidor **Paper** o **Folia** 1.21.11 (o compatible).
- **Java 21**.
- Opcional: [PlaceholderAPI](https://modrinth.com/plugin/placeholderapi), [MiniPlaceholders](https://modrinth.com/plugin/miniplaceholders).

## Instalación

1. Descarga el `.jar` desde [Releases](https://github.com/melerodev/DiscordLink/releases) (o compílalo, ver abajo).
2. Cópialo en la carpeta `plugins/` y reinicia el servidor.
3. Edita `plugins/DiscordLink/config.yml`, cambia `link` por tu invitación y ejecuta `/discord reload`.

## Comandos

| Comando              | Descripción                                          | Permiso                                    |
|----------------------|------------------------------------------------------|--------------------------------------------|
| `/discord`           | Muestra el enlace de Discord                         | `discordlink.command.discord` (configurable, por defecto todos) |
| `/discord broadcast` | Envía `broadcast-messages` a todos los jugadores     | `discordlink.command.broadcast` (OP)       |
| `/discord reload`    | Recarga `config.yml` y los idiomas                   | `discordlink.command.reload` (OP)          |

Alias por defecto: `/dc`, `/disc`. El nombre y los alias se cambian en `command.name` / `command.aliases`
(requiere reiniciar el servidor).

## Placeholders

En los mensajes:

| Placeholder       | Valor                                         |
|-------------------|-----------------------------------------------|
| `<link>`          | El enlace configurado                         |
| `<player>`        | Nombre del jugador que recibe el mensaje      |
| `<online>`        | Jugadores conectados                          |
| `<max_players>`   | Máximo de jugadores                           |
| `<clave>`         | Cualquier clave de la sección `placeholders`  |
| `%...%`           | Cualquier placeholder de PlaceholderAPI       |

PlaceholderAPI (para otros plugins como scoreboards o tab):

- `%discordlink_link%` → el enlace.
- `%discordlink_<clave>%` → cualquier valor de `placeholders`, p. ej. `%discordlink_website%`.

## Configuración

<details>
<summary><code>config.yml</code> por defecto</summary>

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

## API para desarrolladores

```java
String link = DiscordLinkAPI.getInstance().getLink();
```

## Compilar

```bash
./gradlew build
```

El jar queda en `paper/build/libs/`. Para probarlo en un servidor local: `./gradlew runServer`.

## Licencia

[MIT No Attribution](LICENSE). Basado en [milkdrinkers/Minecraft-Plugin-Template](https://github.com/milkdrinkers/Minecraft-Plugin-Template).
