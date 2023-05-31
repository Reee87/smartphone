package com.example.example6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        ArrayList<Object> top = new ArrayList<>();
        top.add(generateTopBound(x, y, width, height));
        top.add(isBoundTop);
        bound.add(top);

        ArrayList<Object> bottom = new ArrayList<>();
        bottom.add(generateBottomBound(x, y, width, height));
        bottom.add(isBoundBottom);
        bound.add(bottom);

        ArrayList<Object> left = new ArrayList<>();
        left.add(generateLeftBound(x, y, width, height));
        left.add(isBoundLeft);
        bound.add(left);

        ArrayList<Object> right = new ArrayList<>();
        right.add(generateRightBound(x, y, width, height));
        right.add(isBoundRight);
        bound.add(right);

        return bound;
    }

    private int[] generateTopBound(int x, int y, float width, float height) {
        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth/2;
        rect[2] = x + (int) (width*coefficient);
        rect[3] = y + lineWidth/2;

        return rect;
    }

    private int[] generateBottomBound(int x, int y, float width, float height) {
        y = y + (int) (height*coefficient);

        int[] rect = new int[4];

        rect[0] = x;
        rect[1] = y - lineWidth/2;
        rect[2] = x + (int) (width*coefficient);
        rect[3] = y + lineWidth/2;

        return rect;
    }

    private int[] generateLeftBound(int x, int y, float width, float height) {
        int[] rect = new int[4];

        rect[0] = x - lineWidth/2;
        rect[1] = y;
        rect[2] = x + lineWidth/2;
        rect[3] = y + (int) (height*coefficient);

        return rect;
    }

    private int[] generateRightBound(int x, int y, float width, float height) {
        x = x + (int) (width*coefficient);

        int[] rect = new int[4];

        rect[0] = x - lineWidth/2;
        rect[1] = y;
        rect[2] = x + lineWidth/2;
        rect[3] = y + (int) (height*coefficient);

        return rect;
    }
}
