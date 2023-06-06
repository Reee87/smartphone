package com.example.example6;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;

public class Parallelogram extends Shape {
    private final int topLeftX;
    private final int topLeftY;
    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int width;
    private final int lineWidth;
    private ArrayList<Path> path;

    public Parallelogram(int topLeftX, int topLeftY, int bottomLeftX, int bottomLeftY, int width, int lineWidth) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.width = width;
        this.lineWidth = lineWidth;
        this.path = new ArrayList<>();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Path left = drawLeft();
        Path right = drawRight();
        canvas.drawPath(left, paint);
        canvas.drawPath(right, paint);
        path.add(left);
        path.add(right);
    }

    private Path drawLeft() {
        Path left = new Path();
        left.moveTo(topLeftX-lineWidth, topLeftY);
        left.lineTo(bottomLeftX-lineWidth, bottomLeftY);
        left.lineTo(bottomLeftX, bottomLeftY);
        left.lineTo(topLeftX, topLeftY);
        left.close();

        return left;
    }

    private Path drawRight() {
        Path right = new Path();
        right.moveTo(topLeftX+width, topLeftY);
        right.lineTo(bottomLeftX+width, bottomLeftY);
        right.lineTo(bottomLeftX+width+lineWidth, bottomLeftY);
        right.lineTo(topLeftX+width+lineWidth, topLeftY);
        right.close();

        return right;
    }

    public ArrayList<Path> getPath() {
        return path;
    }

    public int getPWidth() {
        return width;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getBottomLeftX() {
        return bottomLeftX;
    }

    public int getBottomLeftY() {
        return bottomLeftY;
    }
}
