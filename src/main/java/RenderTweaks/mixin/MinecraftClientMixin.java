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
    public boolean renderParticles;
    public boolean renderFog;
    public boolean fullBright;
    public boolean derpyChicken;

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

        // render particles keybind
        while (((IGameOptions)options).getKeyRenderParticles().wasPressed()) {
            renderParticles = !renderParticles;
            String particlesToggled = "Toggled particle rendering: ";
            particlesToggled = particlesToggled + ((renderParticles ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(particlesToggled), true);
        }

        // render overworld fog
        while (((IGameOptions)options).getKeyRenderFog().wasPressed()) {
            renderFog = !renderFog;
            String fogToggled = "Toggled fog rendering: ";
            fogToggled = fogToggled + ((renderFog ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(fogToggled), true);
        }

        // set fullbright
        while (((IGameOptions)options).getKeyFullBright().wasPressed()) {
            fullBright = !fullBright;
            if (fullBright) {
                ((IGameOptions)options).setPrevGamma(((IGameOptions)options).getGamma());
                ((IGameOptions)options).setGamma(16);
            } else {
                ((IGameOptions)options).setGamma(((IGameOptions)options).getPrevGamma());
            }
            String fullBrightToggled = "Toggled fullbright: ";
            fullBrightToggled = fullBrightToggled + ((fullBright ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(fullBrightToggled), true);
        }

        // Enable Derpy Chicken
        while (((IGameOptions)options).getKeyDerpyChicken().wasPressed()) {
            derpyChicken = !derpyChicken;
            String derpyChickenToggled = "Toggled Derpy Chicken: ";
            derpyChickenToggled = derpyChickenToggled + ((fullBright ? "ON" : "OFF"));
            player.sendMessage(new LiteralText(derpyChickenToggled), true);
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
    public boolean fullBright() {
        return fullBright;
    }

    @Override
    public boolean derpyChicken() {
        return derpyChicken;
    }
}
