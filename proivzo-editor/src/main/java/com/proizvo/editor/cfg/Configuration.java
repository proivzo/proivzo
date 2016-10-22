package com.proizvo.editor.cfg;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.east301.keyring.Keyring;
import net.east301.keyring.PasswordRetrievalException;
import org.apache.commons.io.FileUtils;
import com.proizvo.editor.cloud.Credentials;
import net.east301.keyring.BackendNotSupportedException;
import net.east301.keyring.PasswordSaveException;
import net.east301.keyring.util.LockException;

public class Configuration {

    private Configuration() {
    }

    private static Configuration instance;

    public static synchronized Configuration getInstance() {
        return instance == null ? (instance = new Configuration()) : instance;
    }

    private final String encoding = "UTF8";
    private final String userHome = System.getProperty("user.home");
    private final String service = "proizvo";
    private final File myHome = new File(userHome, "." + service);
    private final File lastLocation = new File(myHome, "last.loc");
    private final File lastUser = new File(myHome, "last.usr");
    private final File secrets = new File(myHome, "secrets.bin");

    public boolean saveLastLocation(File location) {
        try {
            FileUtils.writeStringToFile(lastLocation, location.getAbsolutePath(), encoding);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.getMessage());
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

    public Credentials loadLastCredentials() {
        try {
            String lastAccount = FileUtils.readFileToString(lastUser, encoding);
            Keyring ring = Keyring.create();
            ring.setKeyStorePath(secrets.getAbsolutePath());
            return new Credentials(lastAccount, ring.getPassword(service, lastAccount));
        } catch (IOException | BackendNotSupportedException | LockException | PasswordRetrievalException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.getMessage());
            return null;
        }
    }

    public boolean saveLastCredentials(Credentials creds) {
        try {
            FileUtils.writeStringToFile(lastUser, creds.getUser(), encoding);
            Keyring ring = Keyring.create();
            ring.setKeyStorePath(secrets.getAbsolutePath());
            ring.setPassword(service, creds.getUser(), creds.getPassword());
            return true;
        } catch (IOException | BackendNotSupportedException | LockException | PasswordSaveException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.WARNING, ex.getMessage());
            return false;
        }
    }
}
