package org.igye.sandbox.music.notes.impl;

import org.igye.sandbox.music.notes.Point;
import org.igye.sandbox.music.notes.Rect;

import java.util.Objects;

public class RectImpl implements Rect {
    private double left;
    private double top;
    private double width;
    private double height;

    private Double midX;
    private Double right;
    private Double bottom;
    private Double midY;
    private Point leftBottom;
    private Point midBottom;
    private Point rightBottom;
    private Point leftMid;
    private Point midMid;
    private Point rightMid;
    private Point leftTop;
    private Point midTop;
    private Point rightTop;

    public RectImpl(double left, double top, double width, double height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean includes(double x, double y) {
        return left() <= x && x < right() && top() <= y && y < bottom();
    }

    @Override
    public double left() {
        return left;
    }

    @Override
    public void setLeft(double left) {
        this.left = left;
        clearCaches();
    }

    @Override
    public Rect withLeft(double left) {
        return new RectImpl(left, top, width, height);
    }

    @Override
    public double midX() {
        if (midX == null) {
            midX = left + width / 2;
        }
        return midX;
    }

    @Override
    public void setMidX(double midX) {
        left = midX - width / 2;
        clearCaches();
    }

    @Override
    public Rect withMidX(double midX) {
        return new RectImpl(midX - width / 2, top, width, height);
    }

    @Override
    public double right() {
        if (right == null) {
            right = left + width;
        }
        return right;
    }

    @Override
    public void setRight(double right) {
        left = right - width;
        clearCaches();
    }

    @Override
    public Rect withRight(double right) {
        return new RectImpl(right - width, top, width, height);
    }

    @Override
    public double bottom() {
        if (bottom == null) {
            bottom = top + height;
        }
        return bottom;
    }

    @Override
    public void setBottom(double bottom) {
        top = bottom - height;
        clearCaches();
    }

    @Override
    public Rect withBottom(double bottom) {
        return new RectImpl(left, bottom - height, width, height);
    }

    @Override
    public double midY() {
        if (midY == null) {
            midY = top + height / 2;
        }
        return midY;
    }

    @Override
    public void setMidY(double midY) {
        top = midY - height / 2;
        clearCaches();
    }

    @Override
    public Rect withMidY(double midY) {
        return new RectImpl(left, midY - height / 2, width, height);
    }

    @Override
    public double top() {
        return top;
    }

    @Override
    public void setTop(double top) {
        this.top = top;
        clearCaches();
    }

    @Override
    public Rect withTop(double top) {
        return new RectImpl(left, top, width, height);
    }

    @Override
    public double width() {
        return width;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
        clearCaches();
    }

    @Override
    public Rect withWidth(double width) {
        return new RectImpl(left, top, width, height);
    }

    @Override
    public double height() {
        return height;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
        clearCaches();
    }

    @Override
    public Rect withHeight(double height) {
        return new RectImpl(left, top, width, height);
    }

    @Override
    public Point leftBottom() {
        if (leftBottom == null) {
            leftBottom = Point.make(left(), bottom());
        }
        return leftBottom;
    }

    @Override
    public void setLeftBottom(Point leftBottom) {
        setLeft(leftBottom.x());
        setBottom(leftBottom.y());
    }

    @Override
    public Rect withLeftBottom(Point leftBottom) {
        Rect res = copy();
        res.setLeftBottom(leftBottom);
        return res;
    }

    @Override
    public Point midBottom() {
        if (midBottom == null) {
            midBottom = Point.make(midX(), bottom());
        }
        return midBottom;
    }

    @Override
    public void setMidBottom(Point midBottom) {
        setMidX(midBottom.x());
        setBottom(midBottom.y());
    }

    @Override
    public Rect withMidBottom(Point midBottom) {
        Rect res = copy();
        res.setMidBottom(midBottom);
        return res;
    }

    @Override
    public Point rightBottom() {
        if (rightBottom == null) {
            rightBottom = Point.make(right(), bottom());
        }
        return rightBottom;
    }

    @Override
    public void setRightBottom(Point rightBottom) {
        setRight(rightBottom.x());
        setBottom(rightBottom.y());
    }

    @Override
    public Rect withRightBottom(Point rightBottom) {
        Rect res = copy();
        res.setRightBottom(rightBottom);
        return res;
    }

    @Override
    public Point leftMid() {
        if (leftMid == null) {
            leftMid = Point.make(left(), midY());
        }
        return leftMid;
    }

    @Override
    public void setLeftMid(Point leftMid) {
        setLeft(leftMid.x());
        setMidY(leftMid.y());
    }

    @Override
    public Rect withLeftMid(Point leftMid) {
        Rect res = copy();
        res.setLeftMid(leftMid);
        return res;
    }

    @Override
    public Point midMid() {
        if (midMid == null) {
            midMid = Point.make(midX(), midY());
        }
        return midMid;
    }

    @Override
    public void setMidMid(Point midMid) {
        setMidX(midMid.x());
        setMidY(midMid.y());
    }

    @Override
    public Rect withMidMid(Point midMid) {
        Rect res = copy();
        res.setMidMid(midMid);
        return res;
    }

    @Override
    public Point rightMid() {
        if (rightMid == null) {
            rightMid = Point.make(right(), midY());
        }
        return rightMid;
    }

    @Override
    public void setRightMid(Point rightMid) {
        setRight(rightMid.x());
        setMidY(rightMid.y());
    }

    @Override
    public Rect withRightMid(Point rightMid) {
        Rect res = copy();
        res.setRightMid(rightMid);
        return res;
    }

    @Override
    public Point leftTop() {
        if (leftTop == null) {
            leftTop = Point.make(left(), top());
        }
        return leftTop;
    }

    @Override
    public void setLeftTop(Point leftTop) {
        setLeft(leftTop.x());
        setTop(leftTop.y());
    }

    @Override
    public Rect withLeftTop(Point leftTop) {
        Rect res = copy();
        res.setLeftTop(leftTop);
        return res;
    }

    @Override
    public Point midTop() {
        if (midTop == null) {
            midTop = Point.make(midX(), top());
        }
        return midTop;
    }

    @Override
    public void setMidTop(Point midTop) {
        setMidX(midTop.x());
        setTop(midTop.y());
    }

    @Override
    public Rect withMidTop(Point midTop) {
        Rect res = copy();
        res.setMidTop(midTop);
        return res;
    }

    @Override
    public Point rightTop() {
        if (rightTop == null) {
            rightTop = Point.make(right(), top());
        }
        return rightTop;
    }

    @Override
    public void setRightTop(Point rightTop) {
        setRight(rightTop.x());
        setTop(rightTop.y());
    }

    @Override
    public Rect withRightTop(Point rightTop) {
        Rect res = copy();
        res.setRightTop(rightTop);
        return res;
    }

    @Override
    public Rect copy() {
        return new RectImpl(left, top, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RectImpl rect = (RectImpl) o;
        return Double.compare(left, rect.left) == 0
            && Double.compare(top, rect.top) == 0
            && Double.compare(width, rect.width) == 0
            && Double.compare(height, rect.height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, top, width, height);
    }

    private void clearCaches() {
        midX = null;
        right = null;
        bottom = null;
        midY = null;
        leftBottom = null;
        midBottom = null;
        rightBottom = null;
        leftMid = null;
        midMid = null;
        rightMid = null;
        leftTop = null;
        midTop = null;
        rightTop = null;
    }
}
