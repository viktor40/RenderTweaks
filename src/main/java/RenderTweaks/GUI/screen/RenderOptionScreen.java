package RenderTweaks.GUI.screen;

import RenderTweaks.option.RenderTweakGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class RenderOptionScreen extends Screen {
    private final Option[] OPTIONS = new Option[]{};
    public RenderTweakGameOptions renderTweakGameOptions;

    public RenderOptionScreen(RenderTweakGameOptions renderTweakGameOptions) {
        super(new TranslatableText("Render Tweaks Options"));
        this.renderTweakGameOptions = renderTweakGameOptions;
    }

    protected void init() {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 - 6, 150, 20, new TranslatableText("Environment"),
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(new RenderEnvironmentOptionScreen(this, this.client.options));
                    }
                }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 - 6, 150, 20, new TranslatableText("Particles"),
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(new RenderParticleOptionScreen(this, this.client.options));
                    }
                }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 24 - 6, 150, 20, new TranslatableText("Fun"),
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(new RenderFunOptionScreen(this, this.client.options));
                    }
                }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 24 - 6, 150, 20, new TranslatableText("Other"),
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(new RenderOtherOptionScreen(this, this.client.options));
                    }
                }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, new TranslatableText("Keybinds"),
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(new RenderKeyBindingScreen(this, this.client.options, this.renderTweakGameOptions));
                    }
                }));

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE,
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen((Screen)null);
                    }
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
