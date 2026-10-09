package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.peripheral.HQSpeakerPeripheral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * The recipient radius follows the configured speaker volume. At volume 1 the
 * speaker behaves like a local source (16 blocks); higher volumes proportionally
 * reach farther, up to 256 blocks at the compatibility maximum of 16.
 */
@Mixin(value = HQSpeakerPeripheral.class, remap = false)
abstract class HQSpeakerPeripheralRangeMixin {
    private static final double BASE_AUDIBLE_RADIUS = 16.0D;
    private static final double MAXIMUM_VOLUME = 16.0D;

    @Shadow
    private volatile float speakerDefaultVolume;

    @ModifyConstant(
        method = {"sendToNearby", "sendAudioChunkedToNearby"},
        constant = @Constant(doubleValue = 1024.0D),
        // Newer HQ Speaker versions calculate the radius through radiusSq();
        // the constant exists only in older supported versions.
        require = 0,
        remap = false
    )
    private double hqspeakerSableCompat$useVolumeScaledRecipientRadius(double originalRadiusSquared) {
        double volume = Math.clamp(speakerDefaultVolume, 0.0D, MAXIMUM_VOLUME);
        double radius = BASE_AUDIBLE_RADIUS * volume;
        return radius * radius;
    }
}
