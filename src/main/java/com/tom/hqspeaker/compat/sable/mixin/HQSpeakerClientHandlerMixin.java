package com.tom.hqspeaker.compat.sable.mixin;

import com.tom.hqspeaker.compat.sable.SableProjection;
import com.tom.hqspeaker.client.HQSpeakerClientHandler;
import com.tom.hqspeaker.network.HQSpeakerAudioPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * HQ Speakers already invokes its resolver before starting sounds and every
 * client tick. Replacing the resolver's result therefore fixes both startup
 * culling and moving-contraption audio without duplicating its playback logic.
 */
@Mixin(value = HQSpeakerClientHandler.class, remap = false)
abstract class HQSpeakerClientHandlerMixin {
    @Inject(method = "resolvePacketWorldPos", at = @At("RETURN"), cancellable = true, remap = false)
    private static void hqspeakerSableCompat$projectOutOfSubLevel(
        Level level,
        HQSpeakerAudioPacket packet,
        CallbackInfoReturnable<float[]> callback
    ) {
        if (level == null) return;

        BlockPos blockPosition = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
        Vec3 projected = SableProjection.project(level, Vec3.atCenterOf(blockPosition));
        if (projected == null) return;

        callback.setReturnValue(new float[] {(float) projected.x, (float) projected.y, (float) projected.z});
    }
}
