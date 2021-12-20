package RenderTweaks.option;

import RenderTweaks.interfaces.IGameOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

public class RenderTweaksGameOptions {
    public final TripleKeyBinding keyFog;
    public final TripleKeyBinding keyWeather;
    public final TripleKeyBinding keyParticles;
    public final TripleKeyBinding keyParticlesBlockBreaking;
    public final TripleKeyBinding keyDerpyChicken;
    public TripleKeyBinding[] keysRender;
    public MinecraftClient client;
    public GameOptions gameOptions;
    public LinkedHashMap<String, Boolean> booleanOptions = new LinkedHashMap<>();
    public LinkedHashMap<String, Double> doubleOptions = new LinkedHashMap<>();
    public RenderTweaksGameOptions renderTweaksGameOptions;
    static final Logger LOGGER = LogManager.getLogger();
    public File optionFile;

    public boolean enableWeather;
    public boolean fogEnabled;
    public boolean particlesEnabled;
    public boolean particlesBlockBreaking;
    public boolean derpyChicken;
    public double gammaOverride = 0.0D;
    public double prevGamma = 0.0D;

    public RenderTweaksGameOptions(MinecraftClient client, File f) {
        this.client = client;
        this.gameOptions = client.options;
        this.keyFog = new TripleKeyBinding("Toggle Fog", GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_G, TripleKeyBinding.ENVIRONMENT_CATEGORY);
        this.keyWeather = new TripleKeyBinding("Toggle Weather", GLFW.GLFW_KEY_UNKNOWN, TripleKeyBinding.ENVIRONMENT_CATEGORY);
        this.keyParticles = new TripleKeyBinding("Disable Particles", GLFW.GLFW_KEY_P, TripleKeyBinding.PARTICLES_CATEGORY);
        this.keyParticlesBlockBreaking = new TripleKeyBinding("Disable Block Breaking Particles", GLFW.GLFW_KEY_UNKNOWN, TripleKeyBinding.PARTICLES_CATEGORY);
        this.keyDerpyChicken = new TripleKeyBinding("Derpy Chicken", GLFW.GLFW_KEY_UNKNOWN, TripleKeyBinding.FUN_CATEGORY);
        this.keysRender = (TripleKeyBinding[])ArrayUtils.addAll((Object[])(new TripleKeyBinding[]{this.keyFog, this.keyWeather, this.keyParticles, this.keyParticlesBlockBreaking, this.keyDerpyChicken}));
        this.initOptionMaps();

        this.optionFile = f;
        if (!this.optionFile.exists()) {
            createNewFile();
        }
    }

    public void setKeyCode(KeyBinding key, InputUtil.Key code) {
        key.setBoundKey(code);
        this.writeKeyBindings();
    }

    private void initOptionMaps() {
        booleanOptions.put("environment.weather", true);
        booleanOptions.put("environment.fog", true);
        booleanOptions.put("particles.all", true);
        booleanOptions.put("particles.blockBreaking", true);
        booleanOptions.put("fun.derpyChicken", false);

        doubleOptions.put("environment.gammaOverride", 0.0D);
        doubleOptions.put("environment.prevGamma", 0.0D);
    }

    public void assignOptions() {
        try {
            this.enableWeather = booleanOptions.get("environment.weather");
            this.fogEnabled = booleanOptions.get("environment.fog");
            this.particlesEnabled = booleanOptions.get("particles.all");
            this.particlesBlockBreaking = booleanOptions.get("particles.blockBreaking");
            this.derpyChicken = booleanOptions.get("fun.derpyChicken");

            this.gammaOverride = doubleOptions.get("environment.gammaOverride");
            this.prevGamma = doubleOptions.get("environment.prevGamma");
        } catch (Exception e) {
            this.writeConfigs();
        }
    }

    public void storeOptionChanges() {
        booleanOptions.put("environment.weather", enableWeather);
        booleanOptions.put("environment.fog", fogEnabled);
        booleanOptions.put("particles.all", particlesEnabled);
        booleanOptions.put("particles.blockBreaking", particlesBlockBreaking);
        booleanOptions.put("fun.derpyChicken", derpyChicken);

        doubleOptions.put("environment.gammaOverride", gammaOverride);
        doubleOptions.put("environment.prevGamma", prevGamma);
    }


    // TODO: Create method to load from options file
    public void load() {

    }

    // TODO: Create method to write non default key bindings to a file
    public void write() {

    }

