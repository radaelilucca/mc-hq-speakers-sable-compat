package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.client.HQSpeakerClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Preserve Minecraft's linear attenuation, but allow the same compatibility
 * volume ceiling as the server-side sender.
 */
@Mixin(value = HQSpeakerClientHandler.HQSpeakerSound.class, remap = false)
abstract class HQSpeakerSoundMixin {
    @ModifyConstant(
        method = "<init>(Lcom/tom/hqspeaker/client/HQAudioStream;Lcom/tom/hqspeaker/network/HQSpeakerAudioPacket;FFF)V",
        constant = @Constant(floatValue = 3.0F),
        // Newer versions already allow volume 16.
        require = 0,
        remap = false
    )
    private float hqspeakerSableCompat$raiseClientVolumeCeiling(float originalMaximum) {
        return 16.0F;
    }
}
