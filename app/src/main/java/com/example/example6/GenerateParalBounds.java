package com.example.example6;

import java.util.ArrayList;

public class GenerateParalBounds {
    ArrayList<Parallelogram> bounds;
    int coefficient;
    int lineWidth;

    public GenerateParalBounds(int startX, int startY, int coefficient, int lineWidth) {
        this.coefficient = coefficient;
        this.lineWidth = lineWidth;
        this.bounds = new ArrayList<>();

        int topLeftX, topLeftY, bottomLeftX, bottomLeftY;

        // Cell 10
        topLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        topLeftY = startY + (int) (coefficient*(-2.73/2-4.22));
        bottomLeftX = startX - (int) (1.79*coefficient/2);
        bottomLeftY = startY - (int) (2.73*coefficient/2);
        Parallelogram parallelogram1 = new Parallelogram(topLeftX, topLeftY, bottomLeftX, bottomLeftY, (int)(coefficient*1.79), lineWidth);
        this.bounds.add(parallelogram1);

        // Cell 11
        topLeftX = startX - (int) (1.79*coefficient/2);
        topLeftY = startY + (int) (2.73*coefficient/2);
        bottomLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        bottomLeftY = startY + (int) (coefficient*(2.73/2+4.22));
        Parallelogram parallelogram2 = new Parallelogram(topLeftX, topLeftY, bottomLeftX, bottomLeftY, (int)(coefficient*1.79), lineWidth);
        this.bounds.add(parallelogram2);
    }

    public ArrayList<Parallelogram> getBounds() {
        return bounds;
    }
}
