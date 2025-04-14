package org.igye.sandbox.music.notes.impl;


import org.igye.sandbox.music.notes.KeyboardCmp;
import org.igye.sandbox.music.notes.NoteUtils;
import org.igye.sandbox.music.notes.Rect;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class KeyboardCmpImpl implements KeyboardCmp {
    private final NoteUtils noteUtils;
    private Rect keyboardRect;
    private List<Rect> whiteKeys;
    private List<Rect> blackKeys;

    public KeyboardCmpImpl(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
    }

    private void recalcKeyRects(Rect keyboardRect) {
        this.keyboardRect = keyboardRect;
        double whiteKeyWidth = keyboardRect.width() / noteUtils.numOfWhiteKeys();
        double blackKeyWidth = whiteKeyWidth * (1.1 / 2.3);
        double blackKeyHeight = keyboardRect.height() * (10.0 / 15.0);
        whiteKeys = new ArrayList<>();
        for (int i = 0; i < noteUtils.numOfWhiteKeys(); i++) {
            whiteKeys.add(Rect.make(
                i * whiteKeyWidth + keyboardRect.left(),
                keyboardRect.top(),
                whiteKeyWidth,
                keyboardRect.height()
            ));
        }
        blackKeys = new ArrayList<>();
        for (int i = 0; i < noteUtils.numOfKeys(); i++) {
            if (!noteUtils.isWhiteKey(i)) {
                Rect leftWhiteKey = whiteKeys.get(noteUtils.noteToWhiteKeyIdx(i - 1));
                Rect blackKey = Rect.make(0, 0, blackKeyWidth, blackKeyHeight);
                blackKey.setMidTop(leftWhiteKey.rightTop());
                blackKeys.add(blackKey);
            }
        }
    }

    @Override
    public void render(Graphics g, Rect keyboardRect) {
        if (!keyboardRect.equals(this.keyboardRect)) {
            recalcKeyRects(keyboardRect);
        }
        Color whiteKeyColor = new Color(255, 250, 226);
        Color blackKeyColor = new Color(70, 70, 70);
        for (Rect whiteKey : whiteKeys) {
            g.setColor(whiteKeyColor);
            int whiteKeyLeft = (int) whiteKey.left();
            int whiteKeyTop = (int) whiteKey.top();
            g.fillRect(whiteKeyLeft, whiteKeyTop, (int) whiteKey.width(), (int) whiteKey.height());
            g.setColor(blackKeyColor);
            g.drawLine(whiteKeyLeft, whiteKeyTop, whiteKeyLeft, (int) keyboardRect.bottom());
        }
        g.setColor(blackKeyColor);
        for (Rect blackKey : blackKeys) {
            g.fillRect((int) blackKey.left(), (int) blackKey.top(), (int) blackKey.width(), (int) blackKey.height());
        }
        g.setColor(blackKeyColor);
        g.drawRect(
            (int) keyboardRect.left(), (int) keyboardRect.top(), (int) keyboardRect.width(), (int) keyboardRect.height()
        );
    }
}