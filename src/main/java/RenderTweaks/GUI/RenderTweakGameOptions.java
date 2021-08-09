package RenderTweaks.GUI;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

import java.io.File;

public class RenderTweakGameOptions extends GameOptions {

    public RenderTweakGameOptions(MinecraftClient client, File optionsFile) {
        super(client, optionsFile);
    }
}

