package com.example.example6;

import androidx.annotation.NonNull;

public class Particle {
    private int x;
    private int y;
    private boolean isDead = false;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void updateCoordinates(int[] delta) {
        x += delta[0];
        y += delta[1];
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
