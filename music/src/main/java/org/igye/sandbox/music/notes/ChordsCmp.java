package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.NoteUtilsImpl;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

class ChordsCmp extends JPanel {
    private final NoteUtils noteUtils;
    private final Random rand = new Random();
    private final ChordNotesCmp chordNotesCmp;
    private final JTextField ansField;

    private Clef curClef;
    private List<Note> curNotes;
    private Optional<String> curChord;
    private boolean lastAnsIsCorrect = true;

    public ChordsCmp(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
        ansField = new JTextField(10);
        this.setLayout(new BorderLayout());
        chordNotesCmp = new ChordNotesCmp(noteUtils);
        this.add(chordNotesCmp, BorderLayout.CENTER);
        this.add(ansField, BorderLayout.PAGE_END);

        ansField.addActionListener(_ -> this.onAnswer());

        generateNextChord();
        chordNotesCmp.setCurNotes(curClef, curNotes, lastAnsIsCorrect);
    }

    private void generateNextChord() {
        curClef = rand.nextBoolean() ? Clef.TREBLE : Clef.BASS;
        boolean isTriad = rand.nextBoolean();
        int minNote = curClef == Clef.TREBLE ? noteUtils.strToNote("2B") : noteUtils.strToNote("1D");
        int maxNote = curClef == Clef.TREBLE ? noteUtils.strToNote("6B") : noteUtils.strToNote("5D");
        int n1 = rand.nextInt(minNote, maxNote);
        int n2;
        int n3;
        if (isTriad) {
            n2 = n1 + (rand.nextBoolean() ? 4 : 3);
            n3 = n1 + 7;
        } else {
            if (rand.nextBoolean()) {
                n2 = n1 + (rand.nextBoolean() ? 4 : 3);
                n3 = n1 + 6;
            } else {
                n2 = n1 + 5;
                n3 = n1 + 7;
            }
        }
        List<Integer> triad = List.of(n1, n2, n3);
        curNotes = triad.stream()
            .map(n -> {
                List<Integer> possibleNotes = Stream.iterate(0, i -> i + 1)
                    .limit(21)
                    .map(i -> (i - 10) * 12 + n)
                    .filter(nn -> minNote <= nn && nn <= maxNote)
                    .toList();
                return possibleNotes.get(rand.nextInt(0, possibleNotes.size()));
            })
            .map(n -> noteUtils.intToNote(n, rand.nextBoolean() ? NoteAccidental.SHARP : NoteAccidental.FLAT))
            .toList();
        curChord = noteUtils.triadToStr(triad);
    }

    private void onAnswer() {
        String ans = noteUtils.strToTriad(ansField.getText(), 4)
            .flatMap(noteUtils::triadToStr)
            .orElse(ansField.getText());
        if (!curChord.orElse("").equals(ans)) {
            lastAnsIsCorrect = false;
        } else {
            lastAnsIsCorrect = true;
            ansField.setText("");
            generateNextChord();
        }
        chordNotesCmp.setCurNotes(curClef, curNotes, lastAnsIsCorrect);
        repaint();
    }

    public static void main(String[] args) {
        new ChordsCmp(new NoteUtilsImpl()).checkProbabilityDistribution();
    }

    private void checkProbabilityDistribution() {
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i < 10_000_000; i++) {
            generateNextChord();
            String curChordStr = curChord.orElse("");
            counts.put(curChordStr, counts.getOrDefault(curChordStr, 0) + 1);
        }

        Integer sumOfEmpty = counts.entrySet().stream()
            .filter(e -> e.getKey().isEmpty())
            .map(Map.Entry::getValue)
            .reduce(0, Integer::sum);

        Integer sumOfNonEmpty = counts.entrySet().stream()
            .filter(e -> !e.getKey().isEmpty())
            .map(Map.Entry::getValue)
            .reduce(0, Integer::sum);

        counts.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
        System.out.println("sumOfNonEmpty = " + sumOfNonEmpty);
        System.out.println("sumOfEmpty = " + sumOfEmpty);
    }
}