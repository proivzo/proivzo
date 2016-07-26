package com.proizvo.editor.cfg;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class Configuration {

    private Configuration() {
    }

    private static Configuration instance;

    public static synchronized Configuration getInstance() {
        return instance == null ? (instance = new Configuration()) : instance;
    }

    private final String encoding = "UTF8";
    private final String userHome = System.getProperty("user.home");
    private final File myHome = new File(userHome, ".proizvo");
    private final File lastLocation = new File(myHome, "last.loc");

    public boolean saveLastLocation(File location) {
        try {
            FileUtils.writeStringToFile(lastLocation, location.getAbsolutePath(), encoding);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public File loadLastLocation() {
        try {
            return new File(FileUtils.readFileToString(lastLocation, encoding));
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.getMessage());
            return null;
        }
    }
}
