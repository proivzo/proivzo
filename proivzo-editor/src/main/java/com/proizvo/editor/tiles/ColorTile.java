package com.proizvo.editor.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ColorTile {

    private static final int tileSize = 48;

    public static Image drawTransparentHalfed() {
        return drawHalfed("e5e5e5", "ffffff");
    }

    public static Image drawHalfed(String firstColor, String secondColor) {
        BufferedImage img = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        int half = tileSize / 2;
        g.setColor(Color.decode("#" + firstColor));
        g.fillRect(0, 0, half, half);
        g.fillRect(half, half, half, half);
        g.setColor(Color.decode("#" + secondColor));
        g.fillRect(0, half, half, half);
        g.fillRect(half, 0, half, half);
        g.dispose();
        return img;
    }
}
