package com.example.example6;

import java.util.ArrayList;

public class Particles {
    private ArrayList<Particle> particles;
    private int length;

    public Particles() {
        this.particles = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        length = particles.size();
    }

    public void move(float distance, float direction) {
        for (Particle particle : particles) {
            particle.updateCoordinates(distance, direction);
        }
    }

    public void resample() {
        for (Particle particle : particles) {
            if (particle.isDead()) {
                particles.remove(particle);
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
