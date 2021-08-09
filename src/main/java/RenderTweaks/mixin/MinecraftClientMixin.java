package RenderTweaks.mixin;

import RenderTweaks.GUI.screen.RenderOptionScreen;
import RenderTweaks.IGameOptions;
import RenderTweaks.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    public boolean optionScreen;

    @Final
    public GameOptions options;

    @Shadow
    public ClientPlayerEntity player;

    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void handleKeybinds(CallbackInfo ci) {
        while (((IGameOptions)options).getKeyOptionScreen().wasPressed()) {
            optionScreen = !optionScreen;
            Screen op = new RenderOptionScreen();
            op.renderBackgroundTexture(0);
            setScreen(op);
        }
    }

    public boolean isOptionScreenOpen() {
        return optionScreen;
    }
}
