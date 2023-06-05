package com.example.example6;

import java.util.ArrayList;

public class RectBounds {
    private ArrayList<RectBound> rectangles;

    public RectBounds(int startX, int startY, int coefficient) {
        rectangles = new ArrayList<>();

        int x, y, width, height, cellNum;

        //Cell 1
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 1;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 2
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 2;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 3
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 3;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 4, 5, 6
        x = startX + (int) (coefficient*(1.79/2-2.3));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        width = (int) (coefficient*2.30);
        height = (int) (coefficient*4.30);
        cellNum = 4;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 7
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+(4.80-4.32)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        width = (int) (coefficient*4.32);
        height = (int) (coefficient*1.79);
        cellNum = 7;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 8
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26));
        width = (int) (coefficient*3.57);
        height = (int) (coefficient*3.26);
        cellNum = 8;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 9
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75));
        width = (int) (coefficient*3.57);
        height = (int) (coefficient*2.75);
        cellNum = 9;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 10
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81));
        width = (int) (coefficient*1.24);
        height = (int) (coefficient*3.81);
        cellNum = 10;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 11
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22));
        width = (int) (coefficient*1.24);
        height = (int) (coefficient*3.81);
        cellNum = 11;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 12
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81));
        width = (int) (coefficient*3.57);
        height = (int) (coefficient*2.75);
        cellNum = 12;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 13
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75));
        width = (int) (coefficient*3.57);
        height = (int) (coefficient*3.26);
        cellNum = 13;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 14
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 14;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 15
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 15;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 16
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient*4.80);
        height = (int) (coefficient*2.54);
        cellNum = 16;
        rectangles.add(new RectBound(x, y, width, height, cellNum));

        // Cell 19
        x = startX - (int) (1.79*coefficient/2);
        y = startY - (int) (2.73*coefficient/2);
        width = (int) (coefficient*1.79);
        height = (int) (coefficient*2.73);
        cellNum = 19;
        rectangles.add(new RectBound(x, y, width, height, cellNum));
    }

    public ArrayList<RectBound> getRectangles() {
        return rectangles;
    }
}
