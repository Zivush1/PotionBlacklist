# PotionBlacklist Plugin

PotionBlacklist is a Minecraft plugin that allows server administrators to prevent specific players from using potions.

## Features

- Blacklist players from using all types of potions (drinkable, splash, and lingering)
- Simple command to add or remove players from the blacklist
- Configurable messages
- Persistent blacklist across server restarts

## Installation

1. Download the latest `PotionBlacklist.jar` file from the releases page.
2. Place the JAR file in your server's `plugins` folder.
3. Restart your server or run the command `/reload confirm` if your server supports it.

## Usage

### Commands

- `/potion blacklist <player>` - Add or remove a player from the potion blacklist
    - Requires the `potion.blacklist` permission

### Permissions

- `potion.blacklist` - Allows use of the `/potion blacklist` command (default: op)

### Configuration

The `config.yml` file will be generated in the `plugins/PotionBlacklist` folder after the first run. You can customize messages and view the blacklisted players (by UUID) in this file.
