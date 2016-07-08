package com.proizvo.editor.app;

import com.proizvo.editor.api.IProject;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.FileUtils;

public class Environment {

    private Environment() {
    }

    private static Environment instance;

    public static synchronized Environment getInstance() {
        return instance == null ? (instance = new Environment()) : instance;
    }

    private IProject current;

    public IProject getCurrent() {
        return current;
    }

    public void setCurrent(IProject current) {
        this.current = current;
    }

    public Font loadFont(File file, Float size) throws IOException, FontFormatException {
        return loadFont(new FileInputStream(file), size);
    }

    public Font loadFont(URL url, Float size) throws IOException, FontFormatException {
        return loadFont(url.openStream(), size);
    }

    public Font loadFont(InputStream in, Float size) throws IOException, FontFormatException {
        Font font = Font.createFont(Font.TRUETYPE_FONT, in);
        if (size != null) {
            font = font.deriveFont(Font.PLAIN, size);
        }
        GraphicsEnvironment ge
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        return font;
    }

    public byte[] parseBase64(File file) throws IOException {
        String txt = FileUtils.readFileToString(file, "UTF8");
        return DatatypeConverter.parseBase64Binary(txt);
    }
}
