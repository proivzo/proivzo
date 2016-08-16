package com.proizvo.pkg.con;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;
import org.apache.commons.io.output.TeeOutputStream;
import java.awt.Frame;

public class Terminal {

    public static void initTerminal(Frame parent, boolean modal, File tempDir) {
        // Initialize GUI console
        final ConsoleDialog con = new ConsoleDialog(parent, modal);
        con.setVisible(true);
        // Redirect streams
        System.setOut(new PrintStream(new TeeOutputStream(
                toFile(new File(tempDir, "output.log")),
                new OutputStream() {
            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.white;
                JTextArea area = con.getNormalTextA();
                area.setBackground(Color.black);
                area.setForeground(txtColor);
                area.append(String.valueOf((char) bits));
                area.setCaretPosition(area.getDocument().getLength());
            }
        })));
        System.setErr(new PrintStream(new TeeOutputStream(
                toFile(new File(tempDir, "error.log")),
                new OutputStream() {
            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.red;
                JTextArea area = con.getErrorTxtA();
                area.setBackground(Color.black);
                area.setForeground(txtColor);
                area.append(String.valueOf((char) bits));
                area.setCaretPosition(area.getDocument().getLength());
            }
        })));
    }

    private static OutputStream toFile(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException fnf) {
            throw new UnsupportedOperationException(fnf);
        }
    }
}
