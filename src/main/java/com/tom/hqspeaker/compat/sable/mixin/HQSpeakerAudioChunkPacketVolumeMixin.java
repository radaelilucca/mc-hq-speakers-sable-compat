package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.network.HQSpeakerAudioChunkPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Keeps chunked MP3/OGG/WAV payload validation aligned with the Lua API. */
@Mixin(value = HQSpeakerAudioChunkPacket.class, remap = false)
abstract class HQSpeakerAudioChunkPacketVolumeMixin {
    @ModifyConstant(
        method = "<init>(Ljava/util/UUID;Ljava/util/UUID;Lcom/tom/hqspeaker/network/HQSpeakerAudioPacket$AudioFormat;FFFFIIIJLjava/util/UUID;IIII[B)V",
        constant = @Constant(floatValue = 3.0F),
        // Newer versions already validate volume up to 16.
        require = 0,
        remap = false
    )
    private float hqspeakerSableCompat$raiseChunkVolumeCeiling(float originalMaximum) {
        return 16.0F;
    }
}
