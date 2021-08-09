package RenderTweaks.GUI;

import RenderTweaks.IGameOptions;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;

public abstract class RenderTweakOptions extends Option {
    public static final CyclingOption<Boolean> WEATHER;
    public static final CyclingOption<Boolean> DERPY_CHICKEN;
    public static final CyclingOption<Boolean> PARTICLES;
    public static final CyclingOption<Boolean> PARTICLES_BLOCK_BREAKING;
    public static final CyclingOption<Boolean> FOG;

    public RenderTweakOptions(String key) {
        super(key);
    }

    public static final DoubleOption GAMMA_OVERRIDE = new DoubleOption("Gamma Override", 0.0D, 16.0D, 1.0F,
            (gameOptions) -> ((IGameOptions)gameOptions).gammaOverride(),
            (gameOptions, gamma) -> {((IGameOptions)gameOptions).setGammaOverride(gamma);},
            (gameOptions, option) -> {
                double d = ((IGameOptions)gameOptions).gammaOverride();
                if (d == 0.0D) {
                    ((IGameOptions)gameOptions).setGamma(((IGameOptions)gameOptions).getPrevGamma());
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
                gameOptions -> ((IGameOptions)gameOptions).isWeatherEnabled(),
                (gameOptions, option, enableWeather) -> {((IGameOptions)gameOptions).setWeather(enableWeather);}
        );

        DERPY_CHICKEN = CyclingOption.create("Derpy Chicken",
                gameOptions -> ((IGameOptions)gameOptions).isDerpyChicken(),
                (gameOptions, option, isDerpyChicken) -> {((IGameOptions)gameOptions).setDerpyChicken(isDerpyChicken);}
        );

        PARTICLES = CyclingOption.create("Particles",
                gameOptions -> ((IGameOptions)gameOptions).isParticlesEnabled(),
                (gameOptions, option, isParticles) -> {((IGameOptions)gameOptions).setParticlesEnabled(isParticles);}
        );

        PARTICLES_BLOCK_BREAKING = CyclingOption.create("Particles",
                gameOptions -> ((IGameOptions)gameOptions).isParticlesEnabled(),
                (gameOptions, option, isParticles) -> {((IGameOptions)gameOptions).setParticlesEnabled(isParticles);}
        );

        FOG = CyclingOption.create("Particles",
                gameOptions -> ((IGameOptions)gameOptions).isParticlesEnabled(),
                (gameOptions, option, isParticles) -> {((IGameOptions)gameOptions).setParticlesEnabled(isParticles);}
        );
    }

}
