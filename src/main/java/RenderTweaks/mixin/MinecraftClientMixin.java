package RenderTweaks.mixin;

import RenderTweaks.GUI.RenderTweaksOptionScreen;
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
    public boolean renderWeather;
    public boolean renderBreakingParticles;
    public boolean renderParticles;
    public boolean renderFog;
    public boolean fullBright;
    public boolean derpyChicken;
    public boolean optionScreen;

    @Final
    public GameOptions options;

    @Shadow
    public ClientPlayerEntity player;

    @Shadow public abstract void setScreen(@Nullable Screen screen);

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void handleKeybinds(CallbackInfo ci) {
        // Enable Derpy Chicken
        while (((IGameOptions)options).getKeyOptionScreen().wasPressed()) {
            optionScreen = !optionScreen;
            Screen op = new RenderTweaksOptionScreen();
            op.renderBackgroundTexture(0);
            setScreen(op);
        }
    }

    @Override
    public boolean renderWeather() {
        return renderWeather;
    }

    @Override
    public boolean renderBreakingParticles() {
        return renderBreakingParticles;
    }

    @Override
    public boolean renderParticles() {
        return renderParticles;
    }

    @Override
    public boolean renderFog() {
        return renderFog;
    }

    @Override
    public boolean derpyChicken() {
        return derpyChicken;
    }

    @Override
    public boolean optionScreen() {
        return optionScreen;
    }
}
