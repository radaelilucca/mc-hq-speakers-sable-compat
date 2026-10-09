# CC:HQ Speakers Sable Compatibility

> Make CC:HQ Speakers audio follow moving Sable sublevels.

CC:HQ Speakers Sable Compatibility is a NeoForge addon for
[CC:HQ Speakers](https://www.curseforge.com/minecraft/mc-mods/cc-hq-speakers) and
[Sable](https://www.curseforge.com/minecraft/mc-mods/sable). It fixes positional audio for
speakers placed inside Sable sublevels, such as moving vehicles and
contraptions.

## What it does

Without this addon, a CC:HQ Speaker inside a Sable sublevel uses the
sublevel's local coordinates. Players near the visible contraption can then be
outside the server's audio recipient radius, or hear the sound from the wrong
place on the client.

This addon makes the speaker use its projected world position instead. Audio
is delivered to players around the moving sublevel and follows it as it moves.

It also raises the supported CC:HQ Speaker volume limit from 3 to 16. The
recipient radius scales with the selected volume: volume 1 reaches 16 blocks,
and volume 16 reaches up to 256 blocks.

The addon does not add blocks, items, recipes, or commands. It does not change
how CC:HQ Speakers work outside Sable sublevels.

## Compatibility

- Minecraft 1.21.1
- NeoForge 21.1.236 or newer in the 1.21.1 line
- [CC:HQ Speakers 1.0.4 or 1.1.4-neoforge-1.21.1](https://www.curseforge.com/minecraft/mc-mods/cc-hq-speakers)
- [Sable 2.0.5 for NeoForge](https://www.curseforge.com/minecraft/mc-mods/sable)

## Installation

1. Install NeoForge for Minecraft 1.21.1.
2. Put the matching CC:HQ Speakers JAR, Sable JAR, and this addon's JAR in the
   instance or server `mods` folder.
3. Start the game normally. No configuration is required.

## Development

The project uses Java 21. Place the matching Sable JAR in `libs/` and the
required world mods, including CC:Tweaked, in `run/mods/`. On Windows, run:

```powershell
.\scripts\gradle.ps1 build
.\scripts\gradle.ps1 runClientSingleplayer
```

The VS Code task `Minecraft: Run Client (Singleplayer)` opens the existing
`run/saves/New World` save directly. Set `singleplayer_world` in
`gradle.properties` to select another save folder. The Gradle helper selects
an installed Java 21 JDK and keeps its cache in `.gradle-user-home/`.

The published JAR is written to `build/libs/`. The HQ Speaker JAR in `libs/`
is used only to compile and test this compatibility addon; it is not bundled
into the output.

## License and credits

CC:HQ Speakers Sable Compatibility is distributed under the
[MIT License](LICENSE).

CC:HQ Speakers and Sable are separate projects owned by their respective
authors. This addon is independent and is not affiliated with or endorsed by
either project.

## AI disclosure

AI tools assisted with the research, implementation, and documentation of this
project.
