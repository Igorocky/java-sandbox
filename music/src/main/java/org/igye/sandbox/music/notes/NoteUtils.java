package org.igye.sandbox.music.notes;

public interface NoteUtils {
    int numOfKeys();

    int numOfWhiteKeys();

    boolean isWhiteKey(int note);

    String noteToStr(int note);

    int strToNote(String str);

    int noteToWhiteKeyIdx(int note);

}
