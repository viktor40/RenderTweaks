package RenderTweaks.mixin;

import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.BiFunction;

@Mixin(Option.class)
public class OptionMixin {
    private static final double MAX_GAMMA_MULTIPLIER = 16.0D;

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
                    target = "Lnet/minecraft/client/options/DoubleOption;<init>(Ljava/lang/String;DDFLjava/util/function/Function;Ljava/util/function/BiConsumer;Ljava/util/function/BiFunction;)V",
                    ordinal = 0
            )
    )
    private static double increaseMaxGamma(double original) {
        return MAX_GAMMA_MULTIPLIER;
    }

    // TODO: make slider logarithmic
    @ModifyArg(
            method = "<clinit>",
            index = 6,
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=options.gamma"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/options/DoubleOption;<init>(Ljava/lang/String;DDFLjava/util/function/Function;Ljava/util/function/BiConsumer;Ljava/util/function/BiFunction;)V",
                    ordinal = 0
            )
    )
    private static BiFunction<GameOptions, DoubleOption, String> addFullbrightOptionText(BiFunction<GameOptions, DoubleOption, String> original) {
        return (gameOptions, option) -> {
            double d = option.getRatio(option.get(gameOptions));
            String string = option.getDisplayPrefix();
            if (d == 0.0D) {
                return string + I18n.translate("options.gamma.min", new Object[0]);
            } else if (d > (1.0D / MAX_GAMMA_MULTIPLIER - 0.002) && d < (1.0D / MAX_GAMMA_MULTIPLIER + 0.002) ) {
                return string + I18n.translate("options.gamma.max", new Object[0]);
            } else if (d == 1.0) {
                return string + "Fullbright";
            } else {
                return string + "+" + (int)(d * 100.0D * MAX_GAMMA_MULTIPLIER) + "%";
            }
        };
    }
}
