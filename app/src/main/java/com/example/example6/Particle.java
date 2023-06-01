package com.example.example6;

public class Particle {
    private int x;
    private int y;
    private boolean isDead = false;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateCoordinates(int distance, int direction) {
        double radians = Math.toRadians(direction);
        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);
        x += (int) (distance * cosValue);
        y -= (int) (distance * sinValue);
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