    // TODO: Create method to write updated options to the optionMaps
    public void writeOptionMaps() {

    }

    // TODO: Create method to assign updated keyBindings
    public void writeKeyBindings() {

    }

    // TODO: Write getters and setters for booleanOptions and doubleOptions
    public boolean getBooleanOption(String key) {
        return false;
    }

    public double getDoubleOption(String key) {
        return 0.0D;
    }

    public void setBooleanOption(String key) {

    }

    public void setDoubleOption(String key) {

    }

    // TODO: Move to functions above
    public void loadConfigs() {
        try {
            if (!this.optionFile.exists()) {
                return;
            }
            BufferedReader in = new BufferedReader(new FileReader(optionFile), 16*1024);
            Scanner read = new Scanner(in);
            read.useDelimiter(":|\\r\\n|\\n");
            String key;
            double doubleValue;
            boolean booleanValue;
            while (read.hasNext())
            {
                key = read.next();
                if (read.hasNextBoolean()) {
                    booleanValue = read.nextBoolean();
                    loadBooleanOptions(key, booleanValue);
                    System.out.print(key + " : " + booleanValue + " Boolean loaded");
                } else if (read.hasNextDouble()) {
                    doubleValue = read.nextDouble();
                    loadDoubleOptions(key, doubleValue);
                    System.out.print(key + " : " + doubleValue + " Double loaded");
                } else {
                    this.writeConfigs();
                    break;
                }
            }
            read.close();
        } catch (Exception e) {
            this.writeConfigs();
            LOGGER.error("Failed to load options", e);
        }
    }

    // TODO: Move to functions above
    public void writeConfigs() {
        Set<String> booleanOptionKeys = this.booleanOptions.keySet();
        Set<String> doubleOptionKeys = this.doubleOptions.keySet();

        try {
            optionFile.delete();
            FileWriter writer = new FileWriter(optionFile);
            for (String key : booleanOptionKeys) {
                boolean bValue = this.booleanOptions.get(key);
                writer.write(key + ":" + bValue + System.lineSeparator());
            }

            for (String key : doubleOptionKeys) {
                double dValue = this.doubleOptions.get(key);
                writer.write(key + ":" + dValue + System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            LOGGER.error("Failed write render tweak options", e);
        }

    }

    public void createNewFile() {
        try {
            this.optionFile.createNewFile();
            writeConfigs();
        } catch (Exception e) {
            LOGGER.error("Failed to create render tweak options file", e);
        }
    }

    public void loadBooleanOptions(String key, boolean value) {
        this.booleanOptions.put(key, value);
    }

    public void loadDoubleOptions(String key, double value) {
        this.doubleOptions.put(key, value);
    }

    public LinkedHashMap<String, Boolean> getBooleanOptions() {
        return this.booleanOptions;
    }

    public LinkedHashMap<String, Double> getDoubleOptions() {
        return this.doubleOptions;
    }

    public void setDerpyChicken(boolean isDerpyChicken) {
        this.derpyChicken = isDerpyChicken;
    }

    public void setWeather(boolean isWeather) {
        this.enableWeather = isWeather;
    }

    public void setParticlesEnabled(boolean isParticles) {
        this.particlesEnabled = isParticles;
    }

    public void setParticlesBlockBreaking(boolean isParticlesBlockBreaking) {
        this.particlesBlockBreaking = isParticlesBlockBreaking;
    }

    public void setFog(boolean isFOg) {
        this.fogEnabled = isFOg;
    }

    public void setGammaOverride(double isGammaOverride) {
        this.gammaOverride = isGammaOverride;
    }

    public void setGamma(double gamma) {
        ((IGameOptions)(this.gameOptions)).setGamma(gamma);
    }

    public double getGamma(double gamma) {
        return ((IGameOptions)(this.gameOptions)).getGamma();
    }

    public void setPrevGamma(double prevGamma) {
        this.prevGamma = prevGamma;
    }

    public boolean isDerpyChicken() {
        return this.derpyChicken;
    }

    public boolean isWeatherEnabled() {
        return this.enableWeather;
    }

    public boolean isParticlesEnabled() {
        return this.particlesEnabled;
    }

    public boolean isParticlesBlockBreakingEnabled() {
        return this.particlesBlockBreaking;
    }

    public boolean isFogEnabled() {
        return this.fogEnabled;
    }

    public double gammaOverride() {
        return this.gammaOverride;
    }

    public double getPrevGamma() {
        return this.prevGamma;
    }
}

