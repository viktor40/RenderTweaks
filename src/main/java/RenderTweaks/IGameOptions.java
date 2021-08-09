package RenderTweaks;

import net.minecraft.client.options.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyRenderWeather();
    KeyBinding getKeyRenderBreakingParticles();
    KeyBinding getKeyRenderParticles();
    KeyBinding getKeyRenderFog();
    KeyBinding getKeyFullBright();
    KeyBinding getKeyDerpyChicken();

    double getGamma();
    double getPrevGamma();

    void setPrevGamma(double prevGamma);
    void setGamma(double gamma);
}
