package RenderTweaks.mixin;

import RenderTweaks.interfaces.IGameOptions;
import RenderTweaks.interfaces.IMinecraftClient;
import RenderTweaks.option.RenderTweaksGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin implements IGameOptions {
    @Shadow @Final @Mutable public KeyBinding[] keysAll;
    @Shadow public double gamma;
    @Shadow protected MinecraftClient client;
    public GameOptions thisGameOptions = (GameOptions)(Object)this;
    public RenderTweaksGameOptions renderTweaksGameOptions;
    public KeyBinding keyOptionScreen;

    @Inject(method = "load", at = @At(value = "HEAD"))
    private void onLoadInjectAtHead(CallbackInfo ci) {

    }

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;syncChunkWrites:Z"))
    private void onGameOptionsInitInjectAtTail(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        Option.RENDER_DISTANCE.setMax(64);
        this.renderTweaksGameOptions = new RenderTweaksGameOptions(this.client, new File(this.client.runDirectory, "RenderTweakOptions.txt"));
        ((IMinecraftClient)(this.client)).setRenderTweakGameOptions(this.renderTweaksGameOptions);
        this.renderTweaksGameOptions.loadConfigs();
        this.renderTweaksGameOptions.assignOptions();
        this.keyOptionScreen = new KeyBinding("Open Option Screen", GLFW.GLFW_KEY_O, "RenderTweaks");
        this.keysAll = ArrayUtils.add(keysAll, keyOptionScreen);
    }

    @Override
    public RenderTweaksGameOptions getRenderTweaksGameOptions() {
        return this.renderTweaksGameOptions;
    }

    @Override
    public KeyBinding getKeyOptionScreen() {
        return keyOptionScreen;
    }

    @Override
    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public double getGamma() {
        return this.gamma;
    }
}
