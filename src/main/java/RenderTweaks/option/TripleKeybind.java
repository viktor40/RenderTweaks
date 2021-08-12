package RenderTweaks.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class TripleKeybind extends KeyBinding {

    public TripleKeybind(String translationKey, int code, String category) {
        super(translationKey, code, category);
    }

    public TripleKeybind(String translationKey, InputUtil.Type type, int code, String category) {
        super(translationKey, type, code, category);
    }
}
