package RenderTweaks.GUI.widget;

import RenderTweaks.GUI.screen.RenderKeyBindingScreen;
import RenderTweaks.option.RenderTweaksGameOptions;
import RenderTweaks.option.TripleKeyBinding;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class TripleControlListWidget extends ElementListWidget<TripleControlListWidget.Entry> {
    public RenderTweaksGameOptions renderTweaksGameOptions;
    final RenderKeyBindingScreen parent;
    int maxKeyNameLength;


    public TripleControlListWidget(RenderKeyBindingScreen parent, MinecraftClient client, RenderTweaksGameOptions renderTweaksGameOptions) {
        super(client, parent.width + 45, parent.height, 43, parent.height - 32, 20);
        this.renderTweaksGameOptions = renderTweaksGameOptions;
        this.parent = parent;
        TripleKeyBinding[] tripleKeyBindings = (TripleKeyBinding[])ArrayUtils.clone((Object[]) this.renderTweaksGameOptions.keysRender);
        String string = null;

        for (TripleKeyBinding keyBinding : tripleKeyBindings) {
            String category = keyBinding.getCategory();
            if (!category.equals(string)) {
                string = category;
                this.addEntry(new TripleControlListWidget.CategoryEntry(new TranslatableText(category)));
            }

            Text text = new TranslatableText(keyBinding.getTranslationKey());
            int i = client.textRenderer.getWidth(text);
            if (i > this.maxKeyNameLength) {
                this.maxKeyNameLength = i;
            }

            this.addEntry(new TripleControlListWidget.KeyBindingEntry(keyBinding, text, this.renderTweaksGameOptions));
        }
    }

    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 15;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 32;
    }

    @Environment(EnvType.CLIENT)
    public class CategoryEntry extends TripleControlListWidget.Entry {
        final Text text;
        private final int textWidth;

        public CategoryEntry(Text text) {
            this.text = text;
            this.textWidth = TripleControlListWidget.this.client.textRenderer.getWidth(this.text);
        }

        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            TextRenderer var10000 = TripleControlListWidget.this.client.textRenderer;
            assert TripleControlListWidget.this.client.currentScreen != null;
            float var10003 = (float)(TripleControlListWidget.this.client.currentScreen.width / 2 - this.textWidth / 2);
            int var10004 = y + entryHeight;
            Objects.requireNonNull(TripleControlListWidget.this.client.textRenderer);
            var10000.draw(matrices, this.text, var10003, (float)(var10004 - 9 - 1), 16777215);
        }

        public boolean changeFocus(boolean lookForwards) {
            return false;
        }

        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(new Selectable() {
                public Selectable.SelectionType getType() {
                    return Selectable.SelectionType.HOVERED;
                }

                public void appendNarrations(NarrationMessageBuilder builder) {
                    builder.put(NarrationPart.TITLE, CategoryEntry.this.text);
                }
            });
        }
    }

    @Environment(EnvType.CLIENT)
    public class KeyBindingEntry extends TripleControlListWidget.Entry {
        private final TripleKeyBinding tripleBinding;
        private final KeyBinding binding1;
        private final KeyBinding binding2;
        private final KeyBinding binding3;
        private final Text bindingName;
        private final ButtonWidget key1Button;
        private final ButtonWidget key2Button;
        private final ButtonWidget key3Button;
        private final ButtonWidget resetButton;
        private final RenderTweaksGameOptions renderTweaksGameOptions;

        KeyBindingEntry(TripleKeyBinding binding, Text bindingName, RenderTweaksGameOptions renderTweaksGameOptions) {
            this.renderTweaksGameOptions = renderTweaksGameOptions;
            this.tripleBinding = binding;
            this.binding1 = binding.getKeyBinding1();
            this.binding2 = binding.getKeyBinding2();
            this.binding3 = binding.getKeyBinding3();
            this.bindingName = bindingName;
            this.key1Button = new ButtonWidget(0, 0, 75, 20, bindingName, (button) -> TripleControlListWidget.this.parent.focusedBinding1 = binding1) {
                protected MutableText getNarrationMessage() {
                    return binding1.isUnbound() ? new TranslatableText("narrator.controls.unbound", bindingName) : new TranslatableText("narrator.controls.bound", bindingName, super.getNarrationMessage());
                }
            };

            this.key2Button = new ButtonWidget(0, 0, 75, 20, bindingName, (button) -> TripleControlListWidget.this.parent.focusedBinding2 = binding2) {
                protected MutableText getNarrationMessage() {
                    return binding2.isUnbound() ? new TranslatableText("narrator.controls.unbound", bindingName) : new TranslatableText("narrator.controls.bound", bindingName, super.getNarrationMessage());
                }
            };

            this.key3Button = new ButtonWidget(0, 0, 75, 20, bindingName, (button) -> TripleControlListWidget.this.parent.focusedBinding3 = binding3) {
                protected MutableText getNarrationMessage() {
                    return binding3.isUnbound() ? new TranslatableText("narrator.controls.unbound", bindingName) : new TranslatableText("narrator.controls.bound", bindingName, super.getNarrationMessage());
                }
            };

            this.resetButton = new ButtonWidget(0, 0, 50, 20, new TranslatableText("controls.reset"), (button) -> {
                TripleControlListWidget.this.client.options.setKeyCode(binding1, binding1.getDefaultKey());
                TripleControlListWidget.this.client.options.setKeyCode(binding2, binding2.getDefaultKey());
                TripleControlListWidget.this.client.options.setKeyCode(binding3, binding3.getDefaultKey());
                // TODO: make sure the function below executes properly
                KeyBinding.updateKeysByCode();
            }) {
                protected MutableText getNarrationMessage() {
                    return new TranslatableText("narrator.controls.reset", bindingName);
                }
            };
        }

        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            boolean binding1Bound = TripleControlListWidget.this.parent.focusedBinding1 == this.binding1;
            boolean binding2Bound = TripleControlListWidget.this.parent.focusedBinding2 == this.binding2;
            boolean binding3Bound = TripleControlListWidget.this.parent.focusedBinding3 == this.binding3;
            TextRenderer optionName = TripleControlListWidget.this.client.textRenderer;
            float optionNameX = (float)(x - 75 / 2 - TripleControlListWidget.this.maxKeyNameLength);
            float optionNameY = (float)(y + entryHeight / 2 - 9 / 2);
            Objects.requireNonNull(TripleControlListWidget.this.client.textRenderer);
            optionName.draw(matrices, this.bindingName, optionNameX, optionNameY, 16777215);
            this.resetButton.x = x + 280 - 75 / 2;
            this.resetButton.y = y;
            this.resetButton.active = !this.binding1.isDefault();
            this.resetButton.render(matrices, mouseX, mouseY, tickDelta);

            this.key1Button.x = x + 25 - 75 / 2;
            this.key1Button.y = y;
            this.key1Button.setMessage(this.binding1.getBoundKeyLocalizedText());
            boolean bl2 = false;
            if (!this.binding1.isUnbound()) {
                TripleKeyBinding[] keyBinds = this.renderTweaksGameOptions.keysRender;
                for (TripleKeyBinding keyBinding : keyBinds) {
                    if (keyBinding.getKeyBinding1() != this.binding1 && this.binding1.equals(keyBinding.getKeyBinding1())) {
                        bl2 = true;
                        break;
                    }
                }
            }

            if (binding1Bound) {
                this.key1Button.setMessage((new LiteralText("> ")).append(this.key1Button.getMessage().shallowCopy().formatted(Formatting.YELLOW)).append(" <").formatted(Formatting.YELLOW));
            } else if (bl2) {
                // TODO: fix warning for double key bindings
                this.key1Button.setMessage(this.key1Button.getMessage().shallowCopy().formatted(Formatting.RED));
            }

            this.key1Button.render(matrices, mouseX, mouseY, tickDelta);

            this.key2Button.x = x + 110 - 75 / 2;
            this.key2Button.y = y;
            this.key2Button.setMessage(this.binding2.getBoundKeyLocalizedText());
            boolean bl3 = false;
            if (!this.binding2.isUnbound()) {
                TripleKeyBinding[] keyBinds = this.renderTweaksGameOptions.keysRender;
                for (TripleKeyBinding keyBinding : keyBinds) {
                    if (keyBinding.getKeyBinding2() != this.binding1 && this.binding1.equals(keyBinding.getKeyBinding2())) {
                        bl3 = true;
                        break;
                    }
                }
            }

            if (binding2Bound) {
                this.key2Button.setMessage((new LiteralText("> ")).append(this.key2Button.getMessage().shallowCopy().formatted(Formatting.YELLOW)).append(" <").formatted(Formatting.YELLOW));
            } else if (bl3) {
                // TODO: fix warning for double key bindings
                this.key2Button.setMessage(this.key2Button.getMessage().shallowCopy().formatted(Formatting.RED));
            }

            this.key2Button.render(matrices, mouseX, mouseY, tickDelta);

            this.key3Button.x = x + 195 - 75 / 2;
            this.key3Button.y = y;
            this.key3Button.setMessage(this.binding3.getBoundKeyLocalizedText());
            boolean bl4 = false;
            if (!this.binding3.isUnbound()) {
                TripleKeyBinding[] keyBinds = this.renderTweaksGameOptions.keysRender;
                for (TripleKeyBinding keyBinding : keyBinds) {
                    if (keyBinding.getKeyBinding2() != this.binding3 && this.binding3.equals(keyBinding.getKeyBinding2())) {
                        bl4 = true;
                        break;
                    }
                }
            }

            if (binding3Bound) {
                this.key3Button.setMessage((new LiteralText("> ")).append(this.key3Button.getMessage().shallowCopy().formatted(Formatting.YELLOW)).append(" <").formatted(Formatting.YELLOW));
            } else if (bl4) {
                // TODO: fix warning for double key bindings
                this.key3Button.setMessage(this.key3Button.getMessage().shallowCopy().formatted(Formatting.RED));
            }

            this.key3Button.render(matrices, mouseX, mouseY, tickDelta);
        }

        public List<? extends Element> children() {
            return ImmutableList.of(this.key1Button, this.key2Button, this.key3Button, this.resetButton);
        }

        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.key1Button, this.key2Button, this.key3Button, this.resetButton);
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.key1Button.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (this.key2Button.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else if (this.key3Button.mouseClicked(mouseX, mouseY, button)) {
                return true;
            } else {
                return this.resetButton.mouseClicked(mouseX, mouseY, button);
            }
        }

        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return this.key1Button.mouseReleased(mouseX, mouseY, button) || this.key2Button.mouseReleased(mouseX, mouseY, button) || this.key3Button.mouseReleased(mouseX, mouseY, button) || this.resetButton.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends net.minecraft.client.gui.widget.ElementListWidget.Entry<TripleControlListWidget.Entry> {
    }
}
