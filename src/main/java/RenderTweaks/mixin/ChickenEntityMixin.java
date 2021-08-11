package RenderTweaks.mixin;

import RenderTweaks.interfaces.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity {
    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("RETURN"))
    private void derpyChickenHead(CallbackInfo ci) {
        GameOptions options = MinecraftClient.getInstance().options;
        if (((IGameOptions)options).isDerpyChicken()) {
            this.setRotation(0,-90F);
        }
    }
}
