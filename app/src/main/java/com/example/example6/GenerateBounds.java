package com.example.example6;

import java.util.ArrayList;

public class GenerateBounds {
    ArrayList<Object> bounds;
    int coefficient;
    int lineWidth;

    public GenerateBounds(int startX, int startY, int coefficient, int lineWidth) {
        this.bounds = new ArrayList<>();
        this.coefficient = coefficient;
        this.lineWidth = lineWidth;

        Rectangle rectangle = new Rectangle(coefficient, lineWidth);

        int x, y;

        // Cell 19
        x = startX - (int) (1.79*coefficient/2);
        y = startY - (int) (2.73*coefficient/2);
        bounds.addAll(rectangle.generateBound(x, y, (float) 1.79, (float) 2.73,
                false, false, true, true));

        // Cell 11
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22));
        bounds.addAll(rectangle.generateBound(x, y, (float) 1.24, (float) 3.81,
                false, false, true, true));

        // Cell 10
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81));
        bounds.addAll(rectangle.generateBound(x, y, (float) 1.24, (float) 3.81,
                false, false, true, true));

        // Cell 12
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81));
        bounds.addAll(rectangle.generateBound(x, y, (float) 3.57, (float) 2.75,
                false, false, false, true));

        bounds.add(rectangle.generateTopBound(x+(int)(coefficient*(1.24)), y, (float) (3.57-1.24), true));
        bounds.add(rectangle.generateLeftBound(x, y, (float) (3.26+2.75-1.56-2.54), true));

        // Cell 9
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75));
        bounds.addAll(rectangle.generateBound(x, y, (float) 3.57, (float) 2.75,
                false, false, false, true));

        bounds.add(rectangle.generateLeftBound(x, y+(int) (coefficient*(2.75-(3.26+2.75-1.56-2.54))),
                (float) (3.26+2.75-1.56-2.54), true));
        bounds.add(rectangle.generateBottomBound(x+(int)(coefficient*(1.24)), y, (float) (3.57-1.24), (float) 2.75, true));

        // Cell 13
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75));
        bounds.addAll(rectangle.generateBound(x, y, (float) 3.57, (float) 3.26,
                false, true, false, true));

        bounds.add(rectangle.generateLeftBound(x, y+(int)(coefficient*(3.26-1.56)), (float) 1.56, true));

        // Cell 8
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26));
        bounds.addAll(rectangle.generateBound(x, y, (float) 3.57, (float) 3.26,
                true, false, false, true));

        bounds.add(rectangle.generateLeftBound(x, y, (float) 1.56, true));

        // Cell 14
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, true, false, false));

        // Cell 3
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, false, false, false));

        bounds.add(rectangle.generateBottomBound(x, y, (float) (4.80-0.99)/2, (float) 2.54, true));
        bounds.add(rectangle.generateBottomBound(x+(int) (coefficient*((4.8-0.99)/2+0.99)), y,
                (float) (4.80-0.99)/2, (float) 2.54, true));

        // Cell 15
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, true, false, false));

        // Cell 2
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, false, false, false));

        bounds.add(rectangle.generateBottomBound(x, y, (float) (4.8-1.01)/2, (float) 2.54, true));
        bounds.add(rectangle.generateBottomBound(x+(int) (coefficient*((4.8-1.01)/2+1.01)), y,
                (float) (4.8-1.01)/2, (float) 2.54, true));

        // Cell 16
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, true, true, false));

        // Cell 1
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.80, (float) 2.54,
                true, true, true, false));

        // Cell 7
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+(4.80-4.32)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        bounds.addAll(rectangle.generateBound(x, y, (float) 4.32, (float) 1.79,
                false, true, true, true));

        // Cell 4, 5, 6
        x = startX + (int) (coefficient*(1.79/2-2.3));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        bounds.addAll(rectangle.generateBound(x, y, (float) 2.30, (float) 4.30,
                false, true, true, true));

        // Cell 17
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8-(1.79-1.24)));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81));
        bounds.add(rectangle.generateBottomBound(x, y, (float) (1.79-1.24), (float) 3.81, true));

        // Cell 18
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8-(1.79-1.24)));
        y = startY + (int) (coefficient*(2.73/2+4.22));
        bounds.add(rectangle.generateTopBound(x, y, (float) (1.79-1.24), true));

    }

    public ArrayList<Object> getBounds() {
        return bounds;
    }
}
