package com.example.example6;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;
import java.util.List;

public class ParticlesDrawable {
    private ArrayList<ShapeDrawable> particlesDrawable;
    private Particles particles;
    private int dotSize;
    private List<ShapeDrawable> wallsBound;
    private ArrayList<Parallelogram> parallelograms;
    private List<Rect> wallsBoundRect;
    private List<Rect> parallelogramsRect;

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
            drawable.getPaint().setColor(Color.GREEN);
            drawable.setBounds(particle.getX()-dotSize/2, particle.getY()-dotSize/2, particle.getX()+dotSize/2, particle.getY()+dotSize/2);

            particlesDrawable.add(drawable);
        }
    }

    public void draw(Canvas canvas) {
        synchronize();
        for (ShapeDrawable shapeDrawable : particlesDrawable) {
            shapeDrawable.draw(canvas);
        }
    }

    public void move(int distance, int direction) {
        particles.move(distance, direction);
        synchronize();
    }

    public void checkCollision() {
        for (int i=0; i<particlesDrawable.size(); i++) {
            if (isCollision(particlesDrawable.get(i))) {
                particles.setDead(i);
            }
        }
    }

    public void resample() {
        particles.resample();
        synchronize();
    }

    public int checkCellNum() {
        return particles.checkCellNum();
    }

    private void synchronize() {
        ArrayList<Particle> particleArrayList = particles.getParticles();
        for (int i=0; i<particlesDrawable.size(); i++) {
            particlesDrawable.get(i).setBounds(particleArrayList.get(i).getX()-dotSize/2,
                    particleArrayList.get(i).getY()-dotSize/2,
                    particleArrayList.get(i).getX()+dotSize/2,
                    particleArrayList.get(i).getY()+dotSize/2);
        }
    }

    public void setBounds(List<ShapeDrawable> wallsBound, ArrayList<Parallelogram> parallelograms) {
        this.wallsBound = wallsBound;
        this.parallelograms = parallelograms;
        convertToRect(wallsBound);
    }

    private void convertToRect(List<ShapeDrawable> wallsBound) {
        this.wallsBoundRect = new ArrayList<>();
        this.parallelogramsRect = new ArrayList<>();

        for (ShapeDrawable wall : wallsBound) {
            Rect rect = new Rect(wall.getBounds());
            wallsBoundRect.add(rect);
        }

        for(Parallelogram p : parallelograms) {
            RectF rectF1 = new RectF();
            p.getPath().get(0).computeBounds(rectF1, true);
            RectF rectF2 = new RectF();
            p.getPath().get(0).computeBounds(rectF2, true);
            rectF1.union(rectF2);
            Rect rect = new Rect();
            rectF1.roundOut(rect);
//            parallelogramsRect.add(new Rect(rect.left, rect.top, rect.right+p.getPWidth(), rect.bottom));
            parallelogramsRect.add(new Rect(0, rect.top, 1080, rect.bottom));
        }
    }

    public boolean isCollision(ShapeDrawable drawable) {
        for(ShapeDrawable wall : wallsBound) {
            if(isCollision(wall, drawable))
                return true;
        }

        for(int i=0; i<2; i++) {
            Rect rect = parallelogramsRect.get(i);
            if (isCollision(new Rect(rect.left, rect.top, rect.right, rect.bottom), drawable))
                if(isCollision(parallelograms.get(i), drawable))
                    return true;
        }

        return false;
    }

    public boolean isCollision(Parallelogram parallelogram, ShapeDrawable second) {
        int topLeftX, topLeftY, bottomLeftX, bottomLeftY, width;
        topLeftX = parallelogram.getTopLeftX();
        topLeftY = parallelogram.getTopLeftY();
        bottomLeftX = parallelogram.getBottomLeftX();
        bottomLeftY = parallelogram.getBottomLeftY();
        width = parallelogram.getPWidth();

        float slop = (float) (topLeftY-bottomLeftY) / (float) (topLeftX-bottomLeftX);
        // y - bottomLeftY = slop * (x - bottomLeftX)

        Rect rect = second.getBounds();
        int x, y, xLeftBound, xRightBound;
        x = (rect.left + rect.right)/2;
        y = (rect.top + rect.bottom)/2;
        xLeftBound = (int) ((y - bottomLeftY) / slop + bottomLeftX);
        xRightBound = xLeftBound + width;

        if (x < xLeftBound || x > xRightBound) {
            return true;
        }
        else {
            return false;
        }
    }

//    public boolean isCollision(Parallelogram parallelogram, ShapeDrawable second) {
//        int topRightX, topRightY, bottomRightX, bottomRightY, width;
//        topRightX = parallelogram.getTopRightX();
//        topRightY = parallelogram.getTopRightY();
//        bottomRightX = parallelogram.getBottomRightX();
//        bottomRightY = parallelogram.getBottomRightY();
//        width = parallelogram.getPWidth();
//
//        float slop = (float) (topRightY-bottomRightY) / (float) (topRightX-bottomRightX);
//
//        Rect rect = second.getBounds();
//        int x, y, xLeftBound, xRightBound;
//        x = (rect.left + rect.right)/2;
//        y = (rect.top + rect.bottom)/2;
//        xLeftBound = (int) ((y - bottomRightY) / slop + bottomRightX);
//        xRightBound = xLeftBound + width;
//
//        if (x < xLeftBound || x > xRightBound) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    public boolean isCollision(Rect first, ShapeDrawable second) {
        return first.intersect(second.getBounds());
    }

    public boolean isCollision(ShapeDrawable first, ShapeDrawable second) {
        Rect firstRect = new Rect(first.getBounds());
        return firstRect.intersect(second.getBounds());
    }
}