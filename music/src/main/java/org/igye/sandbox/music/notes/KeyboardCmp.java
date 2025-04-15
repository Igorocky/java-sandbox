package org.igye.sandbox.music.notes;

import java.awt.Graphics;

public interface KeyboardCmp {

    void render(Graphics g, Rect keyboardRect);

    Integer getClickedNote(int x, int y);
}
