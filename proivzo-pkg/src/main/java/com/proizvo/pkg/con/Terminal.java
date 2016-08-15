package com.proizvo.pkg.con;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import mikera.gui.JConsole;
import org.apache.commons.io.output.TeeOutputStream;

public class Terminal {

    public static void initTerminal(File tempDir) {
        // Initialize GUI console
        final JConsole con = new mikera.gui.JConsole(80, 25);
        con.setCursorVisible(true);
        con.setCursorBlink(true);
        con.setVisible(true);
        mikera.gui.Frames.display(con, "Terminal");
        // Redirect streams
        System.setOut(new PrintStream(new TeeOutputStream(
                toFile(new File(tempDir, "output.log")),
                new OutputStream() {
            private final StringBuilder bld = new StringBuilder();

            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.white;
                printInTerminal(con, bits, txtColor, bld);
            }
        })));
        System.setErr(new PrintStream(new TeeOutputStream(
                toFile(new File(tempDir, "error.log")),
                new OutputStream() {
            private final StringBuilder bld = new StringBuilder();

            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.red;
                printInTerminal(con, bits, txtColor, bld);
            }
        })));
    }

    private static void printInTerminal(JConsole con, int bits, Color color, StringBuilder bld) {
        String sign = new String(new byte[]{(byte) bits});
        printInTerminal(con, sign, color, bld);
    }

    private static void printInTerminal(JConsole con, String txt, Color color, StringBuilder bld) {
        bld.append(txt);
        if (txt.contains("\n")) {
            con.write(bld + "", color, con.getBackground());
            con.repaint();
            if (con.getCursorY() >= con.getRows()) {
                con.clearScreen();
            }
            bld.setLength(0);
        }
    }

    private static OutputStream toFile(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException fnf) {
            throw new UnsupportedOperationException(fnf);
        }
    }
}
