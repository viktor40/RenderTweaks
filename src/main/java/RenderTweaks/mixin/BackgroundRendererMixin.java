package RenderTweaks.mixin;

import RenderTweaks.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void disableFog(Camera camera, FogType fogType, float viewDistance, boolean thickFog, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!((IMinecraftClient)client).renderFog()) {
            ci.cancel(); // this injects a return statement
        }
    }
}
