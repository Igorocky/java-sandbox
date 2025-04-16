package org.igye.sandbox.music.notes.impl;

import lombok.SneakyThrows;
import org.igye.sandbox.music.notes.Clef;
import org.igye.sandbox.music.notes.Note;
import org.igye.sandbox.music.notes.NoteAccidental;
import org.igye.sandbox.music.notes.NoteUtils;
import org.igye.sandbox.music.notes.Rect;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NoteUtilsImpl implements NoteUtils {
    private static final int MAX_NOTE = 87;
    private static final int numOfKeys = MAX_NOTE + 1;
    private static final Set<Integer> WHITE_KEYS_IN_OCTAVE = Set.of(0, 2, 4, 5, 7, 9, 11);
    private static final String[] IDX_WITHIN_OCTAVE_TO_NOTE_NAME =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final Pattern TRIAD_PAT = Pattern.compile("^([ABCDEFG])([#b]?)(m?)$");

    private final Map<String, Integer> noteNameToIdxWithinOctave;
    private final int numOfWhiteKeys;
    private final Map<Integer, Integer> noteToWhiteKeyIdx;
    private final Map<Integer, Integer> whiteKeyIdxToNote;
    private final Map<Integer, Integer> noteToBlackKeyIdx;
    private final Map<Integer, Integer> blackKeyIdxToNote;
    private final int bassMiddleNoteWhiteKeyIdx;
    private final int trebleMiddleNoteWhiteKeyIdx;
    private final List<Integer> octaveFirstNotes;

    private final BufferedImage trebleClefImg;
    private final BufferedImage bassClefImg;
    private final BufferedImage sharpImg;
    private final BufferedImage flatImg;

    @SneakyThrows
    public NoteUtilsImpl() {
        Map<String, Integer> noteNames = new HashMap<>();
        for (int i = 0; i < IDX_WITHIN_OCTAVE_TO_NOTE_NAME.length; i++) {
            noteNames.put(IDX_WITHIN_OCTAVE_TO_NOTE_NAME[i], i);
        }
        noteNames.put("Cb", noteNames.get("B"));
        noteNames.put("Db", noteNames.get("C#"));
        noteNames.put("Eb", noteNames.get("D#"));
        noteNames.put("Fb", noteNames.get("E"));
        noteNames.put("Gb", noteNames.get("F#"));
        noteNames.put("Ab", noteNames.get("G#"));
        noteNames.put("Bb", noteNames.get("A#"));
        this.noteNameToIdxWithinOctave = Collections.unmodifiableMap(noteNames);

        Set<Integer> whiteKeys = new HashSet<>();
        Map<Integer, Integer> noteToWhiteKeyIdx = new HashMap<>();
        for (int n = 0; n <= MAX_NOTE; n++) {
            if (isWhiteKey(n)) {
                whiteKeys.add(n);
                noteToWhiteKeyIdx.put(n, noteToWhiteKeyIdx.size());
            }
        }
        this.noteToWhiteKeyIdx = Collections.unmodifiableMap(noteToWhiteKeyIdx);
        this.whiteKeyIdxToNote = this.noteToWhiteKeyIdx.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getValue, Map.Entry::getKey
        ));
        numOfWhiteKeys = whiteKeys.size();

        Set<Integer> blackKeys = new HashSet<>();
        Map<Integer, Integer> noteToBlackKeyIdx = new HashMap<>();
        for (int n = 0; n <= MAX_NOTE; n++) {
            if (!isWhiteKey(n)) {
                blackKeys.add(n);
                noteToBlackKeyIdx.put(n, noteToBlackKeyIdx.size());
            }
        }
        this.noteToBlackKeyIdx = Collections.unmodifiableMap(noteToBlackKeyIdx);
        this.blackKeyIdxToNote = this.noteToBlackKeyIdx.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getValue, Map.Entry::getKey
        ));

        bassMiddleNoteWhiteKeyIdx = noteToWhiteKeyIdx(strToNote("3D"));
        trebleMiddleNoteWhiteKeyIdx = noteToWhiteKeyIdx(strToNote("4B"));

        octaveFirstNotes = Stream.iterate(strToNote("1C") - 12, n -> n + 12).limit(8).toList();

        trebleClefImg = loadImage("/treble-clef.png");
        bassClefImg = loadImage("/bass-clef.png");
        sharpImg = loadImage("/sharp.png");
        flatImg = loadImage("/flat.png");
    }

    @Override
    public int numOfKeys() {
        return numOfKeys;
    }

    @Override
    public int numOfWhiteKeys() {
        return numOfWhiteKeys;
    }

    @Override
    public boolean isWhiteKey(int note) {
        if (note == 0 || note == 2) {
            return true;
        } else if (note == 1) {
            return false;
        }
        note = (note - 3) % 12;
        return WHITE_KEYS_IN_OCTAVE.contains(note);
    }

    @Override
    public String noteToStr(int note) {
        if (note == 0) {
            return "0A";
        }
        if (note == 1) {
            return "0Bb";
        }
        if (note == 2) {
            return "0B";
        }
        note = note - 3;
        return (note / 12 + 1) + IDX_WITHIN_OCTAVE_TO_NOTE_NAME[note % 12];
    }

    @Override
    public int strToNote(String str) {
        int octave = Integer.parseInt(str.substring(0, 1));
        String noteName = str.substring(1);
        Integer idxWithinOctave = noteNameToIdxWithinOctave.get(noteName);
        int note = octave * 12 + idxWithinOctave - 9;
        if (note < 0 || MAX_NOTE < note) {
            throw new RuntimeException("note < 0 || MAX_NOTE < note");
        }
        return note;
    }

    @Override
    public int noteToWhiteKeyIdx(int note) {
        return noteToWhiteKeyIdx.get(note);
    }

    @Override
    public int whiteKeyIdxToNote(int idx) {
        return whiteKeyIdxToNote.get(idx);
    }

    @Override
    public int blackKeyIdxToNote(int idx) {
        return blackKeyIdxToNote.get(idx);
    }

    @Override
    public Note intToNote(int intNote, NoteAccidental signature) {
        if (isWhiteKey(intNote)) {
            return Note.make(noteToWhiteKeyIdx(intNote), NoteAccidental.NONE);
        } else {
            return switch (signature) {
                case FLAT -> Note.make(noteToWhiteKeyIdx(intNote + 1), signature);
                case SHARP -> Note.make(noteToWhiteKeyIdx(intNote - 1), signature);
                case NONE -> throw new RuntimeException("signature == NONE");
            };
        }
    }

    @Override
    public Optional<String> triadToStr(List<Integer> notes) {
        if (notes.size() != 3) {
            return Optional.empty();
        }
        Integer minNote = octaveFirstNotes.getFirst();
        int shift = -minNote;
        List<Integer> normalizedNodes = new ArrayList<>(
            notes.stream().map(n -> (n + shift) % 12).sorted().toList()
        );

        int cnt = 2;
        while (normalizedNodes.getLast() - normalizedNodes.getFirst() != 7 && cnt > 0) {
            normalizedNodes.add(normalizedNodes.getFirst() + 12);
            normalizedNodes.removeFirst();
            cnt--;
        }
        int d1 = normalizedNodes.get(1) - normalizedNodes.get(0);
        if (normalizedNodes.getLast() - normalizedNodes.getFirst() != 7 || (d1 != 3 && d1 != 4)) {
            return Optional.empty();
        }

        return Optional.of(
            IDX_WITHIN_OCTAVE_TO_NOTE_NAME[normalizedNodes.getFirst() % 12] + (d1 == 3 ? "m" : "")
        );
    }

    @Override
    public List<Integer> strToTriad(String triadStr, int baseOctave) {
        return List.of();
    }

    @Override
    public void renderStaff(Graphics2D g, Rect rect, Clef clef, List<List<Note>> notes) {
        renderVerticalNoteGrp(g, rect, clef, Collections.emptyList());
        renderClef(g, rect, clef);
        rect.setLeft(rect.right());
        for (List<Note> verticalNoteGrp : notes) {
            renderVerticalNoteGrp(g, rect, clef, verticalNoteGrp);
            rect.setLeft(rect.right());
        }
    }

    public void renderVerticalNoteGrp(Graphics2D g, Rect rect, Clef clef, List<Note> notes) {
        Map<Integer, Note> levelToNote = notes.stream().collect(Collectors.toMap(
            note -> note.whiteKeyIdx() - (clef == Clef.BASS ? bassMiddleNoteWhiteKeyIdx : trebleMiddleNoteWhiteKeyIdx),
            Function.identity()
        ));
        double levelDy = rect.height() / 8;
        double middleX = (rect.left() + rect.right()) / 2;
        double shortWidth = levelDy * 4;
        double halfShortWidth = shortWidth / 2;
        int minLevel = Math.min(-4, levelToNote.keySet().stream().min(Integer::compareTo).orElse(0));
        int maxLevel = Math.max(4, levelToNote.keySet().stream().max(Integer::compareTo).orElse(0));
        Rect noteRect = Rect.make(0, 0, levelDy * 3, levelDy * 2);
        noteRect.setMidX(middleX);
        for (int level = minLevel; level <= maxLevel; level++) {
            double levelY = rect.midY() - level * levelDy;
            if (level % 2 == 0) {
                if (-4 <= level && level <= 4) {
                    g.drawLine((int) rect.left(), (int) levelY, (int) rect.right(), (int) levelY);
                } else {
                    g.drawLine(
                        (int) (middleX - halfShortWidth), (int) levelY, (int) (middleX + halfShortWidth), (int) levelY
                    );
                }
            }
            Note curNote = levelToNote.get(level);
            if (curNote != null) {
                noteRect.setMidY(levelY);
                g.fillOval(
                    (int) noteRect.left(), (int) noteRect.top(), (int) noteRect.width(), (int) noteRect.height()
                );
                if (curNote.accidental() != NoteAccidental.NONE) {
                    renderAccidental(g, noteRect, curNote.accidental());
                }
            }
        }
    }

    private void renderClef(Graphics2D g, Rect noteRect, Clef clef) {
        switch (clef) {
            case BASS: {
                Rect rect = noteRect.copy();
                rect.setHeight(rect.height() * 0.85);
                rect.setWidth(rect.height() * 622.0 / 720.0);
                rect.setTop(rect.top() - rect.height() * 0.02);
                renderImg(g, bassClefImg, rect);
                break;
            }
            case TREBLE: {
                Rect rect = noteRect.copy();
                rect.setHeight(rect.height() * 1.8);
                rect.setWidth(rect.height() * 274.0 / 724.0);
                rect.setTop(rect.top() - rect.height() * 0.2);
                renderImg(g, trebleClefImg, rect);
                break;
            }
        }
    }

    private void renderAccidental(Graphics2D g, Rect noteRect, NoteAccidental accidental) {
        switch (accidental) {
            case NONE:
                break;
            case SHARP: {
                Rect rect = noteRect.copy();
                rect.setHeight(rect.height() * 1.5);
                rect.setWidth(rect.height() * 198.0 / 256.0);
                rect.setMidY(noteRect.midY());
                rect.setRight(noteRect.left() - noteRect.width() * 0.15);
                renderImg(g, sharpImg, rect);
                break;
            }
            case FLAT: {
                Rect rect = noteRect.copy();
                rect.setHeight(rect.height() * 2);
                rect.setWidth(rect.height() * 812.0 / 1447.0);
                rect.setMidY(noteRect.midY());
                rect.setRight(noteRect.left() - noteRect.width() * 0.15);
                renderImg(g, flatImg, rect);
                break;
            }
        }
    }

    private void renderImg(Graphics2D g, BufferedImage img, Rect rect) {
        g.drawImage(
            img,
            (int) rect.left(), (int) rect.top(), (int) rect.right(), (int) rect.bottom(),
            0, 0, img.getWidth(), img.getHeight(),
            null
        );
    }

    @SneakyThrows
    private BufferedImage loadImage(String path) {
        try (InputStream is = NoteUtilsImpl.class.getResourceAsStream(path)) {
            return ImageIO.read(is);
        }
    }
}
