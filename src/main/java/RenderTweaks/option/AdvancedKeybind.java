package RenderTweaks.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AdvancedKeybind implements Comparable<KeyBinding> {

    @Override
    public int compareTo(@NotNull KeyBinding keyBinding) {
        return 0;
    }
}
