package RenderTweaks.GUI.screen;

import RenderTweaks.GUI.widget.TripleControlListWidget;
import RenderTweaks.option.RenderTweaksGameOptions;
import RenderTweaks.option.TripleKeyBinding;
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
    public RenderTweaksGameOptions renderTweaksGameOptions;
    private TripleControlListWidget keyBindingListWidget;
    private ButtonWidget resetButton;

    public RenderKeyBindingScreen(Screen parent, GameOptions options, RenderTweaksGameOptions renderTweaksGameOptions) {
        super(parent, options, new TranslatableText("Render Tweak Options"));
        this.renderTweaksGameOptions = renderTweaksGameOptions;
    }

    protected void init() {
        this.keyBindingListWidget = new TripleControlListWidget(this, this.client, this.renderTweaksGameOptions);
        this.addSelectableChild(this.keyBindingListWidget);
        this.resetButton = this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, new TranslatableText("controls.resetAll"), (button) -> {

            for (TripleKeyBinding keyBinding : this.renderTweaksGameOptions.keysRender) {
                keyBinding.getKeyBinding1().setBoundKey(keyBinding.getKeyBinding1().getDefaultKey());
                keyBinding.getKeyBinding2().setBoundKey(keyBinding.getKeyBinding3().getDefaultKey());
                keyBinding.getKeyBinding2().setBoundKey(keyBinding.getKeyBinding3().getDefaultKey());
            }
            KeyBinding.updateKeysByCode();
        }));

        if (this.client != null) {
            this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 29, 150, 20, ScreenTexts.DONE, (button) -> this.client.setScreen(this.parent)));
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.focusedBinding1 != null) {
            this.renderTweaksGameOptions.setKeyCode(this.focusedBinding1, InputUtil.Type.MOUSE.createFromCode(button));
            this.focusedBinding1 = null;
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding2 != null) {
            this.renderTweaksGameOptions.setKeyCode(this.focusedBinding2, InputUtil.Type.MOUSE.createFromCode(button));
            this.focusedBinding2 = null;
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding3 != null) {
            this.renderTweaksGameOptions.setKeyCode(this.focusedBinding3, InputUtil.Type.MOUSE.createFromCode(button));
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
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding1, InputUtil.UNKNOWN_KEY);
            } else {
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding1, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            this.focusedBinding1 = null;
            this.time = Util.getMeasuringTimeMs();
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding2 != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding2, InputUtil.UNKNOWN_KEY);
            } else {
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding2, InputUtil.fromKeyCode(keyCode, scanCode));
            }

            this.focusedBinding2 = null;
            this.time = Util.getMeasuringTimeMs();
            KeyBinding.updateKeysByCode();
            return true;
        } else if (this.focusedBinding3 != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding3, InputUtil.UNKNOWN_KEY);
            } else {
                this.renderTweaksGameOptions.setKeyCode(this.focusedBinding3, InputUtil.fromKeyCode(keyCode, scanCode));
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
        boolean isDefaultKey = false;

        // TODO: fix whatever this does
        for (TripleKeyBinding keyBinding : this.renderTweaksGameOptions.keysRender) {
            if (!keyBinding.isDefault()) {
                isDefaultKey = true;
                break;
            }
        }

        this.resetButton.active = isDefaultKey;

        super.render(matrices, mouseX, mouseY, delta);
    }
}
