package client.particles;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

abstract class ParticleSystem {
    private List<Particle> particles;
    private float x, y;
    private int count;
    public ParticleSystem(float x, float y) {
        this.x = x;
        this.y = y;
        this.count = 0;
        particles = new Vector<>();
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public int getCount(){
        return count;
    }

    public void addParticle(Particle p){
        particles.add(p);
        count++;
    }
    public void removeParticles(List<Particle> prtcls){

        particles.removeAll(prtcls);
        count -= particles.size();
    }
    public abstract void initializeParticles();
    abstract void updateSelf(double deltaTime);
    public void draw(Graphics2D graphics){
        for (Particle p:
             particles) {
            p.draw(graphics);
        }
    }
    public final void update(double deltaTime){
        updateSelf(deltaTime);
        List<Particle> toRemove = new ArrayList<>();
        for (Particle p:
             particles) {
            if(isParticleDone(p)){
                toRemove.add(p);
                continue;
            }
            move(p, deltaTime);
            updateColors(p, deltaTime);
            updateSize(p, deltaTime);
        }
        if(toRemove.size() > 0)
            removeParticles(toRemove);
    }
    abstract void move(Particle p, double deltaTime);
    abstract void updateColors(Particle p, double deltaTime);
    abstract void updateSize(Particle p, double deltaTime);
    abstract boolean isParticleDone(Particle p);
    public boolean isFinished(){
        return getCount() <= 0;
    }
}
