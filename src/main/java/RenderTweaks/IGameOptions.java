package RenderTweaks;

import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyOptionScreen();

    double getGamma();
    double getPrevGamma();

    boolean isDerpyChicken();
    boolean isWeatherEnabled();
    boolean isParticlesEnabled();
    boolean isParticlesBlockBreakingEnabled();
    boolean isFogEnabled();
    double gammaOverride();

    void setDerpyChicken(boolean isDerpyChicken);
    void setWeather(boolean isWeather);
    void setParticlesEnabled(boolean isParticles);
    void setParticlesBlockBreaking(boolean isParticlesBlockBreaking);
    void setFog(boolean isFog);
    void setGammaOverride(double isGammaOverride);
    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
