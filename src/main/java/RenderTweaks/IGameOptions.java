package RenderTweaks;

import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyRenderWeather();
    KeyBinding getKeyRenderBreakingParticles();
    KeyBinding getKeyRenderParticles();
    KeyBinding getKeyRenderFog();
    KeyBinding getKeyFullBright();

    double getGamma();
    double getPrevGamma();

    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
