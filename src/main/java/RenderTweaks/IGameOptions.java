package RenderTweaks;

import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyOptionScreen();

    double getGamma();
    double getPrevGamma();

    boolean isDerpyChicken();
    boolean isWeatherEnabled();

    void setDerpyChicken(boolean isDerpyChicken);
    void setWeather(boolean isWeather);
    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
