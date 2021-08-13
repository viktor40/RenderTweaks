package RenderTweaks.option;

import RenderTweaks.interfaces.IGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;

@Environment(EnvType.CLIENT)
public abstract class RenderTweaksOptions extends Option {
    public static final CyclingOption<Boolean> WEATHER;
    public static final CyclingOption<Boolean> DERPY_CHICKEN;
    public static final CyclingOption<Boolean> PARTICLES;
    public static final CyclingOption<Boolean> PARTICLES_BLOCK_BREAKING;
    public static final CyclingOption<Boolean> FOG;
    public RenderTweaksOptions(String key) {
        super(key);
    }

    public static final DoubleOption GAMMA_OVERRIDE = new DoubleOption("Gamma Override", 0.0D, 16.0D, 1.0F,
            (gameOptions) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride(),
            (gameOptions, gamma) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setGammaOverride(gamma),
            (gameOptions, option) -> {
                double d = ((IGameOptions)gameOptions).getRenderTweaksGameOptions().gammaOverride();
                if (d == 0.0D) {
                    ((IGameOptions)gameOptions).setGamma(((IGameOptions)gameOptions).getRenderTweaksGameOptions().getPrevGamma());
                    return option.getGenericLabel(new LiteralText("Disabled"));
                } else {
                    ((IGameOptions)gameOptions).setGamma(d);
                    if (d == 16.0D) {
                        return option.getGenericLabel(new LiteralText("Fullbright"));
                    } else {
                        return option.getGenericLabel(new LiteralText((int)(d * 100) + "%"));
                    }
                }
            });

    static {
        WEATHER = CyclingOption.create("Weather",
                gameOptions -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().isWeatherEnabled(),
                (gameOptions, option, enableWeather) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setWeather(enableWeather)
        );

        DERPY_CHICKEN = CyclingOption.create("Derpy Chicken",
                gameOptions -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().isDerpyChicken(),
                (gameOptions, option, isDerpyChicken) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setDerpyChicken(isDerpyChicken)
        );

        PARTICLES = CyclingOption.create("Particles",
                gameOptions -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().isParticlesEnabled(),
                (gameOptions, option, isParticles) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setParticlesEnabled(isParticles)
        );

        PARTICLES_BLOCK_BREAKING = CyclingOption.create("Block Breaking Particles",
                gameOptions -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().isParticlesBlockBreakingEnabled(),
                (gameOptions, option, isParticlesBlockBreaking) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setParticlesBlockBreaking(isParticlesBlockBreaking)
        );

        FOG = CyclingOption.create("Fog",
                gameOptions -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().isFogEnabled(),
                (gameOptions, option, isFog) -> ((IGameOptions)gameOptions).getRenderTweaksGameOptions().setFog(isFog)
        );
    }

}
