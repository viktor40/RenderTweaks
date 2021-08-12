package RenderTweaks.interfaces;

import RenderTweaks.option.RenderTweakGameOptions;

public interface IMinecraftClient {
    boolean isOptionScreenOpen();
    void setRenderTweakGameOptions(RenderTweakGameOptions renderTweakGameOptions);
}
