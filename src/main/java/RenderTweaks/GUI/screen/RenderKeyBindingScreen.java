package RenderTweaks.GUI.screen;

import RenderTweaks.option.RenderTweakOptions;
import RenderTweaks.GUI.widget.AdvancedControlListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class RenderKeyBindingScreen extends GameOptionsScreen {
    public KeyBinding focusedBinding;
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
