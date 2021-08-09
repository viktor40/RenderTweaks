package RenderTweaks.GUI;

import RenderTweaks.IGameOptions;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;

public abstract class RenderTweakOptions extends Option {
    public static final CyclingOption<Boolean> RAIN;
    public static final CyclingOption<Boolean> DERPY_CHICKEN;

    public RenderTweakOptions(String key) {
        super(key);
    }

    static {
        RAIN = CyclingOption.create("RenderTweaks.option.weather",
                gameOptions -> ((IGameOptions)gameOptions).isWeatherEnabled(),
                (gameOptions, option, enableWeather) -> {((IGameOptions)gameOptions).setWeather(enableWeather);}
        );

        DERPY_CHICKEN = CyclingOption.create("RenderTweaks.option.derpy_chicken",
                gameOptions -> ((IGameOptions)gameOptions).isDerpyChicken(),
                (gameOptions, option, isDerpyChicken) -> {((IGameOptions)gameOptions).setDerpyChicken(isDerpyChicken);}
        );
    }

}
