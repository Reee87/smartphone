package com.example.example6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Particles {
    private ArrayList<Particle> particles;
    private int length;
    private float density = 0.15F;
    private int coefficient;
    private int startX;
    private int startY;
    private int lineWidth;
    private Random random;
    private static final int MOTION_NOISE = 1; // Motion model noise


    public Particles(int startX, int startY, int coefficient, int lineWidth) {
        this.particles = new ArrayList<>();
        this.coefficient = coefficient;
        this.startX = startX;
        this.startY = startY;
        this.lineWidth = lineWidth;
        this.random = new Random();
        initialize();
    }

    private void initialize() {
        int width;

        Rectangles rectangles = new Rectangles(startX, startY, coefficient);
        ArrayList<Rectangle> rectangleArrayList = rectangles.getRectangles();

        for (Rectangle rectBound : rectangleArrayList) {
            particles.addAll(generateParticles(rectBound.getX(), rectBound.getY(), rectBound.getWidth(), rectBound.getHeight()));
        }

        int topLeftX, topLeftY, bottomLeftX, bottomLeftY;
        Rectangle rectangle;

        width = (int)(coefficient*1.79);

        // Cell 17
        rectangle = rectangleArrayList.get(7);
        topLeftX = rectangle.getBottomLeftX() - (int)(coefficient*(1.79-1.24));
        topLeftY = rectangle.getBottomLeftY();

        rectangle = rectangleArrayList.get(14);
        bottomLeftX = rectangle.getTopLeftX();
        bottomLeftY = rectangle.getTopLeftY();


        particles.addAll(generateParticles(topLeftX, topLeftY, bottomLeftX, bottomLeftY, width));

        // Cell 18
        rectangle = rectangleArrayList.get(14);
        topLeftX = rectangle.getBottomLeftX();
        topLeftY = rectangle.getBottomLeftY();

        rectangle = rectangleArrayList.get(8);
        bottomLeftX = rectangle.getTopLeftX() - (int)(coefficient*(1.79-1.24));
        bottomLeftY = rectangle.getTopLeftY();

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
        int[] delta = updateCoordinates(distance, direction);
        int[] newDelta = new int[2];
        for (Particle particle : particles) {
            newDelta[0] = delta[0] + (int) (random.nextGaussian() * MOTION_NOISE);
            newDelta[1] = delta[1] + (int) (random.nextGaussian() * MOTION_NOISE);
            particle.updateCoordinates(newDelta);
        }
    }

    private int[] updateCoordinates(int distance, int direction) {
        int[] delta = new int[2];
        double radians = Math.toRadians(direction);
        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);
        delta[0] = (int) (distance * cosValue);
        delta[1] = - (int) (distance * sinValue);

        return delta;
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
        ArrayList<Particle> particles1 = (ArrayList<Particle>) particles.clone();

        for (int i=0; i<length/currentLength-1; i++) {
            particles.addAll(particles1);
        }

        currentLength = particles.size();

        for (int i=0; i<length-currentLength; i++) {
            int index = random.nextInt(currentLength);
            Particle particle = particles.get(index);
            Particle particle1 = new Particle(particle.getX(), particle.getY());
            particles.add(particle1);
        }

//        Iterator<Particle> it = particles1.iterator();
//
//        int step = length / (length - currentLength);
//        Random randomI = new Random();
//        int i = randomI.nextInt(step);
//
//        while (it.hasNext() && i<length-currentLength) {
//            Particle particle = it.next();
//            if ((i + step) % step == 0) {
//                Particle particle1 = new Particle(particle.getX(), particle.getY());
//                particles.add(particle1);
//            }
//            i += 1;
//        }
    }

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setDead(int index) {
        particles.get(index).setDead(true);
    }
}
