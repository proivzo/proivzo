package com.proizvo.editor.view;

import com.proizvo.editor.app.Environment;
import com.proizvo.editor.impl.RPGMakerMVProj;
import com.proizvo.editor.tiles.ColorTile;
import com.proizvo.editor.util.Drawing;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class EventIconPanel extends TileIconPanel {

    private static final Color[] colors = loadColors();

    public EventIconPanel(List<String> tileParts, RPGMakerMVProj proj) throws IOException {
        super(tileParts, proj);
    }

    @Override
    protected void handleTileParts(List<String> tileParts, RPGMakerMVProj proj,
            AtomicInteger idx, JPanel inner) throws IOException {
        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                Image tri = ColorTile.drawTransparentHalfed();
                handle(tri, null, idx, inner);
                continue;
            }
            int colorIdx = (i - 1) % colors.length;
            Color color = colors[colorIdx];
            Image img1 = ColorTile.drawTransparentHalfed();
            Image img2 = ColorTile.drawHalfed(color, color);
            Image img = Drawing.combine(img1, img2, 0.5f);
            String label = i + "";
            img = Drawing.addCenteredStr(label, Color.white, 16, img);
            handle(img, label, idx, inner);
        }
    }

    private static Color[] loadColors() {
        try {
            Environment env = Environment.getInstance();
            String[] array = env.loadJson(EventIconPanel.class, "internal/colors.json", String[].class);
            Color[] res = new Color[array.length];
            for (int i = 0; i < res.length; i++) {
                res[i] = Color.decode("0x" + array[i]);
            }
            return res;
        } catch (IOException ex) {
            Logger.getLogger(EventIconPanel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
