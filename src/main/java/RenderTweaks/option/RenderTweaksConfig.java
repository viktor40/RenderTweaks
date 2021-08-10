package RenderTweaks.option;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class RenderTweaksConfig {
    static final Logger LOGGER = LogManager.getLogger();
    public File optionFile;

    public RenderTweaksConfig(File f) {
        optionFile = f;
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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(optionFile)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to load options", e);
        }


    }

    public void createNewFile() {
        try {
            optionFile.createNewFile();
        } catch (Exception e) {
            LOGGER.error("Failed to create options", e);
        }
    }
}
