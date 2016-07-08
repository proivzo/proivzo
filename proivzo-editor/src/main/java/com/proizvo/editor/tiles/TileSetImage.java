package com.proizvo.editor.tiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;

import static com.proizvo.editor.tiles.AutoTile.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TileSetImage {

    private final Image image;
    private final List<String[]> descs;
    private final Dimension block;

    public TileSetImage(Image image, List<String> lines) {
        this.image = (new ImageIcon(image)).getImage();
        this.descs = parseDesc(lines);
        this.block = determine(descs);
    }

    public Image getImage() {
        return image;
    }

    public List<String[]> getDescs() {
        return descs;
    }

    public Dimension getBlock() {
        return block;
    }

    private List<String[]> parseDesc(List<String> lines) {
        List<String[]> res = new LinkedList<>();
        for (String line : lines) {
            res.add(line.split("\\|"));
        }
        return res;
    }

    private Dimension determine(List<String[]> descs) {
        int count = descs.size();
        int partW;
        int partH;
        switch (count) {
            case 256:
                partW = 48;
                partH = 48;
                break;
            case 32:
                partW = 96;
                partH = 144;
                break;
            case 16:
                partW = 192;
                partH = 144;
                break;
            default:
                throw new UnsupportedOperationException("Unknown tile count '" + count + "!");
        }
        return new Dimension(partW, partH);
    }

    public int getRowCount() {
        return (int) (image.getHeight(null) / block.getHeight());
    }

    public int getColumnCount() {
        return (int) (image.getWidth(null) / block.getWidth());
    }

    public int getPartCount() {
        return getRowCount() * getColumnCount();
    }

    private final int bTileSize = 48;
    private final int tileSize = 32;

    public Image getPartImage(int idx) {
        int colCount = getPartCount() == 256 ? getColumnCount() / 2 : getColumnCount();
        int row = idx / colCount;
        int col = idx - (row * colCount);
        final int smallerTs = tileSize - 5;
        BufferedImage bi = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        int dx1 = 0;
        int dy1 = 0;
        int dx2 = smallerTs;
        int dy2 = smallerTs;
        int sx1 = (int) (col * block.getWidth());
        int sy1 = (int) (row * block.getHeight());
        if (sx1 >= image.getWidth(null) || sy1 >= image.getHeight(null)) {
            sx1 += image.getWidth(null) / 2;
            sy1 -= image.getHeight(null);
        }
        int sx2 = sx1 + bTileSize;
        int sy2 = sy1 + bTileSize;
        Color bgcolor = null;
        ImageObserver observer = null;
        g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
        g.dispose();
        return bi;
    }

    private List<BufferedImage> imgs;

    public Image getAutoImage(int tile) {
        if (imgs == null) {
            imgs = new LinkedList<>();
            for (int r = 0; r < getRowCount(); r++) {
                for (int c = 0; c < getColumnCount(); c++) {
                    try {
                        int x = (int) (c * block.getWidth());
                        int y = (int) (r * block.getHeight());
                        BufferedImage autoi = toBufferedImage(image, block, x, y);
                        imgs.addAll(buildTiles(autoi));
                    } catch (IOException ex) {
                        Logger.getLogger(TileSetImage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return imgs.get(tile);
    }
}
