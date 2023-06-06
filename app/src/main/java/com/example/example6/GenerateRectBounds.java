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

        Rectangles rectangles = new Rectangles(startX, startY, coefficient);
        RectangleBounds rectangleBounds = new RectangleBounds(lineWidth);

        ArrayList<Rectangle> rectangleArrayList = rectangles.getRectangles();
        Rectangle rectangle;

        int x, y;

        // Cell 19
        rectangle = rectangleArrayList.get(14);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, true, true));

        // Cell 11
        rectangle = rectangleArrayList.get(8);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, true, true));

        // Cell 10
        rectangle = rectangleArrayList.get(7);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, true, true));

        // Cell 12
        rectangle = rectangleArrayList.get(9);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, false, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateTopBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), true));
        bounds.add(rectangleBounds.generateLeftBound(x, y, (int)(coefficient*(3.26+2.75-1.56-2.54)), true));

        // Cell 9
        rectangle = rectangleArrayList.get(6);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, false, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y+(int) (coefficient*(2.75-(3.26+2.75-1.56-2.54))),
                (int)(coefficient*(3.26+2.75-1.56-2.54)), true));
        bounds.add(rectangleBounds.generateBottomBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), (int)(coefficient*2.75), true));

        // Cell 13
        rectangle = rectangleArrayList.get(10);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, true, false, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y+(int)(coefficient*(3.26-1.56)), (int)(coefficient*1.56), true));

        // Cell 8
        rectangle = rectangleArrayList.get(5);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, false, false, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y, (int)(coefficient*1.56), true));

        // Cell 14
        rectangle = rectangleArrayList.get(11);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, true, false, false));

        // Cell 3
        rectangle = rectangleArrayList.get(2);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, false, false, false));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateBottomBound(x, y, (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true));
        bounds.add(rectangleBounds.generateBottomBound(x+(int) (coefficient*((4.8-0.99)/2+0.99)), y,
                (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true));

        // Cell 15
        rectangle = rectangleArrayList.get(12);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, true, false, false));

        // Cell 2
        rectangle = rectangleArrayList.get(1);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, false, false, false));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateBottomBound(x, y, (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true));
        bounds.add(rectangleBounds.generateBottomBound(x+(int) (coefficient*((4.8-1.01)/2+1.01)), y,
                (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true));

        // Cell 16
        rectangle = rectangleArrayList.get(13);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, true, true, false));

        // Cell 1
        rectangle = rectangleArrayList.get(0);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, true, true, false));

        // Cell 7
        rectangle = rectangleArrayList.get(4);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, true, true, true));

        // Cell 4, 5, 6
        rectangle = rectangleArrayList.get(3);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, true, true, true));

        // Cell 17
        rectangle = rectangleArrayList.get(7);
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX()-(int)(coefficient*(1.79-1.24)), rectangle.getY(), (int)(coefficient*(1.79-1.24)), rectangle.getHeight(), true, 4));

        // Cell 18
        rectangle = rectangleArrayList.get(8);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX()-(int)(coefficient*(1.79-1.24)), rectangle.getY(), (int)(coefficient*(1.79-1.24)), true, 4));
    }

    public ArrayList<Object> getBounds() {
        return bounds;
    }
}
