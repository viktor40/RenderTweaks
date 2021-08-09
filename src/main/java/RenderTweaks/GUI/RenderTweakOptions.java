package RenderTweaks.GUI;

import RenderTweaks.IGameOptions;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;

public abstract class RenderTweakOptions extends Option {
    public static final CyclingOption<Boolean> RAIN;

    public RenderTweakOptions(String key) {
        super(key);
    }

    static {
        RAIN = CyclingOption.create("Enable Weather",
                gameOptions -> ((IGameOptions)gameOptions).isWeatherEnabled(),
                (gameOptions, option, enableWeather) -> {((IGameOptions)gameOptions).setEnableWeather(enableWeather);}
        );
    }

}
