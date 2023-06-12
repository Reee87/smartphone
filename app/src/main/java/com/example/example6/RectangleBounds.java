package com.example.example6;

import java.util.ArrayList;

public class RectangleBounds {
    int lineWidth;

    public RectangleBounds(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public ArrayList<Object> generateBound(int x, int y, int width, int height,
                                           boolean isBoundTop, boolean isBoundBottom,
                                           boolean isBoundLeft, boolean isBoundRight,
                                           boolean roundCornerA, boolean roundCornerB,
                                           boolean roundCornerC, boolean roundCornerD) {
        ArrayList<Object> bound = new ArrayList<>();

        bound.add(generateTopBound(x, y, width, isBoundTop, roundCornerA, roundCornerB));
        bound.add(generateBottomBound(x, y, width, height, isBoundBottom, roundCornerC, roundCornerD));
        bound.add(generateLeftBound(x, y, height, isBoundLeft));
        bound.add(generateRightBound(x, y, width, height, isBoundRight));

        return bound;
    }

    public ArrayList<Object> generateTopBound(int x, int y, int width, boolean isBoundTop) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth;
        rect[2] = x + width;
        rect[3] = y;

        ArrayList<Object> top = new ArrayList<>();
        top.add(rect);
        top.add(isBoundTop);

        return top;
    }

    public ArrayList<Object> generateBottomBound(int x, int y, int width, int height, boolean isBoundBottom) {
        y = y + height;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + width;
        rect[3] = y + lineWidth;

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(rect);
        bottom.add(isBoundBottom);

        return bottom;
    }

    public ArrayList<Object> generateTopBound(int x, int y, int width, boolean isBoundTop, boolean roundCornerA, boolean roundCornerB) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth;
        rect[2] = x + width;
        rect[3] = y;

        if (roundCornerA) {
            rect[0] -= lineWidth;
        }

        if (roundCornerB) {
            rect[2] += lineWidth;
        }

        ArrayList<Object> top = new ArrayList<>();
        top.add(rect);
        top.add(isBoundTop);

        return top;
    }

    public ArrayList<Object> generateBottomBound(int x, int y, int width, int height, boolean isBoundBottom, boolean roundCornerC, boolean roundCornerD) {
        y = y + height;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + width;
        rect[3] = y + lineWidth;

        if (roundCornerC) {
            rect[0] -= lineWidth;
        }

        if (roundCornerD) {
            rect[2] += lineWidth;
        }

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(rect);
        bottom.add(isBoundBottom);

        return bottom;
    }

    public ArrayList<Object> generateLeftBound(int x, int y, int height, boolean isBoundLeft) {
        int[] rect = new int[4];

        rect[0] = x - lineWidth;
        rect[1] = y;
        rect[2] = x;
        rect[3] = y + height;

        ArrayList<Object> left = new ArrayList<>();
        left.add(rect);
        left.add(isBoundLeft);

        return left;
    }

    public ArrayList generateRightBound(int x, int y, int width, int height, boolean isBoundRight) {
        x = x + width;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + lineWidth;
        rect[3] = y + height;

        ArrayList<Object> right = new ArrayList<>();
        right.add(rect);
        right.add(isBoundRight);

        return right;
    }

    public ArrayList<Object> generateTopBound(int x, int y, int width, boolean isBoundTop, int newLineWidth) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - newLineWidth;
        rect[2] = x + width;
        rect[3] = y;

        ArrayList<Object> top = new ArrayList<>();
        top.add(rect);
        top.add(isBoundTop);

        return top;
    }

    public ArrayList<Object> generateBottomBound(int x, int y, int width, int height, boolean isBoundBottom, int newLineWidth) {
        y = y + height;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + width;
        rect[3] = y + newLineWidth;

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(rect);
        bottom.add(isBoundBottom);

        return bottom;
    }

    public ArrayList<Object> generateLeftBound(int x, int y, int height, boolean isBoundLeft, int newLineWidth) {
        int[] rect = new int[4];

        rect[0] = x - newLineWidth;
        rect[1] = y;
        rect[2] = x;
        rect[3] = y + height;

        ArrayList<Object> left = new ArrayList<>();
        left.add(rect);
        left.add(isBoundLeft);

        return left;
    }

    public ArrayList generateRightBound(int x, int y, int width, int height, boolean isBoundRight, int newLineWidth) {
        x = x + width;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + newLineWidth;
        rect[3] = y + height;

        ArrayList<Object> right = new ArrayList<>();
        right.add(rect);
        right.add(isBoundRight);

        return right;
    }

    public ArrayList<Object> generateTopBound(int x, int y, int width, boolean isBoundTop, boolean roundCornerA, boolean roundCornerB, int newLineWidth) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - newLineWidth;
        rect[2] = x + width;
        rect[3] = y;

        if (roundCornerA) {
            rect[0] -= newLineWidth;
        }

        if (roundCornerB) {
            rect[2] += newLineWidth;
        }

        ArrayList<Object> top = new ArrayList<>();
        top.add(rect);
        top.add(isBoundTop);

        return top;
    }

    public ArrayList<Object> generateBottomBound(int x, int y, int width, int height, boolean isBoundBottom, boolean roundCornerC, boolean roundCornerD, int newLineWidth) {
        y = y + height;

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y;
        rect[2] = x + width;
        rect[3] = y + newLineWidth;

        if (roundCornerC) {
            rect[0] -= newLineWidth;
        }

        if (roundCornerD) {
            rect[2] += newLineWidth;
        }

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(rect);
        bottom.add(isBoundBottom);

        return bottom;
    }
}
