package RenderTweaks.mixin;

import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.text.Text;
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
                    target = "Lnet/minecraft/client/option/DoubleOption;<init>(Ljava/lang/String;DDFLjava/util/function/Function;Ljava/util/function/BiConsumer;Ljava/util/function/BiFunction;)V",
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
                    target = "Lnet/minecraft/client/option/DoubleOption;<init>(Ljava/lang/String;DDFLjava/util/function/Function;Ljava/util/function/BiConsumer;Ljava/util/function/BiFunction;)V",
                    ordinal = 0
            )
    )
    private static BiFunction<GameOptions, DoubleOption, Text> addFullbrightOptionText(BiFunction<GameOptions, DoubleOption, Text> original) {
        return (gameOptions, option) -> {
            double d = option.getRatio(option.get(gameOptions));
            if (d == 0.0D) {
                return option.getGenericLabel(new TranslatableText("options.gamma.min"));
            } else if (d > (1.0D / MAX_GAMMA_MULTIPLIER - 0.002) && d < (1.0D / MAX_GAMMA_MULTIPLIER + 0.002) ) {
                return option.getGenericLabel(new TranslatableText("options.gamma.max"));
            } else if (d == 1.0) {
                return option.getGenericLabel(new LiteralText("Fullbright"));
            } else {
                return option.getPercentAdditionLabel((int)(d * MAX_GAMMA_MULTIPLIER * 100));
            }
        };
    }
}
