package RenderTweaks.option;

import RenderTweaks.interfaces.IGameOptions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

// TODO: Put RenderTweaksConfig into RenderTweaksGameOptions
@Environment(EnvType.CLIENT)
public class RenderTweaksConfig {
    static final Logger LOGGER = LogManager.getLogger();
    public File optionFile;
    public GameOptions options;

    public RenderTweaksConfig(File f, GameOptions gameOptions) {
        this.optionFile = f;
        this.options = gameOptions;
        if (!this.optionFile.exists()) {
            createNewFile();
        }
    }

    public boolean getBooleanOption(String key) {
        return false;
    }

    public double getDoubleOption(String key) {
        return 0.0D;
    }

    public void settBooleanOption(String key) {

    }

    public void setDoubleOption(String key) {

    }

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
                    ((IGameOptions)options).loadBooleanOptions(key, booleanValue);
                    System.out.print(key + " : " + booleanValue + " Boolean loaded");
                } else if (read.hasNextDouble()) {
                    doubleValue = read.nextDouble();
                    ((IGameOptions)options).loadDoubleOptions(key, doubleValue);
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

    public void writeConfigs() {
        LinkedHashMap<String, Boolean> booleanConfigs = ((IGameOptions)options).getBooleanOptions();
        Set<String> booleanOptionKeys = booleanConfigs.keySet();

        LinkedHashMap<String, Double> doubleConfigs = ((IGameOptions)options).getDoubleOptions();
        Set<String> doubleOptionKeys = doubleConfigs.keySet();

        try {
            optionFile.delete();
            FileWriter writer = new FileWriter(optionFile);
            for (String key : booleanOptionKeys) {
                boolean bValue = booleanConfigs.get(key);
                writer.write(key + ":" + bValue + System.lineSeparator());
            }

            for (String key : doubleOptionKeys) {
                double dValue = doubleConfigs.get(key);
                writer.write(key + ":" + dValue + System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            LOGGER.error("Failed write render tweak options", e);
        }

    }

    public void createNewFile() {
        try {
            optionFile.createNewFile();
            writeConfigs();
        } catch (Exception e) {
            LOGGER.error("Failed to create render tweak options file", e);
        }
    }
}
