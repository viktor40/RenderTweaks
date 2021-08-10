package RenderTweaks.mixin;

import RenderTweaks.IGameOptions;
import RenderTweaks.option.RenderTweaksConfig;
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
import java.util.LinkedHashMap;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin implements IGameOptions {
    @Shadow
    @Final
    @Mutable
    public KeyBinding[] keysAll;
    public RenderTweaksConfig config;
    public LinkedHashMap<String, Boolean> booleanOptions;
    public LinkedHashMap<String, Boolean> doubleOptions;

    @Shadow public double gamma;
    @Shadow protected MinecraftClient client;
    public boolean enableWeather;
    public boolean derpyChicken;
    public boolean particlesEnabled;
    public boolean particlesBlockBreaking;
    public boolean fogEnabled;
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
        config = new RenderTweaksConfig(new File(this.client.runDirectory, "RenderTweakoptions.txt"));
        config.loadConfigs();
    }

    private LinkedHashMap<String, Boolean> initBooleanOptions() {
        LinkedHashMap<String, Boolean> options = new LinkedHashMap<String, Boolean>();
        options.put("", false);
        return options;
    }

    private LinkedHashMap<String, Double> initDoubleOptions() {
        LinkedHashMap<String, Double> options = new LinkedHashMap<String, Double>();
        options.put("", 0.0D);
        return options;
    }

    @Override
    public KeyBinding getKeyOptionScreen() {
        return keyOptionScreen;
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
    public void setParticlesEnabled(boolean isParticles) {
        particlesEnabled = isParticles;
    }

    @Override
    public void setParticlesBlockBreaking(boolean isParticlesBlockBreaking) {
        particlesBlockBreaking = isParticlesBlockBreaking;
    }

    @Override
    public void setFog(boolean isFOg) {
        fogEnabled = isFOg;
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

    @Override
    public boolean isDerpyChicken() {
        return derpyChicken;
    }

    @Override
    public boolean isWeatherEnabled() {
        return enableWeather;
    }

    @Override
    public boolean isParticlesEnabled() {
        return particlesEnabled;
    }

    @Override
    public boolean isParticlesBlockBreakingEnabled() {
        return particlesBlockBreaking;
    }

    @Override
    public boolean isFogEnabled() {
        return fogEnabled;
    }

    @Override
    public double gammaOverride() {
        return gammaOverride;
    }

    @Override
    public double getGamma() {
        return this.gamma;
    }

    @Override
    public double getPrevGamma() {
        return this.prevGamma;
    }

}
