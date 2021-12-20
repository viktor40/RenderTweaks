package RenderTweaks.option;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TripleKeyBinding {
    public KeyBinding keyBinding1;
    public KeyBinding keyBinding2;
    public KeyBinding keyBinding3;
    private InputUtil.Key defaultKey1;
    private InputUtil.Key defaultKey2;
    private InputUtil.Key defaultKey3;
    private InputUtil.Key key1;
    private InputUtil.Key key2;
    private InputUtil.Key key3;
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
        this.keyBinding1 = new KeyBinding(translationKey + ".1", codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey + ".2", GLFW.GLFW_KEY_UNKNOWN, category);
        this.keyBinding3 = new KeyBinding(translationKey + ".3", GLFW.GLFW_KEY_UNKNOWN, category);
        this.defaultKey1 =  keyBinding1.getDefaultKey();
        this.defaultKey2 =  keyBinding1.getDefaultKey();
        this.defaultKey3 =  keyBinding1.getDefaultKey();
    }

    public TripleKeyBinding(String translationKey, int codeKey1, int codeKey2, String category) {
        this.translationKey = translationKey;
        this.category = category;
        this.keyBinding1 = new KeyBinding(translationKey + ".1", codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey + ".2", codeKey2, category);
        this.keyBinding3 = new KeyBinding(translationKey + ".3", GLFW.GLFW_KEY_UNKNOWN, category);
        this.defaultKey1 =  keyBinding1.getDefaultKey();
        this.defaultKey2 =  keyBinding1.getDefaultKey();
        this.defaultKey3 =  keyBinding1.getDefaultKey();
    }

    public TripleKeyBinding(String translationKey, int codeKey1, int codeKey2, int codeKey3, String category) {
        this.translationKey = translationKey;
        this.category = category;
        this.keyBinding1 = new KeyBinding(translationKey, codeKey1, category);
        this.keyBinding2 = new KeyBinding(translationKey, codeKey2, category);
        this.keyBinding3 = new KeyBinding(translationKey, codeKey3, category);
        this.defaultKey1 =  keyBinding1.getDefaultKey();
        this.defaultKey2 =  keyBinding1.getDefaultKey();
        this.defaultKey3 =  keyBinding1.getDefaultKey();
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

        if (this.keyBinding1.isPressed() && !this.keyBinding1.isUnbound()) {
            System.out.println("key 1 pressed");
        }

        if (this.keyBinding2.isPressed() && !this.keyBinding2.isUnbound()) {
            System.out.println("key 2 pressed");
        }

        if (this.keyBinding3.isPressed() && !this.keyBinding3.isUnbound()) {
            System.out.println("key 3 pressed");
        }

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

    public InputUtil.Key getKey1() {
        return key1;
    }

    public InputUtil.Key getKey2() {
        return key2;
    }

    public InputUtil.Key getKey3() {
        return key3;
    }

    public InputUtil.Key getDefaultKey1() {
        return defaultKey1;
    }

    public InputUtil.Key getDefaultKey2() {
        return defaultKey2;
    }

    public InputUtil.Key getDefaultKey3() {
        return defaultKey3;
    }

    public boolean isDefault() {
        return ((key1 == defaultKey1) && (key2 == defaultKey2) && (key3 == defaultKey3));
    }

    public String getCategory() {
        return this.category;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    // TODO: create method to check if two key bindings are the same
    public boolean areKeyBindingsEqual() {
        return false;
    }
}
