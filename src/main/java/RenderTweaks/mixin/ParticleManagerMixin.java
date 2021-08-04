package RenderTweaks.mixin;

import RenderTweaks.IMinecraftClient;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void cancelBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!((IMinecraftClient)client).renderBreakingParticles()) {
            ci.cancel();
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void cancelParticles(Particle particle, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!((IMinecraftClient)client).renderWeather() && (particle instanceof BlockLeakParticle)) {
            ci.cancel();
        } else if (!((IMinecraftClient)client).renderParticles()) {
            ci.cancel(); // Cancel all particles
        }
    }
}
