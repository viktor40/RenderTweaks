package RenderTweaks.GUI.screen;

import RenderTweaks.GUI.widget.AdvancedControlListWidget;
import RenderTweaks.option.RenderTweakOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class RenderKeyBindingScreen extends GameOptionsScreen {
    public KeyBinding focusedBinding1;
    public KeyBinding focusedBinding2;
    public KeyBinding focusedBinding3;
    public long time;
    private AdvancedControlListWidget keyBindingListWidget;
    private ButtonWidget resetButton;

    public RenderKeyBindingScreen(Screen parent, GameOptions options) {
        super(parent, options, new TranslatableText("Render Tweak Options"));
    }

    protected void init() {
        this.keyBindingListWidget = new AdvancedControlListWidget(this, this.client);
        this.addSelectableChild(this.keyBindingListWidget);
        this.resetButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableText("controls.resetAll"), (button) -> {

            for (KeyBinding keyBinding : RenderTweakOptions.keysRender) {
                keyBinding.setBoundKey(keyBinding.getDefaultKey());
            }
            KeyBinding.updateKeysByCode();
        }));

        if (this.client != null) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, ScreenTexts.DONE, (button) -> this.client.setScreen(this.parent)));
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.focusedBinding1 != null) {
            this.gameOptions.setKeyCode(this.focusedBinding1, InputUtil.Type.MOUSE.createFromCode(button));
            this.focusedBinding1 = null;
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding2 != null) {
            this.gameOptions.setKeyCode(this.focusedBinding2, InputUtil.Type.MOUSE.createFromCode(button));
            this.focusedBinding2 = null;
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding3 != null) {
            this.gameOptions.setKeyCode(this.focusedBinding3, InputUtil.Type.MOUSE.createFromCode(button));
            this.focusedBinding3 = null;
            KeyBinding.updateKeysByCode();
            return true;
        } else {
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.focusedBinding1 != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.gameOptions.setKeyCode(this.focusedBinding1, InputUtil.UNKNOWN_KEY);
            } else {
                this.gameOptions.setKeyCode(this.focusedBinding1, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            this.focusedBinding1 = null;
            this.time = Util.getMeasuringTimeMs();
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding2 != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.gameOptions.setKeyCode(this.focusedBinding2, InputUtil.UNKNOWN_KEY);
            } else {
                this.gameOptions.setKeyCode(this.focusedBinding2, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            this.focusedBinding2 = null;
            this.time = Util.getMeasuringTimeMs();
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding3 != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.gameOptions.setKeyCode(this.focusedBinding3, InputUtil.UNKNOWN_KEY);
            } else {
                this.gameOptions.setKeyCode(this.focusedBinding3, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            this.focusedBinding3 = null;
            this.time = Util.getMeasuringTimeMs();
            KeyBinding.updateKeysByCode();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.keyBindingListWidget.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);

        for (KeyBinding keyBinding : RenderTweakOptions.keysRender) {
            keyBinding.setBoundKey(keyBinding.getDefaultKey());
        }

        KeyBinding.updateKeysByCode();
        super.render(matrices, mouseX, mouseY, delta);
    }
}
