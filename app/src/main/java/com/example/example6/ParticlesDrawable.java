package com.example.example6;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;
import java.util.List;

public class ParticlesDrawable {
    private ArrayList<ShapeDrawable> particlesDrawable;
    private Particles particles;
    private int dotSize;

    public ParticlesDrawable(int dotSize, int coefficient, int startX, int startY, int lineWidth) {
        this.dotSize = dotSize;
        this.particles = new Particles(startX, startY, coefficient, lineWidth);
        initialize();
    }

    private void initialize() {
        particlesDrawable = new ArrayList<>();

        ArrayList<Particle> particleArrayList = particles.getParticles();

        for (Particle particle : particleArrayList) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(Color.BLUE);
            drawable.setBounds(particle.getX()-dotSize/2, particle.getY()-dotSize/2, particle.getX()+dotSize/2, particle.getY()+dotSize/2);

            particlesDrawable.add(drawable);
        }
    }

    public void draw(Canvas canvas) {
        for (ShapeDrawable shapeDrawable : particlesDrawable) {
            shapeDrawable.getPaint().setColor(Color.GREEN);
            shapeDrawable.draw(canvas);
        }
    }

    public void move(int distance, int direction) {
        particles.move(distance, direction);
    }

    public void checkCollision(List<ShapeDrawable> wallsBound, ArrayList<Parallelogram> parallelograms) {
        for (int i=0; i<particlesDrawable.size(); i++) {
            if (isCollision(wallsBound, parallelograms, particlesDrawable.get(i))) {
                particles.setDead(i);
            }
        }
    }

    public void resample() {
        particles.resample();
        synchronize();
    }

    private void synchronize() {
//        ArrayList<Particle> particleArrayList = particles.getParticles();
//        for (int i=0; i<particlesDrawable.size(); i++) {
//            particlesDrawable.get(i).setBounds(particleArrayList.get(i).getX()-dotSize/2,
//                    particleArrayList.get(i).getY()-dotSize/2,
//                    particleArrayList.get(i).getX()+dotSize/2,
//                    particleArrayList.get(i).getY()+dotSize/2);
//        }

        particlesDrawable.clear();

        ArrayList<Particle> particleArrayList = particles.getParticles();

        for (Particle particle : particleArrayList) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(Color.BLUE);
            drawable.setBounds(particle.getX()-dotSize/2, particle.getY()-dotSize/2, particle.getX()+dotSize/2, particle.getY()+dotSize/2);

            particlesDrawable.add(drawable);
        }
    }

    private boolean isCollision(List<ShapeDrawable> wallsBound, ArrayList<Parallelogram> parallelograms, ShapeDrawable drawable) {
        for(ShapeDrawable wall : wallsBound) {
            if(isCollision(wall, drawable))
                return true;
        }
        for(Parallelogram p : parallelograms) {
            ArrayList<int[]> points = p.getPoints();
            if(isCollision(points, drawable))
                return true;
        }
        return false;
    }

    private boolean isCollision(ShapeDrawable first, ShapeDrawable second) {
        Rect firstRect = new Rect(first.getBounds());
        return firstRect.intersect(second.getBounds());
    }

    private boolean isCollision(ArrayList<int[]> points, ShapeDrawable second) {
        Rect rect = new Rect(second.getBounds());
        for (int[] point : points) {
            if (rect.contains(point[0], point[1])) {
                return true;
            }
        }

        return false;
    }
}