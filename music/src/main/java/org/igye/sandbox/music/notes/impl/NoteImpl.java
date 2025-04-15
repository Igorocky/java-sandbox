package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.Note;
import org.igye.sandbox.music.notes.NoteAccidental;

public class NoteImpl implements Note {
    private int whiteKeyIdx;
    private NoteAccidental accidental;

    public NoteImpl(int whiteKeyIdx, NoteAccidental accidental) {
        this.whiteKeyIdx = whiteKeyIdx;
        this.accidental = accidental;
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
    public NoteAccidental accidental() {
        return accidental;
    }

    @Override
    public void setAccidental(NoteAccidental accidental) {
        this.accidental = accidental;
    }

    @Override
    public Note withAccidental(NoteAccidental accidental) {
        Note res = copy();
        res.setAccidental(accidental);
        return res;
    }

    @Override
    public Note copy() {
        return new NoteImpl(whiteKeyIdx(), accidental());
    }
}
