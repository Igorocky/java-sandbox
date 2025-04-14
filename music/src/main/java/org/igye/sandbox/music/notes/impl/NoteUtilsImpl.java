package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.NoteUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoteUtilsImpl implements NoteUtils {
    private static final int MAX_NOTE = 87;
    private static final int numOfKeys = MAX_NOTE + 1;
    private static final Set<Integer> WHITE_KEYS_IN_OCTAVE = Set.of(0, 2, 4, 5, 7, 9, 11);
    private static final String[] IDX_WITHIN_OCTAVE_TO_NOTE_NAME =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    private final Map<String, Integer> noteNameToIdxWithinOctave;
    private final Set<Integer> whiteKeys;
    private final Set<Integer> blackKeys;
    private final int numOfWhiteKeys;
    private final Map<Integer, Integer> noteToWhiteKeyIdx;

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
        for (int n = 0; n < MAX_NOTE + 1; n++) {
            if (isWhiteKey(n)) {
                whiteKeys.add(n);
                noteToWhiteKeyIdx.put(n, noteToWhiteKeyIdx.size());
            }
        }
        this.whiteKeys = Collections.unmodifiableSet(whiteKeys);
        this.noteToWhiteKeyIdx = Collections.unmodifiableMap(noteToWhiteKeyIdx);
        numOfWhiteKeys = whiteKeys.size();
        blackKeys = Stream.iterate(0, n -> n + 1)
            .limit(MAX_NOTE + 1)
            .filter(n -> !whiteKeys.contains(n))
            .collect(Collectors.toSet());
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
}
