package RenderTweaks;

import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyRenderWeather();
    KeyBinding getKeyRenderBreakingParticles();
    KeyBinding getKeyRenderParticles();
    KeyBinding getKeyRenderFog();
    KeyBinding getKeyFullBright();
    KeyBinding getKeyDerpyChicken();
    KeyBinding getKeyOptionScreen();

    double getGamma();
    double getPrevGamma();

    boolean isWeatherEnabled();
    void setEnableWeather(boolean isWeather);

    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
