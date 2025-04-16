package org.igye.sandbox.music.notes;

import org.apache.commons.lang3.tuple.Pair;
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
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NotesCmp extends JPanel {
    private final NoteUtils noteUtils;
    private final KeyboardCmp keyboardCmp;

    private final List<Pair<Clef, Integer>> notesToRepeat = new ArrayList<>();
    private boolean lastClickedNoteIsCorrect = true;
    private final Map<Clef, Map<Integer, Integer>> noteCounts;

    public NotesCmp(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
        keyboardCmp = new KeyboardCmpImpl(noteUtils);

        prepareNotesToRepeat();
        noteCounts = new EnumMap<>(Clef.class);
        noteCounts.put(Clef.BASS, new HashMap<>());
        noteCounts.put(Clef.TREBLE, new HashMap<>());
        for (Pair<Clef, Integer> clefAndNote : notesToRepeat) {
            resetCount(noteCounts, clefAndNote.getLeft(), clefAndNote.getRight());
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Integer clickedNote = keyboardCmp.getClickedNote(e.getX(), e.getY());
                if (clickedNote != null) {
                    processClickedNote(clickedNote);
                }
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

        double keyboardWidth = windowWidth * 0.9;
        double keyboardHeight = keyboardWidth * (15.0 / 122.5);
        Rect keyboardRect = Rect.make(0, 0, keyboardWidth, keyboardHeight);
        keyboardRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2 + windowHeight * 0.15));
        keyboardCmp.render(g2, keyboardRect);

        int numOfNotes = 1;
        double staffWidth = keyboardWidth * 0.1;
        double staffHeight = staffWidth / (numOfNotes + 1) * 0.7;
        Rect staffRect = Rect.make(0, 0, staffWidth, staffHeight);
        staffRect.setMidMid(Point.make(windowWidth / 2, windowHeight / 2 - windowHeight * 0.2));
        staffRect.setWidth(staffRect.width() / (numOfNotes + 1));
        renderCurNote(g2, staffRect);
    }

    private void prepareNotesToRepeat() {
        int bassFirstNote = noteUtils.strToNote("1D");
        int bassLastNote = noteUtils.strToNote("5D");
        int trebleFirstNote = noteUtils.strToNote("2B");
        int trebleLastNote = noteUtils.strToNote("6B");
        notesToRepeat.clear();
        for (int i = bassFirstNote; i <= bassLastNote; i++) {
            if (noteUtils.isWhiteKey(i)) {
                notesToRepeat.add(Pair.of(Clef.BASS, i));
            }
        }
        for (int i = trebleFirstNote; i <= trebleLastNote; i++) {
            if (noteUtils.isWhiteKey(i)) {
                notesToRepeat.add(Pair.of(Clef.TREBLE, i));
            }
        }
        Collections.shuffle(notesToRepeat);
    }

    private void processClickedNote(int clickedNote) {
        Pair<Clef, Integer> curClefAndNote = notesToRepeat.getFirst();
        Clef curClef = curClefAndNote.getLeft();
        int curNote = curClefAndNote.getRight();
        if (curNote != clickedNote) {
            lastClickedNoteIsCorrect = false;
        } else {
            lastClickedNoteIsCorrect = true;
            incCount(noteCounts, curClef, curNote);
            notesToRepeat.removeFirst();
            if (notesToRepeat.isEmpty()) {
                prepareNotesToRepeat();
            }
        }
        repaint();
        printTotalCounts();
    }

    private int getCount(Map<Clef, Map<Integer, Integer>> counts, Clef clef, int note) {
        return counts.get(clef).get(note);
    }

    private void resetCount(Map<Clef, Map<Integer, Integer>> counts, Clef clef, int note) {
        counts.get(clef).put(note, 0);
    }

    private void incCount(Map<Clef, Map<Integer, Integer>> counts, Clef clef, int note) {
        Map<Integer, Integer> subCounts = counts.get(clef);
        subCounts.put(note, subCounts.get(note) + 1);
    }

    private void printTotalCounts() {
        System.out.println("------------------------------------------------------------------------------");
        printCounts("Note counts", noteCounts);
        System.out.println("------------------------------------------------------------------------------");

    }

    private void printCounts(String title, Map<Clef, Map<Integer, Integer>> counts) {
        System.out.println(title + ":");
        Map<Integer, Integer> bassCounts = counts.get(Clef.BASS);
        Map<Integer, Integer> trebleCounts = counts.get(Clef.TREBLE);
        printCountsForClef(Clef.BASS, bassCounts);
        printCountsForClef(Clef.TREBLE, trebleCounts);
        int minCnt = Math.min(
            bassCounts.values().stream().min(Integer::compareTo).orElse(0),
            trebleCounts.values().stream().min(Integer::compareTo).orElse(0)
        );
        int maxCnt = Math.max(
            bassCounts.values().stream().max(Integer::compareTo).orElse(0),
            trebleCounts.values().stream().max(Integer::compareTo).orElse(0)
        );
        System.out.printf("minCnt: %s, maxCnt: %s%n", minCnt, maxCnt);
    }

    private void printCountsForClef(Clef clef, Map<Integer, Integer> counts) {
        System.out.println("    " + clef + ":");
        counts.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .forEach(entry ->
                System.out.println(String.format(
                    "        %s: %s", noteUtils.noteToStr(entry.getKey()), entry.getValue()
                ))
            );
    }

    private void renderCurNote(Graphics2D g, Rect rect) {
        Pair<Clef, Integer> curClefAndNote = notesToRepeat.get(0);
        Clef curClef = curClefAndNote.getLeft();
        int curNote = curClefAndNote.getRight();
        if (lastClickedNoteIsCorrect) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.RED);
        }
        noteUtils.renderStaff(
            g, rect, curClef, List.of(List.of(noteUtils.intToNote(curNote, NoteAccidental.SHARP)))
        );
    }
}