package RenderTweaks.option;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.glfw.GLFW;

public class RenderTweaksGameOptions {
    public final TripleKeyBinding keyFog;
    public final TripleKeyBinding keyWeather;
    public final TripleKeyBinding keyParticles;
    public final TripleKeyBinding keyParticlesBlockBreaking;
    public final TripleKeyBinding keyDerpyChicken;
    public TripleKeyBinding[] keysRender;
    public MinecraftClient client;

    public RenderTweaksGameOptions(MinecraftClient client) {
        this.client = client;
        this.keyFog = new TripleKeyBinding("Toggle Fog", GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_G, "Environment");
        this.keyWeather = new TripleKeyBinding("Toggle Weather", GLFW.GLFW_KEY_UNKNOWN, "Environment");
        this.keyParticles = new TripleKeyBinding("Disable Particles", GLFW.GLFW_KEY_P, "Particles");
        this.keyParticlesBlockBreaking = new TripleKeyBinding("Disable Block Breaking Particles", GLFW.GLFW_KEY_UNKNOWN, "Particles");
        this.keyDerpyChicken = new TripleKeyBinding("Derpy Chicken", GLFW.GLFW_KEY_UNKNOWN, "Fun");
        this.keysRender = (TripleKeyBinding[])ArrayUtils.addAll((Object[])(new TripleKeyBinding[]{this.keyFog, this.keyWeather, this.keyParticles, this.keyParticlesBlockBreaking, this.keyDerpyChicken}));

    }

    public void setKeyCode(KeyBinding key, InputUtil.Key code) {
        key.setBoundKey(code);
        this.writeKeyBindings();
    }

    // TODO: Create method to write non default key bindings to a file
    public void writeKeyBindings() {

    }
}

