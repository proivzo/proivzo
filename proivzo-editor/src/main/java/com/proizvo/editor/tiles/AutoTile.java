package com.proizvo.editor.tiles;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class AutoTile {

    public static List<BufferedImage> buildTiles(BufferedImage auto) throws IOException {
        List<BufferedImage> list = new LinkedList<>();
        for (int[] array : readHowto()) {
            list.add(buildTile(auto, array));
        }
        return list;
    }

    public static BufferedImage buildTile(BufferedImage auto, int... array) {
        final int tileSize = 48;
        final int halfSize = tileSize / 2;
        BufferedImage tile = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tile.createGraphics();
        int idx = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                // Reference
                int ref = array[idx] - 1;
                int rr = ref / 2;
                int rc = ref - (rr * 2);
                // Source        
                int sx1 = rc * tileSize + (x * halfSize);
                int sy1 = rr * tileSize + (y * halfSize);
                int sx2 = sx1 + halfSize;
                int sy2 = sy1 + halfSize;
                // Destination
                int dx1 = x * halfSize;
                int dy1 = y * halfSize;
                int dx2 = dx1 + halfSize;
                int dy2 = dy1 + halfSize;
                // Draw it!
                g2d.drawImage(auto, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
                idx++;
            }
        }
        g2d.dispose();
        return tile;
    }

    public static BufferedImage toBufferedImage(Image image, Dimension dim, int x, int y) {
        int width = (int) dim.getWidth();
        int height = (int) dim.getHeight();
        BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, x, y, x + width, y + height, null);
        g2d.dispose();
        return buffImage;
    }

    public static int[][] readHowto() throws IOException {
        ClassLoader clazz = AutoTile.class.getClassLoader();
        try (InputStream in = clazz.getResourceAsStream("internal/autotiles.json")) {
            try (InputStreamReader rd = new InputStreamReader(in)) {
                Gson json = new Gson();
                return json.fromJson(rd, int[][].class);
            }
        }
    }
}
