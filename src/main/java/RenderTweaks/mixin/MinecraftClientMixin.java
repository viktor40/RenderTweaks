package RenderTweaks.mixin;

import RenderTweaks.IGameOptions;
import RenderTweaks.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.LiteralText;
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
    public boolean renderFog;

    @Final
    public GameOptions options;

    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "handleInputEvents", at = @At(value = "HEAD"))
    private void handleKeybinds(CallbackInfo ci) {
        // render weather keybind
        while (((IGameOptions)options).getKeyRenderWeather().wasPressed()) {
            renderWeather = !renderWeather;
            String weatherToggled = "Toggled weather rendering: ";
            weatherToggled = weatherToggled + ((renderWeather ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(weatherToggled), true);
        }

        // render block breaking particles keybind
        while (((IGameOptions)options).getKeyRenderBreakingParticles().wasPressed()) {
            renderBreakingParticles = !renderBreakingParticles;
            String breakingParticlesToggled = "Toggled block breaking particle rendering: ";
            breakingParticlesToggled = breakingParticlesToggled + ((renderBreakingParticles ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(breakingParticlesToggled), true);
        }

        // render overworld fog
        while (((IGameOptions)options).getKeyRenderFog().wasPressed()) {
            renderFog = !renderFog;
            String fogToggled = "Toggled fog rendering: ";
            fogToggled = fogToggled + ((renderFog ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(fogToggled), true);
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
    public boolean renderFog() {
        return renderFog;
    }
}
