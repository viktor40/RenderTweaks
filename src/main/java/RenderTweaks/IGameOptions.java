package RenderTweaks;

import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyOptionScreen();

    double getGamma();
    double getPrevGamma();

    boolean isDerpyChicken();
    boolean isWeatherEnabled();
    double gammaOverride();

    void setDerpyChicken(boolean isDerpyChicken);
    void setWeather(boolean isWeather);
    void setGammaOverride(double isGammaOverride);
    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
