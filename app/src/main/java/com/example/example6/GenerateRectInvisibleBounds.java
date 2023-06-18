package com.example.example6;

import java.util.ArrayList;

public class GenerateRectInvisibleBounds {
    ArrayList<Object> bounds;
    int coefficient;
    int lineWidth;

    public GenerateRectInvisibleBounds(int startX, int startY, int coefficient, int lineWidth) {
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
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), false));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true));

        // Cell 11
        rectangle = rectangleArrayList.get(8);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), false));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true, 100));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true));

        // Cell 10
        rectangle = rectangleArrayList.get(7);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), false));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true, 100));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true));

        // Cell 12
        rectangle = rectangleArrayList.get(9);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, false, true,
                false, true, false, false));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateTopBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), true, false, true, (int) (coefficient*3.81)));
        bounds.add(rectangleBounds.generateLeftBound(x, y, (int)(coefficient*(3.26+2.75-1.56-2.54)), true));

        // Cell 9
        rectangle = rectangleArrayList.get(6);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, false, false, true,
                false, false, false, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y+(int) (coefficient*(2.75-(3.26+2.75-1.56-2.54))),
                (int)(coefficient*(3.26+2.75-1.56-2.54)), true, 35));
        bounds.add(rectangleBounds.generateBottomBound(x+(int)(coefficient*(1.24)), y, (int)(coefficient*(3.57-1.24)), (int)(coefficient*2.75), true, false, true, (int) (coefficient*3.81)));

        // Cell 13
        rectangle = rectangleArrayList.get(10);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                false, true, false, true,
                false, false, true, true));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y+(int)(coefficient*(3.26-1.56)), (int)(coefficient*1.56), true));

        // Cell 8
        rectangle = rectangleArrayList.get(5);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, false, false, true,
                true, true, false, false));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateLeftBound(x, y, (int)(coefficient*1.56), true));

        // Cell 14
        rectangle = rectangleArrayList.get(11);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), true, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));



        // Cell 3
        rectangle = rectangleArrayList.get(2);
        bounds.addAll(rectangleBounds.generateBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                true, false, false, false,
                false, false, false, false));

        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateBottomBound(x, y, (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true, 35));
        bounds.add(rectangleBounds.generateBottomBound(x+(int) (coefficient*((4.8-0.99)/2+0.99)), y,
                (int)(coefficient*(4.80-0.99)/2), (int)(coefficient*2.54), true, 35));

        // Cell 15
        rectangle = rectangleArrayList.get(12);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), true, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));

        // Cell 2
        rectangle = rectangleArrayList.get(1);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), true));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth()+4, rectangle.getHeight(), true, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), false));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));


        x = rectangle.getX();
        y = rectangle.getY();
        bounds.add(rectangleBounds.generateBottomBound(x, y, (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true, 35));
        bounds.add(rectangleBounds.generateBottomBound(x+(int) (coefficient*((4.8-1.01)/2+1.01)), y,
                (int)(coefficient*(4.8-1.01)/2), (int)(coefficient*2.54), true, 35));

        // Cell 16
        rectangle = rectangleArrayList.get(13);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), true, true, false, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, true, false));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));

        // Cell 1
        rectangle = rectangleArrayList.get(0);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), true, true, false));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, true, false, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), false));


        // Cell 7
        rectangle = rectangleArrayList.get(4);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), false));
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX()-4, rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, false, false, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))-rectangle.getHeight()));
        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true));
        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, 4));

        // Cell 4, 5, 6
//        rectangle = rectangleArrayList.get(3);
//        bounds.add(rectangleBounds.generateTopBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), false));
//        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, true, true, (int)(coefficient*(3.81+3.26+2.75-1.56-2.54))-rectangle.getHeight()));
//        bounds.add(rectangleBounds.generateLeftBound(rectangle.getX(), rectangle.getY(), rectangle.getHeight(), true));
//        bounds.add(rectangleBounds.generateRightBound(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), true, 50));

        // Cell 17
        rectangle = rectangleArrayList.get(7);
        bounds.add(rectangleBounds.generateTopBound(rectangle.getBottomLeftX()-(int)(coefficient*(1.79-1.24)), rectangle.getBottomLeftY(), (int)(coefficient*(1.79-1.24)), true, 200));

        // Cell 18
        rectangle = rectangleArrayList.get(8);
        bounds.add(rectangleBounds.generateBottomBound(rectangle.getX()-(int)(coefficient*(1.79-1.24)), rectangle.getY(), (int)(coefficient*(1.79-1.24)), 0, true, 200));
    }

    public ArrayList<Object> getBounds() {
        return bounds;
    }
}
