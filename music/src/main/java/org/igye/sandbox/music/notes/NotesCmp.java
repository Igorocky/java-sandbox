package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.KeyboardCmpImpl;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class NotesCmp extends JPanel {
    private final NoteUtils noteUtils;
    private final KeyboardCmp keyboardCmp;
    private final List<Integer> clickedNotes = new ArrayList<>();

    public NotesCmp(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
        keyboardCmp = new KeyboardCmpImpl(noteUtils);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Integer clickedNote = keyboardCmp.getClickedNote(e.getX(), e.getY());
                if (clickedNote != null) {
                    clickedNotes.add(clickedNote);
                }
                repaint();
            }
        });
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
        renderClickedNotes(g2);

        double keyboardWidth = windowWidth * 0.9;
        double keyboardHeight = keyboardWidth * (15.0 / 122.5);
        Rect keyboardRect = Rect.make(0, 0, keyboardWidth, keyboardHeight);
        keyboardRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2));
        keyboardCmp.render(g2, keyboardRect);
    }

    private void renderClickedNotes(Graphics2D g) {
        double scaleFactor = 1.5;
        Rect rect = Rect.make(20, 50, 50 * scaleFactor, 40 * scaleFactor);
        List<List<Note>> notesToRender = clickedNotes.stream()
            .map(n -> Collections.singletonList(noteUtils.intToNote(n, NoteAccidental.SHARP)))
            .toList();
        g.setColor(Color.BLACK);
        noteUtils.renderStaff(g, rect, Clef.TREBLE, notesToRender);
    }
}