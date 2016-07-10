package com.proizvo.editor.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;

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

    public static void drawCenteredStr(String txt, int w, int h, Font font, Color color, Graphics g) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(txt)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(txt, x, y);
    }

    public static void drawCenteredStr(String txt, int w, int h, int fontSize, Color color, Graphics g) {
        Font font = g.getFont().deriveFont(Font.BOLD, fontSize);
        drawCenteredStr(txt, w, h, font, color, g);
    }

    public static Image addCenteredStr(String txt, Color color, int size, Image img) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        Graphics g = create2D(img);
        drawCenteredStr(txt, w, h, size, color, g);
        return img;
    }
}
