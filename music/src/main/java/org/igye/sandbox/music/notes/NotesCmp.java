package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.KeyboardCmpImpl;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class NotesCmp extends JPanel {
    private KeyboardCmp keyboardCmp;

    public NotesCmp(NoteUtils noteUtils) {
        keyboardCmp = new KeyboardCmpImpl(noteUtils);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Rectangle clipBounds = g.getClipBounds();
        double windowWidth = clipBounds.getWidth();
        double windowHeight = clipBounds.getHeight();
        g.setColor(new Color(150, 150, 150));
        g.fillRect(0, 0, (int) windowWidth, (int) windowHeight);

        double keyboardWidth = windowWidth * 0.9;
        double keyboardHeight = keyboardWidth * (15.0 / 122.5);
        Rect keyboardRect = Rect.make(0, 0, keyboardWidth, keyboardHeight);
        keyboardRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2));
        keyboardCmp.render(g, keyboardRect);
    }
}