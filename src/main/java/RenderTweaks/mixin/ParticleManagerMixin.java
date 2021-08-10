package RenderTweaks.mixin;

import RenderTweaks.interfaces.IGameOptions;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void cancelBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        GameOptions options = MinecraftClient.getInstance().options;
        if (!((IGameOptions)options).isParticlesBlockBreakingEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void cancelParticles(Particle particle, CallbackInfo ci) {
        GameOptions options = MinecraftClient.getInstance().options;
        if (!((IGameOptions)options).isWeatherEnabled() && (particle instanceof BlockLeakParticle.Dripping)) {
            ci.cancel(); // Cancel block leak particlesEnabled from rain
        } else if (!((IGameOptions)options).isParticlesBlockBreakingEnabled() && (particle instanceof CrackParticle)) {
            ci.cancel(); // Cancel block breaking particlesEnabled
        } else if (!((IGameOptions)options).isParticlesEnabled()) {
            ci.cancel(); // Cancel all particlesEnabled
        }
    }
}
