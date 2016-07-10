package com.proizvo.editor.view;

import com.proizvo.editor.impl.RPGMakerMVProj;
import com.proizvo.editor.tiles.TileSetImage;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

public class TileIconPanel extends JScrollPane implements ActionListener {

    protected final int tileSize = 32;
    protected final int cols = 8;
    protected final Dimension tileDim = new Dimension(tileSize, tileSize);

    public TileIconPanel(List<String> tileParts, RPGMakerMVProj proj) throws IOException {
        JPanel inner = new JPanel(new GridBagLayout());
        AtomicInteger idx = new AtomicInteger(0);
        handleTileParts(tileParts, proj, idx, inner);
        setViewportView(inner);
    }

    protected void handleTileParts(List<String> tileParts, RPGMakerMVProj proj,
            AtomicInteger idx, JPanel inner) throws IOException {
        for (String tilePart : tileParts) {
            TileSetImage pTile = proj.getTileSet(tilePart);
            List<String[]> pDescs = pTile.getDescs();
            for (int i = 0; i < pDescs.size(); i++) {
                String[] pDesc = pDescs.get(i);
                Image pImg = pTile.getPartImage(i);
                handle(pImg, pDesc[0], idx, inner);
            }
        }
    }

    protected void handle(Image pImg, String en, AtomicInteger idx, JPanel inner) {
        ImageIcon icon = new ImageIcon(pImg);
        JToggleButton btn = new JToggleButton(icon);
        btn.setToolTipText(en);
        btn.setPreferredSize(tileDim);
        btn.addActionListener(this);
        GridBagConstraints pos = new GridBagConstraints();
        pos.gridy = idx.get() / cols;
        pos.gridx = idx.get() - (pos.gridy * cols);
        inner.add(btn, pos);
        idx.incrementAndGet();
    }

    protected static JToggleButton lastButton;

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (lastButton != null) {
            lastButton.setSelected(false);
        }
        JToggleButton current = (JToggleButton) ae.getSource();
        lastButton = current;
    }
}
