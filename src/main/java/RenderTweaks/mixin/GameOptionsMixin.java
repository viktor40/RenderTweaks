package RenderTweaks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Inject(
            method = "<init>",
            at = @At(value = "RETURN")
    )
    private void onGameOptionsInitInjectAtTail(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        System.out.println("!!!!! onGameOptionsInitInjectAtTail in GameOptionsMixin LOADED !!!!!");
        Option.RENDER_DISTANCE.setMax(64);

    }
}
