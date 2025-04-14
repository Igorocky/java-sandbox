package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.RectImpl;

public interface Rect {
    static Rect make(double left, double top, double width, double height) {
        return new RectImpl(left, top, width, height);
    }

    double left();

    void setLeft(double left);

    Rect withLeft(double left);

    double midX();

    void setMidX(double midX);

    Rect withMidX(double midX);

    double right();

    void setRight(double right);

    Rect withRight(double right);

    double bottom();

    void setBottom(double bottom);

    Rect withBottom(double bottom);

    double midY();

    void setMidY(double midY);

    Rect withMidY(double midY);

    double top();

    void setTop(double top);

    Rect withTop(double top);

    double width();

    void setWidth(double width);

    Rect withWidth(double width);

    double height();

    void setHeight(double height);

    Rect withHeight(double height);

    Point leftBottom();

    void setLeftBottom(Point leftBottom);

    Rect withLeftBottom(Point leftBottom);

    Point midBottom();

    void setMidBottom(Point midBottom);

    Rect withMidBottom(Point midBottom);

    Point rightBottom();

    void setRightBottom(Point rightBottom);

    Rect withRightBottom(Point rightBottom);

    Point leftMid();

    void setLeftMid(Point leftMid);

    Rect withLeftMid(Point leftMid);

    Point midMid();

    void setMidMid(Point midMid);

    Rect withMidMid(Point midMid);

    Point rightMid();

    void setRightMid(Point rightMid);

    Rect withRightMid(Point rightMid);

    Point leftTop();

    void setLeftTop(Point leftTop);

    Rect withLeftTop(Point leftTop);

    Point midTop();

    void setMidTop(Point midTop);

    Rect withMidTop(Point midTop);

    Point rightTop();

    void setRightTop(Point rightTop);

    Rect withRightTop(Point rightTop);

    Rect copy();
}
