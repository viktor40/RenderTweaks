package RenderTweaks.mixin;

import RenderTweaks.GUI.screen.RenderOptionScreen;
import RenderTweaks.interfaces.IGameOptions;
import RenderTweaks.interfaces.IMinecraftClient;
import RenderTweaks.option.RenderTweaksGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    @Final @Shadow public GameOptions options;
    public RenderTweaksGameOptions renderTweaksGameOptions;
    @Shadow public ClientPlayerEntity player;
    @Shadow public abstract void setScreen(@Nullable Screen screen);
    public boolean optionScreen;
    public boolean keyFog;

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void handleKeyBindings(CallbackInfo ci) {
        while (((IGameOptions)options).getKeyOptionScreen().wasPressed()) {
            optionScreen = !optionScreen;
            Screen op = new RenderOptionScreen(renderTweaksGameOptions);
            op.renderBackgroundTexture(0);
            setScreen(op);
        }

        if (renderTweaksGameOptions.keyFog.isTripleKeyBindingPressed()) {
            keyFog = !keyFog;
            String fogToggled = "Toggled fog rendering: ";
            fogToggled = fogToggled + ((keyFog ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(fogToggled), true);
        }
    }

    @Override
    public boolean isOptionScreenOpen() {
        return optionScreen;
    }

    @Override
    public void setRenderTweakGameOptions(RenderTweaksGameOptions renderTweaksGameOptions) {
        this.renderTweaksGameOptions = renderTweaksGameOptions;
    }
}
