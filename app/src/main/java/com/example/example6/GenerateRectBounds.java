package com.example.example6;

import java.util.ArrayList;

public class GenerateRectBounds {
    ArrayList<Object> bounds;
    int coefficient;
    int lineWidth;

    public GenerateRectBounds(int startX, int startY, int coefficient, int lineWidth) {
        this.bounds = new ArrayList<>();
        this.coefficient = coefficient;
        this.lineWidth = lineWidth;

        RectBounds rectBounds = new RectBounds(startX, startY, coefficient);
        Rectangle rectangle = new Rectangle(lineWidth);

        ArrayList<RectBound> rectangles = rectBounds.getRectangles();
        RectBound rectBound;

        int x, y;

        // Cell 19
        rectBound = rectangles.get(14);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, false, true, true));

        // Cell 11
        rectBound = rectangles.get(8);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, false, true, true));

        // Cell 10
        rectBound = rectangles.get(7);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, false, true, true));

        // Cell 12
        rectBound = rectangles.get(9);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, false, false, true));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateTopBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), true));
        bounds.add(rectangle.generateLeftBound(x, y, (int)(coefficient*(3.26+2.75-1.56-2.54)), true));

        // Cell 9
        rectBound = rectangles.get(6);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, false, false, true));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateLeftBound(x, y+(int) (coefficient*(2.75-(3.26+2.75-1.56-2.54))),
                (int)(coefficient*(3.26+2.75-1.56-2.54)), true));
        bounds.add(rectangle.generateBottomBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), (int)(coefficient*2.75), true));

        // Cell 13
        rectBound = rectangles.get(10);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, true, false, true));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateLeftBound(x, y+(int)(coefficient*(3.26-1.56)), (int)(coefficient*1.56), true));

        // Cell 8
        rectBound = rectangles.get(5);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, false, false, true));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateLeftBound(x, y, (int)(coefficient*1.56), true));

        // Cell 14
        rectBound = rectangles.get(11);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, true, false, false));

        // Cell 3
        rectBound = rectangles.get(2);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, false, false, false));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateBottomBound(x, y, (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true));
        bounds.add(rectangle.generateBottomBound(x+(int) (coefficient*((4.8-0.99)/2+0.99)), y,
                (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true));

        // Cell 15
        rectBound = rectangles.get(12);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, true, false, false));

        // Cell 2
        rectBound = rectangles.get(1);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, false, false, false));

        x = rectBound.getX();
        y = rectBound.getY();
        bounds.add(rectangle.generateBottomBound(x, y, (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true));
        bounds.add(rectangle.generateBottomBound(x+(int) (coefficient*((4.8-1.01)/2+1.01)), y,
                (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true));

        // Cell 16
        rectBound = rectangles.get(13);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, true, true, false));

        // Cell 1
        rectBound = rectangles.get(0);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                true, true, true, false));

        // Cell 7
        rectBound = rectangles.get(4);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, true, true, true));

        // Cell 4, 5, 6
        rectBound = rectangles.get(3);
        bounds.addAll(rectangle.generateBound(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight(),
                false, true, true, true));

        // Cell 17
        rectBound = rectangles.get(7);
        bounds.add(rectangle.generateBottomBound(rectBound.getX()-(int)(coefficient*(1.79-1.24)), rectBound.getY(), (int)(coefficient*(1.79-1.24)), rectBound.getHeight(), true));

        // Cell 18
        rectBound = rectangles.get(8);
        bounds.add(rectangle.generateTopBound(rectBound.getX()-(int)(coefficient*(1.79-1.24)), rectBound.getY(), (int)(coefficient*(1.79-1.24)), true));
    }

    public ArrayList<Object> getBounds() {
        return bounds;
    }
}
