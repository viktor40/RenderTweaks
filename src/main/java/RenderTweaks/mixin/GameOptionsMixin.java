package RenderTweaks.mixin;

import RenderTweaks.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin implements IGameOptions {
    @Shadow
    @Final
    @Mutable
    public KeyBinding[] keysAll;

    public KeyBinding keyRenderWeather;
    public KeyBinding keyRenderBreakingParticles;
    public KeyBinding keyRenderFog;

    @Inject(method = "load", at = @At(value = "HEAD"))
    private void onLoadInjectAtHead(CallbackInfo ci) {
        keyRenderWeather = new KeyBinding("Toggle Weather", GLFW.GLFW_KEY_R, "RenderTweaks");
        keyRenderBreakingParticles = new KeyBinding("Toggle Block Breaking Particles", GLFW.GLFW_KEY_B, "RenderTweaks");
        keyRenderFog = new KeyBinding("Toggle Fog", GLFW.GLFW_KEY_G, "RenderTweaks");
        keysAll = ArrayUtils.add(keysAll, keyRenderWeather);
        keysAll = ArrayUtils.add(keysAll, keyRenderBreakingParticles);
        keysAll = ArrayUtils.add(keysAll, keyRenderFog);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void onGameOptionsInitInjectAtTail(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        Option.RENDER_DISTANCE.setMax(64);

    }

    @Override
    public KeyBinding getKeyRenderWeather() {
        return keyRenderWeather;
    }

    @Override
    public KeyBinding getKeyRenderBreakingParticles() {
        return keyRenderBreakingParticles;
    }

    @Override
    public KeyBinding getKeyRenderFog() {
        return keyRenderFog;
    }
}
