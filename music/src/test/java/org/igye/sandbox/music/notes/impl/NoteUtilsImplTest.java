package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.NoteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NoteUtilsImplTest {
    private final NoteUtils noteUtils = new NoteUtilsImpl();

    @Test
    void noteToStr() {
        Assertions.assertEquals("0A", noteUtils.noteToStr(0));
        Assertions.assertEquals("0Bb", noteUtils.noteToStr(1));
        Assertions.assertEquals("0B", noteUtils.noteToStr(2));
        Assertions.assertEquals("1C", noteUtils.noteToStr(3));
        Assertions.assertEquals("1G", noteUtils.noteToStr(10));
        Assertions.assertEquals("2C", noteUtils.noteToStr(15));
    }

    @Test
    void strToNote() {
        Assertions.assertEquals(0, noteUtils.strToNote("0A"));
        Assertions.assertEquals(1, noteUtils.strToNote("0A#"));
        Assertions.assertEquals(1, noteUtils.strToNote("0Bb"));
        Assertions.assertEquals(2, noteUtils.strToNote("0B"));
        Assertions.assertEquals(3, noteUtils.strToNote("1C"));
        Assertions.assertEquals(10, noteUtils.strToNote("1G"));
        Assertions.assertEquals(15, noteUtils.strToNote("2C"));
    }

}