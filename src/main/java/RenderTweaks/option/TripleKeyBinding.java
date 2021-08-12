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
    private final String translationKey;
    private final String category;
    private int timesPressed;
    public static final String ENVIRONMENT_CATEGORY = "Environment";
    public static final String FUN_CATEGORY = "Fun";
    public static final String OTHER_CATEGORY = "Other";
    public static final String PARTICLES_CATEGORY = "Particles";

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

    public boolean isTripleKeyBindingPressed() {
        boolean keyBinding1Pressed = this.keyBinding1.isUnbound() || this.keyBinding1.isPressed();
        boolean keyBinding2Pressed = this.keyBinding2.isUnbound() || this.keyBinding2.isPressed();
        boolean keyBinding3Pressed = this.keyBinding3.isUnbound() || this.keyBinding3.isPressed();
        boolean isPressed = (
                keyBinding1Pressed
                        && keyBinding2Pressed
                        && keyBinding3Pressed
                        && !(this.keyBinding1.isUnbound() && this.keyBinding2.isUnbound() && this.keyBinding3.isUnbound())
        );

        if (this.timesPressed == 0 && isPressed) {
            this.timesPressed++;
            return true;
        } else if (isPressed) {
            this.timesPressed++;
            return false;
        } else {
            timesPressed = 0;
            return false;
        }
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

    // TODO: create method to get the current key for each keybind
    public int getKey1() {
        return 0;
    }

    public int getKey2() {
        return 0;
    }

    public int getKey3() {
        return 0;
    }

    // TODO: create method to get the default key for each keybind
    public int getDefaultKey1() {
        return 0;
    }

    public int getDefaultKey2() {
        return 0;
    }

    public int getDefaultKey3() {
        return 0;
    }

    public String getCategory() {
        return this.category;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    // TODO: Create isDefault method
    public boolean isDefault() {
        return false;
    }

    // TODO: create method to check if two key bindings are the same
    public boolean areKeyBindingsEqual() {
        return false;
    }
}
