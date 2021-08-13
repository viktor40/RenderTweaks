package RenderTweaks.interfaces;

import RenderTweaks.option.RenderTweaksGameOptions;
import net.minecraft.client.option.KeyBinding;

public interface IGameOptions {
    KeyBinding getKeyOptionScreen();
    RenderTweaksGameOptions getRenderTweaksGameOptions();

    void setGamma(double gamma);
    double getGamma();
}
