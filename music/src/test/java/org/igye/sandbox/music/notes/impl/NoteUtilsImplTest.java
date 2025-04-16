package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.NoteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    void triadToStr() {
        int base = noteUtils.strToNote("4C");
        Assertions.assertEquals(Optional.of("C"), noteUtils.triadToStr(List.of(base, base + 4, base + 7)));
        Assertions.assertEquals(Optional.of("Cm"), noteUtils.triadToStr(List.of(base, base + 3, base + 7)));

        base = noteUtils.strToNote("4C");
        Assertions.assertEquals(Optional.of("C"), noteUtils.triadToStr(List.of(base, base + 4 - 24, base + 7 - 12)));
        Assertions.assertEquals(Optional.of("Cm"), noteUtils.triadToStr(List.of(base, base + 3 + 12, base + 7 - 24)));

        base = noteUtils.strToNote("5F#");
        Assertions.assertEquals(Optional.of("F#"), noteUtils.triadToStr(List.of(base, base + 4, base + 7)));
        Assertions.assertEquals(Optional.of("F#m"), noteUtils.triadToStr(List.of(base, base + 3, base + 7)));

        base = noteUtils.strToNote("5F#");
        Assertions.assertEquals(Optional.of("F#"), noteUtils.triadToStr(List.of(base, base + 4 - 24, base + 7 - 12)));
        Assertions.assertEquals(Optional.of("F#m"), noteUtils.triadToStr(List.of(base, base + 3 + 12, base + 7 - 24)));
    }

    @Test
    void strToTriad() {
        int n1 = noteUtils.strToNote("4C");
        int n2 = noteUtils.strToNote("4E");
        int n3 = noteUtils.strToNote("4G");
        Assertions.assertEquals(Optional.of(List.of(n1, n2, n3)), noteUtils.strToTriad("C", 4));

        n1 = noteUtils.strToNote("4C");
        n2 = noteUtils.strToNote("4Eb");
        n3 = noteUtils.strToNote("4G");
        Assertions.assertEquals(Optional.of(List.of(n1, n2, n3)), noteUtils.strToTriad("Cm", 4));

        n1 = noteUtils.strToNote("3G#");
        n2 = noteUtils.strToNote("4C");
        n3 = noteUtils.strToNote("4D#");
        Assertions.assertEquals(Optional.of(List.of(n1, n2, n3)), noteUtils.strToTriad("G#", 3));

        n1 = noteUtils.strToNote("3G#");
        n2 = noteUtils.strToNote("3B");
        n3 = noteUtils.strToNote("4D#");
        Assertions.assertEquals(Optional.of(List.of(n1, n2, n3)), noteUtils.strToTriad("G#m", 3));
    }

}