# CC:HQ Speakers Sable Compatibility

> Make CC:HQ Speakers audio follow moving Sable sublevels.

CC:HQ Speakers Sable Compatibility is a NeoForge addon for
[CC:HQ Speakers](https://github.com/tiktop101/CC-HQ-Speakers) and
[Sable](https://github.com/ryanhcode/sable). It fixes positional audio for
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
- [CC:HQ Speakers 1.1.4-neoforge-1.21.1](https://github.com/tiktop101/CC-HQ-Speakers)
- [Sable 2.0.5 for NeoForge](https://github.com/ryanhcode/sable/releases)

The mod intentionally supports only these tested dependency ranges:

- CC:HQ Speakers `>= 1.1.4-neoforge-1.21.1` and `< 1.1.5`
- Sable `>= 2.0.5` and `< 2.1.0`

Install CC:HQ Speakers, Sable, and this addon together on both the client and
server. Keep the same versions on every side of a multiplayer connection.

## Installation

1. Install NeoForge for Minecraft 1.21.1.
2. Put the matching CC:HQ Speakers JAR, Sable JAR, and this addon's JAR in the
   instance or server `mods` folder.
3. Start the game normally. No configuration is required.

## Development

The project uses Java 21. For a local client run, place the matching Sable JAR
in `libs/` and run:

```powershell
.\gradlew.bat build
.\gradlew.bat runClient
```

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
