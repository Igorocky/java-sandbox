package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.KeySignature;
import org.igye.sandbox.music.notes.Note;

public class NoteImpl implements Note {
    private int whiteKeyIdx;
    private KeySignature signature;

    public NoteImpl(int whiteKeyIdx, KeySignature signature) {
        this.whiteKeyIdx = whiteKeyIdx;
        this.signature = signature;
    }

    @Override
    public int whiteKeyIdx() {
        return whiteKeyIdx;
    }

    @Override
    public void setWhiteKeyIdx(int whiteKeyIdx) {
        this.whiteKeyIdx = whiteKeyIdx;
    }

    @Override
    public Note withWhiteKeyIdx(int whiteKeyIdx) {
        Note res = copy();
        res.setWhiteKeyIdx(whiteKeyIdx);
        return res;
    }

    @Override
    public KeySignature signature() {
        return signature;
    }

    @Override
    public void setSignature(KeySignature signature) {
        this.signature = signature;
    }

    @Override
    public Note withSignature(KeySignature signature) {
        Note res = copy();
        res.setSignature(signature);
        return res;
    }

    @Override
    public Note copy() {
        return new NoteImpl(whiteKeyIdx(), signature());
    }
}
