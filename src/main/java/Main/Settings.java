package Main;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static Settings instance;

    private final String repoType;

    private final String repoMasina;
    private final String repoInchiriere;


    private Settings(String repoType, String masina, String inchirire) {
        this.repoType = repoType;
        this.repoMasina = masina;
        this.repoInchiriere = inchirire;
    }

    public String getRepoMasina() {
        return repoMasina;
    }

    public String getRepoInchiriere() {
        return repoInchiriere;
    }

    public String getRepoType() {
        return repoType;
    }

    private static Properties loadSettings() {
        try (FileReader fr = new FileReader("settings.properties.txt")) {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Settings getInstance() {
        Properties properties = loadSettings();
        instance = new Settings(properties.getProperty("repo_type"), properties.getProperty("Masina"), properties.getProperty("Inchiriere"));
        return instance;
    }
}
