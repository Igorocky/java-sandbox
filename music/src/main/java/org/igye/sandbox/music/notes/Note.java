package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.NoteImpl;

public interface Note {

    static Note make(int whiteKeyIdx, KeySignature signature) {
        return new NoteImpl(whiteKeyIdx, signature);
    }

    int whiteKeyIdx();

    void setWhiteKeyIdx(int whiteKeyIdx);

    Note withWhiteKeyIdx(int whiteKeyIdx);

    KeySignature signature();

    void setSignature(KeySignature signature);

    Note withSignature(KeySignature signature);

    Note copy();
}
