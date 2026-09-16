package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.peripheral.HQSpeakerPeripheral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/** Raises the Lua volume ceiling used by HQ Speakers from 3 to 16. */
@Mixin(value = HQSpeakerPeripheral.class, remap = false)
abstract class HQSpeakerPeripheralVolumeMixin {
    @ModifyConstant(
        method = {"clampVol", "clampVolChecked"},
        constant = @Constant(doubleValue = 3.0D),
        require = 2,
        remap = false
    )
    private static double hqspeakerSableCompat$raiseLuaVolumeCeiling(double originalMaximum) {
        return 16.0D;
    }
}
