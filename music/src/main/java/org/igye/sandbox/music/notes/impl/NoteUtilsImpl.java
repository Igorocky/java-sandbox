package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.Clef;
import org.igye.sandbox.music.notes.KeySignature;
import org.igye.sandbox.music.notes.Note;
import org.igye.sandbox.music.notes.NoteUtils;
import org.igye.sandbox.music.notes.Rect;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NoteUtilsImpl implements NoteUtils {
    private static final int MAX_NOTE = 87;
    private static final int numOfKeys = MAX_NOTE + 1;
    private static final Set<Integer> WHITE_KEYS_IN_OCTAVE = Set.of(0, 2, 4, 5, 7, 9, 11);
    private static final String[] IDX_WITHIN_OCTAVE_TO_NOTE_NAME =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private final Map<String, Integer> noteNameToIdxWithinOctave;
    private final int numOfWhiteKeys;
    private final Map<Integer, Integer> noteToWhiteKeyIdx;
    private final Map<Integer, Integer> whiteKeyIdxToNote;
    private final Map<Integer, Integer> noteToBlackKeyIdx;
    private final Map<Integer, Integer> blackKeyIdxToNote;
    private final int bassMiddleNoteWhiteKeyIdx;
    private final int trebleMiddleNoteWhiteKeyIdx;

    public NoteUtilsImpl() {
        Map<String, Integer> noteNames = new HashMap<>();
        for (int i = 0; i < IDX_WITHIN_OCTAVE_TO_NOTE_NAME.length; i++) {
            noteNames.put(IDX_WITHIN_OCTAVE_TO_NOTE_NAME[i], i);
        }
        noteNames.put("Cb", noteNames.get("B"));
        noteNames.put("Db", noteNames.get("C#"));
        noteNames.put("Eb", noteNames.get("D#"));
        noteNames.put("Fb", noteNames.get("E"));
        noteNames.put("Gb", noteNames.get("F#"));
        noteNames.put("Ab", noteNames.get("G#"));
        noteNames.put("Bb", noteNames.get("A#"));
        this.noteNameToIdxWithinOctave = Collections.unmodifiableMap(noteNames);

        Set<Integer> whiteKeys = new HashSet<>();
        Map<Integer, Integer> noteToWhiteKeyIdx = new HashMap<>();
        for (int n = 0; n <= MAX_NOTE; n++) {
            if (isWhiteKey(n)) {
                whiteKeys.add(n);
                noteToWhiteKeyIdx.put(n, noteToWhiteKeyIdx.size());
            }
        }
        this.noteToWhiteKeyIdx = Collections.unmodifiableMap(noteToWhiteKeyIdx);
        this.whiteKeyIdxToNote = this.noteToWhiteKeyIdx.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getValue, Map.Entry::getKey
        ));
        numOfWhiteKeys = whiteKeys.size();

        Set<Integer> blackKeys = new HashSet<>();
        Map<Integer, Integer> noteToBlackKeyIdx = new HashMap<>();
        for (int n = 0; n <= MAX_NOTE; n++) {
            if (!isWhiteKey(n)) {
                blackKeys.add(n);
                noteToBlackKeyIdx.put(n, noteToBlackKeyIdx.size());
            }
        }
        this.noteToBlackKeyIdx = Collections.unmodifiableMap(noteToBlackKeyIdx);
        this.blackKeyIdxToNote = this.noteToBlackKeyIdx.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getValue, Map.Entry::getKey
        ));

        bassMiddleNoteWhiteKeyIdx = noteToWhiteKeyIdx(strToNote("3D"));
        trebleMiddleNoteWhiteKeyIdx = noteToWhiteKeyIdx(strToNote("4B"));
    }

    @Override
    public int numOfKeys() {
        return numOfKeys;
    }

    @Override
    public int numOfWhiteKeys() {
        return numOfWhiteKeys;
    }

    @Override
    public boolean isWhiteKey(int note) {
        if (note == 0 || note == 2) {
            return true;
        } else if (note == 1) {
            return false;
        }
        note = (note - 3) % 12;
        return WHITE_KEYS_IN_OCTAVE.contains(note);
    }

    @Override
    public String noteToStr(int note) {
        if (note == 0) {
            return "0A";
        }
        if (note == 1) {
            return "0Bb";
        }
        if (note == 2) {
            return "0B";
        }
        note = note - 3;
        return (note / 12 + 1) + IDX_WITHIN_OCTAVE_TO_NOTE_NAME[note % 12];
    }

    @Override
    public int strToNote(String str) {
        int octave = Integer.parseInt(str.substring(0, 1));
        String noteName = str.substring(1);
        Integer idxWithinOctave = noteNameToIdxWithinOctave.get(noteName);
        int note = octave * 12 + idxWithinOctave - 9;
        if (note < 0 || MAX_NOTE < note) {
            throw new RuntimeException("note < 0 || MAX_NOTE < note");
        }
        return note;
    }

    @Override
    public int noteToWhiteKeyIdx(int note) {
        return noteToWhiteKeyIdx.get(note);
    }

    @Override
    public int whiteKeyIdxToNote(int idx) {
        return whiteKeyIdxToNote.get(idx);
    }

    @Override
    public int blackKeyIdxToNote(int idx) {
        return blackKeyIdxToNote.get(idx);
    }

    @Override
    public Note intToNote(int intNote, KeySignature signature) {
        if (isWhiteKey(intNote)) {
            return Note.make(noteToWhiteKeyIdx(intNote), KeySignature.NONE);
        } else {
            return switch (signature) {
                case FLAT -> Note.make(noteToWhiteKeyIdx(intNote + 1), signature);
                case SHARP -> Note.make(noteToWhiteKeyIdx(intNote - 1), signature);
                case NONE -> throw new RuntimeException("signature == NONE");
            };
        }
    }

    @Override
    public void renderNotes(Graphics g, Rect rect, Clef clef, Collection<Note> notes) {
        Map<Integer, Note> levelToNote = notes.stream().collect(Collectors.toMap(
            note -> note.whiteKeyIdx() - (clef == Clef.BASS ? bassMiddleNoteWhiteKeyIdx : trebleMiddleNoteWhiteKeyIdx),
            Function.identity()
        ));
        double levelDy = rect.height() / 8;
        double middleX = (rect.left() + rect.right()) / 2;
        double shortWidth = levelDy * 4;
        double halfShortWidth = shortWidth / 2;
        int minLevel = Math.min(-4, levelToNote.keySet().stream().min(Integer::compareTo).orElse(0));
        int maxLevel = Math.max(4, levelToNote.keySet().stream().max(Integer::compareTo).orElse(0));
        Rect noteRect = Rect.make(0, 0, levelDy * 3, levelDy * 2);
        noteRect.setMidX(middleX);
        for (int level = minLevel; level <= maxLevel; level++) {
            double levelY = rect.midY() - level * levelDy;
            if (level % 2 == 0) {
                if (-4 <= level && level <= 4) {
                    g.drawLine((int) rect.left(), (int) levelY, (int) rect.right(), (int) levelY);
                } else {
                    g.drawLine(
                        (int) (middleX - halfShortWidth), (int) levelY, (int) (middleX + halfShortWidth), (int) levelY
                    );
                }
            }
            Note curNote = levelToNote.get(level);
            if (curNote != null) {
                noteRect.setMidY(levelY);
                g.fillOval(
                    (int) noteRect.left(), (int) noteRect.top(), (int) noteRect.width(), (int) noteRect.height()
                );
            }
        }
    }
}
