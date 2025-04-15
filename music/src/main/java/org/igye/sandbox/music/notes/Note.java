package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.NoteImpl;

public interface Note {

    static Note make(int whiteKeyIdx, NoteAccidental accidental) {
        return new NoteImpl(whiteKeyIdx, accidental);
    }

    int whiteKeyIdx();

    void setWhiteKeyIdx(int whiteKeyIdx);

    Note withWhiteKeyIdx(int whiteKeyIdx);

    NoteAccidental accidental();

    void setAccidental(NoteAccidental accidental);

    Note withAccidental(NoteAccidental accidental);

    Note copy();
}
