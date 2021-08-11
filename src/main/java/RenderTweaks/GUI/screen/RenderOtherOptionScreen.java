package RenderTweaks.GUI.screen;

import RenderTweaks.interfaces.IGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class RenderOtherOptionScreen extends GameOptionsScreen {
    private final Option[] OPTIONS = new Option[]{};
    private ButtonListWidget list;

    public RenderOtherOptionScreen(Screen parent, GameOptions options) {
        super(parent, options, new TranslatableText("Enviroment Options"));
    }

    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(OPTIONS);
        this.addSelectableChild(this.list);
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.BACK,
                (button) -> {
                    if (this.client != null) {
                        this.client.setScreen(this.parent);
                        ((IGameOptions)(this.client.options)).storeOptionChanges();
                        ((IGameOptions)(this.client.options)).getConfig().writeConfigs();
                    }
                }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void onClose() {
        super.onClose();
        if (this.client != null) {
            ((IGameOptions) (this.client.options)).storeOptionChanges();
            ((IGameOptions) (this.client.options)).getConfig().writeConfigs();
        }
    }
}
