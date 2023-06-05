package com.example.example6;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;

    private int cellNum;

    public Rectangle(int x, int y, int width, int height, int cellNum) {
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

    public int getTopLeftX() {
        return getX();
    }

    public int getTopLeftY() {
        return getY();
    }

    public int getBottomLeftX() {
        return getX();
    }

    public int getBottomLeftY() {
        return getY() + height;
    }

    public int getTopRightX() {
        return getX() + width;
    }

    public int getTopRightY() {
        return getY();
    }

    public int getBottomRightX() {
        return getX() + width;
    }

    public int getBottomRightY() {
        return getY() + height;
    }
}
