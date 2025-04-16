package org.igye.sandbox.music.notes;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

class ChordNotesCmp extends JPanel {
    private final NoteUtils noteUtils;
    private Clef curClef;
    private List<Note> curNotes;
    private boolean lastAnsIsCorrect;

    public ChordNotesCmp(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
    }

    public void setCurNotes(Clef curClef, List<Note> curNotes, boolean lastAnsIsCorrect) {
        this.curClef = curClef;
        this.curNotes = curNotes;
        this.lastAnsIsCorrect = lastAnsIsCorrect;
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

        int numOfNotes = 3;
        double staffWidth = windowWidth * 0.2;
        double staffHeight = staffWidth / (numOfNotes + 1) * 0.7;
        Rect staffRect = Rect.make(0, 0, staffWidth, staffHeight);
        staffRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2));
        staffRect.setWidth(staffRect.width() / (numOfNotes + 1));

        if (lastAnsIsCorrect) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }
        noteUtils.renderStaff(
            g2, staffRect, curClef, curNotes.stream().map(Collections::singletonList).toList()
        );
    }

}