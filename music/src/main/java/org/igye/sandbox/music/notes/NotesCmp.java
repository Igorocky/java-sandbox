package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.KeyboardCmpImpl;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
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
                System.out.println("clickedNote=" + clickedNote);
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

        renderClickedNotes(g);

        double keyboardWidth = windowWidth * 0.9;
        double keyboardHeight = keyboardWidth * (15.0 / 122.5);
        Rect keyboardRect = Rect.make(0, 0, keyboardWidth, keyboardHeight);
        keyboardRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2));
        keyboardCmp.render(g, keyboardRect);
    }

    private void renderClickedNotes(Graphics g) {
        g.setColor(Color.BLACK);
        Rect rect = Rect.make(20, 20, 50, 40);
        for (Integer clickedNote : clickedNotes) {
            noteUtils.renderNotes(
                g, rect, Clef.TREBLE, Collections.singletonList(noteUtils.intToNote(clickedNote, NoteAccidental.SHARP))
            );
            rect.setLeft(rect.right());
        }

    }
}