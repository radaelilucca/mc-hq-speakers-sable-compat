# Compatibility audit: HQ Speaker 1.1.0

Date: 2026-10-23. Minecraft 1.21.1, NeoForge 21.1+, HQ Speaker 1.1.0.

## Result

The supplied NeoForge JAR identifies itself as mod `hqspeaker`, version
`1.1.0`, and declares Minecraft `[1.21.1,1.22)` and NeoForge `[21.1,)`.
The compatibility manifest now accepts the 1.1.x-and-later line starting at
`[1.1.0,)`, while preserving the previously supported 1.0.4 and 1.0.7–1.0.9
intervals. The former 1.1.4 NeoForge build is included by the open-ended range.

The supplied artifact SHA-256 is:

```text
D5E5D7623880C1B299387ACA5460F8937B2361ACD82FFA8D7EF03697BD429E28
```

## Mixin target inspection

The JAR contains the expected classes and target members used by the addon:

- `HQSpeakerPeripheral`: `computeWorldPos(String)`, `currentLevel()`,
  `currentBlockPos()`, `clampVol(double)`, `clampVolChecked(double,String)`,
  `sendToNearby(...)`, and `sendAudioChunkedToNearby(...)`.
- `HQSpeakerClientHandler`: both `resolvePacketWorldPos` overloads used by
  the client projection mixin.
- `HQSpeakerAudioPacket` and `HQSpeakerAudioChunkPacket`: the constructors
  targeted by the volume validation mixins have the expected descriptors.
- `HQSpeakerClientHandler$HQSpeakerSound`: the targeted sound class exists.

The range, volume, and packet constant adjustments declare `require = 0` for
version-dependent constants/invocations, so their absence does not fail
startup. The essential server and client projection method targets exist in
1.1.0. Inspection found no target signature requiring a Java or mixin change.

## Validation limits

The build was attempted with Java 21 and the supplied JAR, but Gradle failed
before task execution because its client could not connect to the local Gradle
daemon. This is an environment/daemon startup failure, so this audit does not
claim a successful build or an in-game mixin application test. Runtime audio
and Sable movement behavior remain unverified against 1.1.0.
