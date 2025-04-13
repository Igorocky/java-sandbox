package org.igye.sandbox.music.notes;

public interface NoteUtils {
    boolean isWhiteKey(int note);

    String noteToStr(int note);

    int strToNote(String str);

}
