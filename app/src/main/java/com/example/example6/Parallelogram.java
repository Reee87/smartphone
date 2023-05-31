package com.example.example6;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
        left.moveTo(topLeftX-lineWidth/2, topLeftY);
        left.lineTo(bottomLeftX-lineWidth/2, bottomLeftY);
        left.lineTo(bottomLeftX+lineWidth/2, bottomLeftY);
        left.lineTo(topLeftX+lineWidth/2, topLeftY);
        left.close();

        return left;
    }

    private Path drawRight() {
        Path right = new Path();
        right.moveTo(topLeftX+width-lineWidth/2, topLeftY);
        right.lineTo(bottomLeftX+width-lineWidth/2, bottomLeftY);
        right.lineTo(bottomLeftX+width+lineWidth/2, bottomLeftY);
        right.lineTo(topLeftX+width+lineWidth/2, topLeftY);
        right.close();

        return right;
    }

    public ArrayList<Path> getPath() {
        return path;
    }
}
