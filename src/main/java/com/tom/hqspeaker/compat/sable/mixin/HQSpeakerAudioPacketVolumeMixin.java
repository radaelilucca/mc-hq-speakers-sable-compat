package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.network.HQSpeakerAudioPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Keeps the audio packet's validation ceiling aligned with the Lua API. */
@Mixin(value = HQSpeakerAudioPacket.class, remap = false)
abstract class HQSpeakerAudioPacketVolumeMixin {
    @ModifyConstant(
        method = "<init>(Ljava/util/UUID;Lcom/tom/hqspeaker/network/HQSpeakerAudioPacket$AudioFormat;FFFFIII[BLjava/lang/String;JLjava/util/UUID;I)V",
        constant = @Constant(floatValue = 3.0F),
        require = 1,
        remap = false
    )
    private float hqspeakerSableCompat$raisePacketVolumeCeiling(float originalMaximum) {
        return 16.0F;
    }
}
