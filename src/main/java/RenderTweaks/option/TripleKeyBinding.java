package RenderTweaks.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TripleKeyBinding {
    public KeyBinding keyBinding1;
    public KeyBinding keyBinding2;
    public KeyBinding keyBinding3;
    public String translationKey;
    public String category;
    public boolean keyBinding1Pressed;
    public boolean keyBinding2Pressed;
    public boolean keyBinding3Pressed;
    public boolean wasPressed;

    public TripleKeyBinding(String translationKey, int codeKey1, String category) {
        this.translationKey = translationKey;
        this.category = category;
        this.keyBinding1 = new KeyBinding(translationKey, codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey, GLFW.GLFW_KEY_UNKNOWN, category);
        this.keyBinding3 = new KeyBinding(translationKey, GLFW.GLFW_KEY_UNKNOWN, category);
    }
    public TripleKeyBinding(String translationKey, int codeKey1, int codeKey2, String category) {
        this.translationKey = translationKey;
        this.category = category;
        this.keyBinding1 = new KeyBinding(translationKey, codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey, codeKey2, category);
        this.keyBinding3 = new KeyBinding(translationKey, GLFW.GLFW_KEY_UNKNOWN, category);
    }
    public TripleKeyBinding(String translationKey, int codeKey1, int codeKey2, int codeKey3, String category) {
        this.translationKey = translationKey;
        this.category = category;
        this.keyBinding1 = new KeyBinding(translationKey, codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey, codeKey2, category);
        this.keyBinding3 = new KeyBinding(translationKey, codeKey3, category);
    }

    public boolean wasTripleKeyBindingPressed() {
        if (this.keyBinding1.isUnbound() && this.keyBinding2.isUnbound() && this.keyBinding3.isUnbound()) {
            this.keyBinding1Pressed = false;
            this.keyBinding2Pressed = false;
            this.keyBinding3Pressed = false;
            return false;
        }

        this.keyBinding1Pressed = this.keyBinding1.isUnbound() || this.keyBinding1.wasPressed();
        this.keyBinding2Pressed = this.keyBinding2.isUnbound() || this.keyBinding2.wasPressed();
        this.keyBinding3Pressed = this.keyBinding3.isUnbound() || this.keyBinding3.wasPressed();
        this.wasPressed = (this.keyBinding1Pressed && this.keyBinding2Pressed && this.keyBinding3Pressed);
        return this.wasPressed;
    }

    public KeyBinding getKeyBinding1() {
        return this.keyBinding1;
    }

    public KeyBinding getKeyBinding2() {
        return this.keyBinding2;
    }

    public KeyBinding getKeyBinding3() {
        return this.keyBinding3;
    }

    public String getCategory() {
        return this.category;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public boolean areKeyBindingsEqual() {
        return false;
    }
}
