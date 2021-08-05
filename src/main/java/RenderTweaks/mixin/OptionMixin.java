package RenderTweaks.mixin;

import net.minecraft.client.option.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Option.class)
public class OptionMixin {
    @ModifyArg(
            method = "<clinit>",
            index = 2,
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=options.gamma"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/DoubleOption;<init>(Ljava/lang/String;DDFLjava/util/function/Function;Ljava/util/function/BiConsumer;Ljava/util/function/BiFunction;)V",
                    ordinal = 0
            )
    )
    private static double increaseMaxGamma(double original) {
        return 16.0D;
    }
}
