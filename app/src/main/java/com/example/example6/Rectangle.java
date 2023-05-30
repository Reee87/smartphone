package com.example.example6;

import java.util.ArrayList;

public class Rectangle {
    int coefficient;
    int lineWidth;

    public Rectangle(int coefficient, int lineWidth) {
        this.coefficient = coefficient;
        this.lineWidth = lineWidth;
    }

    public ArrayList<int[]> generateBound(int x, int y, float width, float height,
                                          boolean generateTop, boolean generateBottom,
                                          boolean generateLeft, boolean generateRight) {
        ArrayList<int[]> bound = new ArrayList<>();

        if (generateTop) bound.add(generateTopBound(x, y, width, height));
        if (generateBottom) bound.add(generateBottomBound(x, y, width, height));
        if (generateLeft) bound.add(generateLeftBound(x, y, width, height));
        if (generateRight) bound.add(generateRightBound(x, y, width, height));

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
