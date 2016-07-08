package com.proizvo.editor.tiles;

import com.proizvo.editor.data.Map;
import com.proizvo.editor.data.Tileset;
import com.proizvo.editor.impl.RPGMakerMVProj;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JPanel;

public class TilePanel extends JPanel {

    private final int tileSize = 48;
    private Map map;
    private Tileset set;
    private RPGMakerMVProj proj;
    private HashMap<String, TileSetImage> tiles;

    private final boolean labelEnabled = false;
    private final boolean gridEnabled = false;
    private final boolean tilesEnabled = true;

    public TilePanel() {
        setBackground(Color.lightGray);
    }

    public void update(Map map, Tileset set, RPGMakerMVProj proj) throws IOException {
        this.map = map;
        this.set = set;
        this.proj = proj;
        this.tiles = new LinkedHashMap<>();
        for (String tsName : set.getTilesetNames()) {
            tiles.put(tsName, proj.getTileSet(tsName));
        }
    }

    private void paintGrid(Graphics g) {
        if (map == null || !gridEnabled) {
            return;
        }
        for (int x = 0; x <= map.getWidth(); x++) {
            g.drawLine(x * tileSize, 0, x * tileSize, tileSize * map.getHeight());
        }
        for (int y = 0; y <= map.getHeight(); y++) {
            g.drawLine(0, y * tileSize, tileSize * map.getWidth(), y * tileSize);
        }
    }

    private void paintTiles(Graphics g, int level) {
        if (map == null || !tilesEnabled) {
            return;
        }
        int idx = (level * (map.getHeight() * map.getWidth()));
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                int val = map.getData()[idx++];
                paintTile(g, x * tileSize, y * tileSize, val);
            }
        }
    }

    private void paintTile(Graphics g, int x, int y, int tile) {
        String tileImgName = "?";
        if (tile <= 255) {
            tileImgName = "World_B";
        } else if (tile <= 511) {
            tile -= 256;
            tileImgName = "World_C";
        } else if (tile >= 2816) {
            tile -= 2816;
            tileImgName = "World_A2";
        } else if (tile >= 2048) {
            tile -= 2048;
            tileImgName = "World_A1";
        }
        TileSetImage myTile = tiles.get(tileImgName);
        if (myTile.getPartCount() != 256) {
            // Get some auto-generated image...
            Image someImg = myTile.getAutoImage(tile);
            // Draw it!
            g.drawImage(someImg, x, y, this);
            if (labelEnabled && tile != 0) {
                g.drawString(tile + "", x + 3, y + 9);
            }
            return;
        }
        Image tileImg = myTile.getImage();
        // Destination rectangle
        int dx1 = x;
        int dy1 = y;
        int dx2 = dx1 + tileSize;
        int dy2 = dy1 + tileSize;
        // Determine position
        int colSize = 8;
        int row = (tile / colSize);
        int col = tile - (row * colSize);
        // Source rectangle
        int sx1 = tileSize * col;
        int sy1 = tileSize * row;
        int sx2 = sx1 + tileSize;
        int sy2 = sy1 + tileSize;
        // Correct bounds
        int height = tileImg.getHeight(this);
        if (sy2 > height) {
            sy1 -= height;
            sy2 -= height;
            int offset = tileImg.getWidth(this) / 2;
            sx1 += offset;
            sx2 += offset;
        }
        // Draw it!
        g.drawImage(tileImg, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
        if (labelEnabled && tile != 0) {
            g.drawString(tile + "", x + 3, y + 9);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
        for (int l = 0; l < 6; l++) {
            paintTiles(g, l);
        }
    }
}
