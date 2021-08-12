package RenderTweaks.mixin;

import RenderTweaks.interfaces.IGameOptions;
import RenderTweaks.interfaces.IMinecraftClient;
import RenderTweaks.option.RenderTweakGameOptions;
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
    @Shadow @Final @Mutable public KeyBinding[] keysAll;
    @Shadow public double gamma;
    @Shadow protected MinecraftClient client;
    public RenderTweaksConfig config;
    public LinkedHashMap<String, Boolean> booleanOptions = new LinkedHashMap<>();
    public LinkedHashMap<String, Double> doubleOptions = new LinkedHashMap<>();
    public GameOptions thisGameOptions = (GameOptions)(Object)this;
    public RenderTweakGameOptions renderTweakGameOptions;
    public boolean enableWeather;
    public boolean fogEnabled;
    public boolean particlesEnabled;
    public boolean particlesBlockBreaking;
    public boolean derpyChicken;
    public double gammaOverride = 0.0D;
    public double prevGamma = 1.0D;

    public KeyBinding keyOptionScreen = new KeyBinding("Open Option Screen", GLFW.GLFW_KEY_O, "RenderTweaks");

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void onGameOptionsInitInjectAtTail(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        Option.RENDER_DISTANCE.setMax(64);
        this.renderTweakGameOptions = new RenderTweakGameOptions(this.client);
        ((IMinecraftClient)(this.client)).setRenderTweakGameOptions(this.renderTweakGameOptions);
    }

    @Inject(method = "load", at = @At(value = "HEAD"))
    private void onLoadInjectAtHead(CallbackInfo ci) {
        keysAll = ArrayUtils.add(keysAll, keyOptionScreen);
        initOptionMaps();
        config = new RenderTweaksConfig(new File(this.client.runDirectory, "RenderTweakoptions.txt"), thisGameOptions);
        config.loadConfigs();
        assignOptions();
    }

    private void initOptionMaps() {
        booleanOptions.put("environment.weather", true);
        booleanOptions.put("environment.fog", true);
        booleanOptions.put("particles.all", true);
        booleanOptions.put("particles.blockBreaking", true);
        booleanOptions.put("fun.derpyChicken", false);

        doubleOptions.put("environment.gammaOverride", 0.0D);
        doubleOptions.put("environment.prevGamma", 0.0D);
    }

    private void assignOptions() {
        try {
            this.enableWeather = booleanOptions.get("environment.weather");
            this.fogEnabled = booleanOptions.get("environment.fog");
            this.particlesEnabled = booleanOptions.get("particles.all");
            this.particlesBlockBreaking = booleanOptions.get("particles.blockBreaking");
            this.derpyChicken = booleanOptions.get("fun.derpyChicken");

            this.gammaOverride = doubleOptions.get("environment.gammaOverride");
            this.prevGamma = doubleOptions.get("environment.prevGamma");
        } catch (Exception e) {
            this.config.writeConfigs();
        }
    }

    public void storeOptionChanges() {
        booleanOptions.put("environment.weather", enableWeather);
        booleanOptions.put("environment.fog", fogEnabled);
        booleanOptions.put("particles.all", particlesEnabled);
        booleanOptions.put("particles.blockBreaking", particlesBlockBreaking);
        booleanOptions.put("fun.derpyChicken", derpyChicken);

        doubleOptions.put("environment.gammaOverride", gammaOverride);
        doubleOptions.put("environment.prevGamma", prevGamma);
    }

    @Override
    public void loadBooleanOptions(String key, boolean value) {
        booleanOptions.put(key, value);
    }

    @Override
    public void loadDoubleOptions(String key, double value) {
        doubleOptions.put(key, value);
    }

    @Override
    public LinkedHashMap<String, Boolean> getBooleanOptions() {
        return this.booleanOptions;
    }

    @Override
    public LinkedHashMap<String, Double> getDoubleOptions() {
        return this.doubleOptions;
    }

    @Override
    public RenderTweaksConfig getConfig() {
        return this.config;
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
    public double getPrevGamma() {
        return this.prevGamma;
    }

}
