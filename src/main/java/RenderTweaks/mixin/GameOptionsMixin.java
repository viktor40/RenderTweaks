package RenderTweaks.mixin;

import RenderTweaks.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
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

    @Shadow public double gamma;
    public boolean enableWeather = true;
    public boolean derpyChicken = true;
    public double gammaOverride = 0.0D;
    public double prevGamma = 1.0D;

    public KeyBinding keyOptionScreen = new KeyBinding("Open Option Screen", GLFW.GLFW_KEY_O, "RenderTweaks");

    @Inject(method = "load", at = @At(value = "HEAD"))
    private void onLoadInjectAtHead(CallbackInfo ci) {
        keysAll = ArrayUtils.add(keysAll, keyOptionScreen);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void onGameOptionsInitInjectAtTail(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        Option.RENDER_DISTANCE.setMax(64);
    }

    @Override
    public void setDerpyChicken(boolean isDerpyChicken) {
        derpyChicken = isDerpyChicken;
    }

    @Override
    public void setWeather(boolean isWeather) {
        enableWeather = isWeather;
    }

    @Override
    public boolean isDerpyChicken() {
        return derpyChicken;
    }

    @Override
    public double gammaOverride() {
        return gammaOverride;
    }

    @Override
    public boolean isWeatherEnabled() {
        return enableWeather;
    }

    @Override
    public KeyBinding getKeyOptionScreen() {
        return keyOptionScreen;
    }

    @Override
    public double getGamma() {
        return this.gamma;
    }

    @Override
    public double getPrevGamma() {
        return this.prevGamma;
    }

    @Override
    public void setGammaOverride(double isGammaOverride) {
        this.gammaOverride = isGammaOverride;
    }

    @Override
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void setPrevGamma(double prevGamma) {
        this.prevGamma = prevGamma;
    }
}
