package RenderTweaks.mixin;

import RenderTweaks.interfaces.IGameOptions;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mixin(Option.class)
public class OptionMixin {
    @ModifyArg(
            method = "<clinit>",
            index = 4,
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
    private static Function<GameOptions, Double> lockIfFullBrightEnabledGetter(Function<GameOptions, Double> original) {
        return (gameOptions) -> {
            if (((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride() == 0.0D) {
                return gameOptions.gamma;
            } else {
                return ((IGameOptions)gameOptions).getRenderTweaksGameOptions().getPrevGamma();
            }
        };
    }

    @ModifyArg(
            method = "<clinit>",
            index = 5,
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
    private static BiConsumer<GameOptions, Double> lockIfFullBrightEnabledSetter(BiConsumer<GameOptions, Double> original) {
        return (gameOptions, gamma) -> {
            if (((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride() == 0.0D) {
                gameOptions.gamma = gamma;
                ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setPrevGamma(gamma);
            } else {
                gameOptions.gamma = ((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride();
            }
        };
    }

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
    private static BiFunction<GameOptions, DoubleOption, Text> lockIfFullBrightEnabledDisplayStringSetter(BiFunction<GameOptions, DoubleOption, Text> original) {
        return (gameOptions, option) -> {
            if (((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride() == 0.0D) {
                double d = option.getRatio(option.get(gameOptions));
                if (d == 0.0D) {
                    return option.getGenericLabel(new TranslatableText("options.gamma.min"));
                } else {
                    return d == 1.0D ? option.getGenericLabel(new TranslatableText("options.gamma.max")) : option.getPercentAdditionLabel((int)(d * 100.0D));
                }
            } else {
                return option.getGenericLabel(new LiteralText("LOCKED"));
            }
        };
    }
}
