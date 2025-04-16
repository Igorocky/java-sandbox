package org.igye.sandbox.music.notes;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Optional;

public interface NoteUtils {
    int numOfKeys();

    int numOfWhiteKeys();

    boolean isWhiteKey(int note);

    String noteToStr(int note);

    int strToNote(String str);

    int noteToWhiteKeyIdx(int note);

    int whiteKeyIdxToNote(int idx);

    int blackKeyIdxToNote(int idx);

    Note intToNote(int intNote, NoteAccidental signature);

    Optional<String> triadToStr(List<Integer> notes);

    Optional<List<Integer>> strToTriad(String triadStr, int baseOctave);

    void renderStaff(Graphics2D g, Rect rect, Clef clef, List<List<Note>> notes);
}
