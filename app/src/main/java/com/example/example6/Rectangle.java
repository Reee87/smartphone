package com.example.example6;

import java.util.ArrayList;

public class Rectangle {
    int coefficient;
    int lineWidth;

    public Rectangle(int coefficient, int lineWidth) {
        this.coefficient = coefficient;
        this.lineWidth = lineWidth;
    }

    public ArrayList<Object> generateBound(int x, int y, float width, float height,
                                                       boolean isBoundTop, boolean isBoundBottom,
                                                       boolean isBoundLeft, boolean isBoundRight) {
        ArrayList<Object> bound = new ArrayList<>();

        bound.add(generateTopBound(x, y, width, isBoundTop));
        bound.add(generateBottomBound(x, y, width, height, isBoundBottom));
        bound.add(generateLeftBound(x, y, height, isBoundLeft));
        bound.add(generateRightBound(x, y, width, height, isBoundRight));

        return bound;
    }

    public ArrayList<Object> generateTopBound(int x, int y, float width, boolean isBoundTop) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth/2;
        rect[2] = x + (int) (width*coefficient);
        rect[3] = y + lineWidth/2;

        ArrayList<Object> top = new ArrayList<>();
        top.add(rect);
        top.add(isBoundTop);

        return top;
    }

    public ArrayList<Object> generateBottomBound(int x, int y, float width, float height, boolean isBoundBottom) {
        y = y + (int) (height*coefficient);

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth/2;
        rect[2] = x + (int) (width*coefficient);
        rect[3] = y + lineWidth/2;

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(rect);
        bottom.add(isBoundBottom);

        return bottom;
    }

    public ArrayList<Object> generateLeftBound(int x, int y, float height, boolean isBoundLeft) {
        int[] rect = new int[4];

        rect[0] = x - lineWidth/2;
        rect[1] = y;
        rect[2] = x + lineWidth/2;
        rect[3] = y + (int) (height*coefficient);

        ArrayList<Object> left = new ArrayList<>();
        left.add(rect);
        left.add(isBoundLeft);

        return left;
    }

    public ArrayList generateRightBound(int x, int y, float width, float height, boolean isBoundRight) {
        x = x + (int) (width*coefficient);

        int[] rect = new int[4];

        rect[0] = x - lineWidth/2;
        rect[1] = y;
        rect[2] = x + lineWidth/2;
        rect[3] = y + (int) (height*coefficient);

        ArrayList<Object> right = new ArrayList<>();
        right.add(rect);
        right.add(isBoundRight);

        return right;
    }
}
