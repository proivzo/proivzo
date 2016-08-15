package com.proizvo.pkg.con;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import mikera.gui.JConsole;

public class Terminal {

    public static void initTerminal() {
        // Initialize GUI console
        final JConsole con = new mikera.gui.JConsole(80, 25);
        con.setCursorVisible(true);
        con.setCursorBlink(true);
        con.setVisible(true);
        mikera.gui.Frames.display(con, "Terminal");
        // Redirect streams
        System.setOut(new PrintStream(new OutputStream() {
            private final StringBuilder bld = new StringBuilder();

            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.white;
                printInTerminal(con, bits, txtColor, bld);
            }
        }));
        System.setErr(new PrintStream(new OutputStream() {
            private final StringBuilder bld = new StringBuilder();

            @Override
            public void write(int bits) throws IOException {
                Color txtColor = Color.red;
                printInTerminal(con, bits, txtColor, bld);
            }
        }));
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
}
