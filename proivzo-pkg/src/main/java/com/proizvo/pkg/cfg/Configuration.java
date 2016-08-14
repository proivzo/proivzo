package com.proizvo.pkg.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    private Configuration() {
    }

    private static Configuration instance;

    public static synchronized Configuration getInstance() {
        return instance == null ? (instance = new Configuration()) : instance;
    }

    private final String userHome = System.getProperty("user.home");
    private final File myHome = new File(userHome, ".proizvo");
    private final File packagerDef = new File(myHome, "packager.cfg");

    public Properties loadPackagerCfg() {
        Properties cfg = new Properties();
        try (InputStream in = new FileInputStream(packagerDef)) {
            cfg.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.getMessage());
        }
        return cfg;
    }

    public boolean savePackagerCfg(Properties cfg) {
        try (OutputStream out = new FileOutputStream(packagerDef)) {
            cfg.store(out, "Auto-generated configuration file");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
