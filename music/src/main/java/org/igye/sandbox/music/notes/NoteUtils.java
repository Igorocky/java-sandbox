package org.igye.sandbox.music.notes;

import java.awt.Graphics;
import java.util.Collection;

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

    void renderNotes(Graphics g, Rect rect, Clef clef, Collection<Note> notes);
}
