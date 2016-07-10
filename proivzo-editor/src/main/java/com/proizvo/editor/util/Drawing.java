package com.proizvo.editor.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Composite;

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

    public static Image combine(Image ground, Image overlay, float alpha) {
        int width = Math.max(ground.getWidth(null), overlay.getWidth(null));
        int height = Math.max(ground.getHeight(null), overlay.getHeight(null));
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = create2D(img);
        g.drawImage(ground, 0, 0, width, height, null);
        int rule = AlphaComposite.SRC_OVER;
        Composite comp = AlphaComposite.getInstance(rule, alpha);
        g.setComposite(comp);
        g.drawImage(overlay, 0, 0, width, height, null);
        return img;
    }
}
