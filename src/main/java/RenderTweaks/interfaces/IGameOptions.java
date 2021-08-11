package RenderTweaks.interfaces;

import RenderTweaks.option.RenderTweaksConfig;
import net.minecraft.client.option.KeyBinding;

import java.util.LinkedHashMap;

public interface IGameOptions {
    KeyBinding getKeyOptionScreen();

    boolean isDerpyChicken();
    boolean isWeatherEnabled();
    boolean isParticlesEnabled();
    boolean isParticlesBlockBreakingEnabled();
    boolean isFogEnabled();
    double getPrevGamma();
    double gammaOverride();
    LinkedHashMap<String, Boolean> getBooleanOptions();
    LinkedHashMap<String, Double> getDoubleOptions();
    RenderTweaksConfig getConfig();

    void setDerpyChicken(boolean isDerpyChicken);
    void setWeather(boolean isWeather);
    void setParticlesEnabled(boolean isParticles);
    void setParticlesBlockBreaking(boolean isParticlesBlockBreaking);
    void setFog(boolean isFog);
    void setGammaOverride(double isGammaOverride);
    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
    void loadBooleanOptions(String key, boolean value);
    void loadDoubleOptions(String key, double value);
    void storeOptionChanges();
}
