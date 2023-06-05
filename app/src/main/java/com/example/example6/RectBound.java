package com.example.example6;

public class RectBound {
    private int x;
    private int y;
    private int width;
    private int height;

    private int cellNum;

    public RectBound(int x, int y, int width, int height, int cellNum) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cellNum = cellNum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellNum() {
        return cellNum;
    }
}
