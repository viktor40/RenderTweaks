package RenderTweaks.mixin;

import RenderTweaks.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
    private void cancelRainSplashing(Camera camera, CallbackInfo ci) {
        GameOptions options = MinecraftClient.getInstance().options;
        if (!((IGameOptions)options).isWeatherEnabled()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void cancelRenderWeather(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo ci) {
        GameOptions options = MinecraftClient.getInstance().options;
        if (!((IGameOptions)options).isWeatherEnabled()) {
            ci.cancel();
        }
    }
}
