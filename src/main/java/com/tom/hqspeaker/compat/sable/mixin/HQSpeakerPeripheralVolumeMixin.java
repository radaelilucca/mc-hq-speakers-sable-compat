package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.peripheral.HQSpeakerPeripheral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Keeps the Lua volume ceiling at the compatibility maximum across HQ Speaker versions. */
@Mixin(value = HQSpeakerPeripheral.class, remap = false)
abstract class HQSpeakerPeripheralVolumeMixin {
    @Redirect(
        method = {"clampVol", "clampVolChecked"},
        at = @At(
            value = "INVOKE",
            target = "Lcom/tom/hqspeaker/HQSpeakerConfig;maxVolume()F"
        ),
        require = 0,
        remap = false
    )
    private static float hqspeakerSableCompat$useCompatibilityVolumeMaximum() {
        return 16.0F;
    }

    @ModifyConstant(
        method = {"clampVol", "clampVolChecked"},
        constant = @Constant(doubleValue = 3.0D),
        // Newer HQ Speaker versions take their maximum from config.
        require = 0,
        remap = false
    )
    private static double hqspeakerSableCompat$raiseLuaVolumeCeiling(double originalMaximum) {
        return 16.0D;
    }
}
