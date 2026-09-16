package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.compat.sable.SableProjection;
import com.tom.hqspeaker.peripheral.HQSpeakerPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * HQ Speakers uses this position on the logical server to choose recipients of
 * an audio packet. In a Sable sublevel the speaker's plot position is not its
 * world position, so the normal radius check excludes nearby players before
 * the client gets a chance to create a sound instance.
 */
@Mixin(value = HQSpeakerPeripheral.class, remap = false)
abstract class HQSpeakerPeripheralMixin {
    @Shadow
    protected abstract Level currentLevel();

    @Shadow
    protected abstract BlockPos currentBlockPos();

    @Inject(method = "computeWorldPos", at = @At("RETURN"), cancellable = true, remap = false)
    private void hqspeakerSableCompat$projectDispatchOrigin(
        String reason,
        CallbackInfoReturnable<float[]> callback
    ) {
        Level level = currentLevel();
        BlockPos blockPosition = currentBlockPos();
        if (level == null || blockPosition == null) return;

        Vec3 projected = SableProjection.project(level, Vec3.atCenterOf(blockPosition));
        if (projected == null) return;

        callback.setReturnValue(new float[] {(float) projected.x, (float) projected.y, (float) projected.z});
    }
}
