package com.proizvo.editor.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class Drawing {

    private Drawing() {
    }

    public static Graphics2D create2D(Image img) {
        return create2D(img.getGraphics());
    }

    public static Graphics2D create2D(Graphics raw) {
        Graphics2D g = (Graphics2D) raw;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g;
    }
}
