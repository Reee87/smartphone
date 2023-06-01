package com.example.example6;

public class Particle {
    private int x;
    private int y;
    private int coefficient;
    private boolean isDead = false;

    public Particle(int x, int y, int coefficient) {
        this.x = x;
        this.y = y;
        this.coefficient = coefficient;
    }

    public void updateCoordinates(float distance, float direction) {
        double radians = Math.toRadians(direction);
        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);
        x += (int) (coefficient * distance * cosValue);
        y -= (int) (coefficient * distance * sinValue);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
