package RenderTweaks.interfaces;

import RenderTweaks.option.RenderTweaksGameOptions;

public interface IMinecraftClient {
    boolean isOptionScreenOpen();
    void setRenderTweakGameOptions(RenderTweaksGameOptions renderTweaksGameOptions);
}
