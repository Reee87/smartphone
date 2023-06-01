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
    private ArrayList<int[]> points;

    public Parallelogram(int topLeftX, int topLeftY, int bottomLeftX, int bottomLeftY, int width, int lineWidth) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.width = width;
        this.lineWidth = lineWidth;
        this.path = new ArrayList<>();
        this.points = new ArrayList<>();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Path left = drawLeft();
        Path right = drawRight();
        canvas.drawPath(left, paint);
        canvas.drawPath(right, paint);
        path.add(left);
        path.add(right);
        ArrayList<int[]> leftPoints = generatePoints(left);
        ArrayList<int[]> rightPoints = generatePoints(right);
        points.addAll(leftPoints);
        points.addAll(rightPoints);
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

    private ArrayList<int[]> generatePoints(Path p) {
        ArrayList<int[]> points = new ArrayList<>();
        PathMeasure pathMeasure = new PathMeasure(p, false);
        float pathLength = pathMeasure.getLength();

        float[] coordinates = new float[2];
        for (float distance = 0; distance < pathLength/2; distance += 5) {
            pathMeasure.getPosTan(distance, coordinates, null);
            int[] point = new int[2];
            point[0] = (int) coordinates[0];
            point[1] = (int) coordinates[1];
            points.add(point);
        }

        return points;
    }

    public ArrayList<Path> getPath() {
        return path;
    }

    public ArrayList<int[]> getPoints() {
        return points;
    }
}
