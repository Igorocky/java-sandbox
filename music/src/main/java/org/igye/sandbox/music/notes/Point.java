package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.PointImpl;

public interface Point {
    static Point make(double x, double y) {
        return new PointImpl(x, y);
    }

    double x();

    double y();
}
