package org.igye.sandbox.music.notes;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

class ChordNotesCmp extends JPanel {
    private final NoteUtils noteUtils;

    public ChordNotesCmp(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Rectangle clipBounds = g.getClipBounds();
        double windowWidth = clipBounds.getWidth();
        double windowHeight = clipBounds.getHeight();
        g.setColor(new Color(150, 150, 150));
        g.fillRect(0, 0, (int) windowWidth, (int) windowHeight);

        Graphics2D g2 = (Graphics2D) g;

    }

}