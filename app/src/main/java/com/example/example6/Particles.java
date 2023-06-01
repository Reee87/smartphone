package com.example.example6;

import java.util.ArrayList;
import java.util.Iterator;

public class Particles {
    private ArrayList<Particle> particles;
    private int length;
    private float density = 0.05F;
    private int coefficient;
    private int startX;
    private int startY;
    int lineWidth;

    public Particles(int startX, int startY, int coefficient, int lineWidth) {
        this.particles = new ArrayList<>();
        this.coefficient = coefficient;
        this.startX = startX;
        this.startY = startY;
        this.lineWidth = lineWidth;
        initialize();
    }

    private void initialize() {
        int x, y, width, height;

        // Cell 1
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 2
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 3
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 4, 5, 6
        x = startX + (int) (coefficient*(1.79/2-2.3));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        width = (int) (coefficient * 2.30);
        height = (int) (coefficient * 4.30);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 7
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+(4.80-4.32)/2));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26+1.56+2.54));
        width = (int) (coefficient * 4.32);
        height = (int) (coefficient * 1.79);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 8
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75-3.26));
        width = (int) (coefficient * 3.57);
        height = (int) (coefficient * 3.26);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 9
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81-2.75));
        width = (int) (coefficient * 3.57);
        height = (int) (coefficient * 2.75);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 10
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(-2.73/2-4.22-3.81));
        width = (int) (coefficient * 1.24);
        height = (int) (coefficient * 3.81);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 11
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22));
        width = (int) (coefficient * 1.24);
        height = (int) (coefficient * 3.81);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 12
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81));
        width = (int) (coefficient * 3.57);
        height = (int) (coefficient * 2.75);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 13
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75));
        width = (int) (coefficient * 3.57);
        height = (int) (coefficient * 3.26);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 14
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 15
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 16
        x = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2-4.8*2));
        y = startY + (int) (coefficient*(2.73/2+4.22+3.81+2.75-(2.56-(3.26-1.56))));
        width = (int) (coefficient * 4.80);
        height = (int) (coefficient * 2.54);
        particles.addAll(generateParticles(x, y, width, height));

        // Cell 19
        x = startX - (int) (1.79*coefficient/2);
        y = startY - (int) (2.73*coefficient/2);
        width = (int) (coefficient * 1.79);
        height = (int) (coefficient * 2.73);
        particles.addAll(generateParticles(x, y, width, height));

        int topLeftX, topLeftY, bottomLeftX, bottomLeftY;

        // Cell 17
        topLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        topLeftY = startY + (int) (coefficient*(-2.73/2-4.22));
        bottomLeftX = startX - (int) (1.79*coefficient/2);
        bottomLeftY = startY - (int) (2.73*coefficient/2);
        width = (int)(coefficient*1.79);
        particles.addAll(generateParticles(topLeftX, topLeftY, bottomLeftX, bottomLeftY, width));

        // Cell 18
        topLeftX = startX - (int) (1.79*coefficient/2);
        topLeftY = startY + (int) (2.73*coefficient/2);
        bottomLeftX = startX + (int) (coefficient*(1.79/2+(4.8-2.3)/2+4.8+1.24-1.79));
        bottomLeftY = startY + (int) (coefficient*(2.73/2+4.22));
        particles.addAll(generateParticles(topLeftX, topLeftY, bottomLeftX, bottomLeftY, width));

        length = particles.size();
    }

    private ArrayList<Particle> generateParticles(int x, int y, int width, int height) {
        ArrayList<Particle> particles1 = new ArrayList<>();
        int margin = lineWidth/2+1;

        for (int i=x+margin; i<x+width-margin; i+=(int)(1/density)) {
            for (int j=y+margin; j<y+height-margin; j+=(int)(1/density)) {
                Particle particle = new Particle(i, j);
                particles1.add(particle);
            }
        }

        return particles1;
    }

    private ArrayList<Particle> generateParticles(int topLeftX, int topLeftY, int bottomLeftX, int bottomLeftY, int width) {
        ArrayList<Particle> particles1 = new ArrayList<>();

        int lineWidth = 4;
        int margin = lineWidth/2+1;

        float slop = (float) (topLeftY-bottomLeftY) / (float) (topLeftX-bottomLeftX);
        // y - bottomLeftY = slop * (x - bottomLeftX)
        int x, y;
        if (topLeftX > bottomLeftX) {
            for (int i=bottomLeftX; i<topLeftX; i+=(int)(1/density)) {
                for (int j=margin; j<width-margin; j+=(int)(1/density)) {
                    y = (int) (slop * (i - bottomLeftX) + bottomLeftY);
                    x = i + j;
                    Particle particle = new Particle(x, y);
                    particles1.add(particle);
                }
            }
        }
        else {
            for (int i=bottomLeftX; i>topLeftX; i-=(int)(1/density)) {
                for (int j=margin; j<width-margin; j+=(int)(1/density)) {
                    y = (int) (slop * (i - bottomLeftX) + bottomLeftY);
                    x = i + j;
                    Particle particle = new Particle(x, y);
                    particles1.add(particle);
                }
            }
        }


        return particles1;
    }

    public void move(int distance, int direction) {
        for (Particle particle : particles) {
            particle.updateCoordinates(distance, direction);
        }
    }

    public void resample() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (particle.isDead()) {
                iterator.remove();
            }
        }

        int currentLength = particles.size();
        for (int i=0; i<length/currentLength-1; i++) {
            ArrayList<Particle> particles1 = (ArrayList<Particle>) particles.clone();
            particles.addAll(particles1);
        }
        ArrayList<Particle> particles1 = (ArrayList<Particle>) particles.clone();
        for (int i=0; i<length-(length/currentLength)*currentLength; i++) {
            particles.add(particles1.get(i));
        }
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setDead(int index) {
        particles.get(index).setDead(true);
    }
}
