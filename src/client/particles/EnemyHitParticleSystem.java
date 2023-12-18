package client.particles;

import java.awt.*;
import java.util.Random;

public class EnemyHitParticleSystem extends ParticleSystem{
    private Random rnd;
    private Color startingParticleColor;
    private int maxParticleDistance;
    private int startingParticleSize;

    private int startingParticleCount;

    public EnemyHitParticleSystem(float x, float y, int startingParticleCount, Random rnd, Color startingParticleColor, int startingParticleSize, int maxParticleDistance){
        super(x, y);
        this.rnd = rnd;
        this.startingParticleColor = startingParticleColor;
        this.startingParticleSize = startingParticleSize;
        this.maxParticleDistance = maxParticleDistance;
        this.startingParticleCount = startingParticleCount;
        initializeParticles();
    }

    @Override
    public void initializeParticles() {
        for(int i = 0; i < startingParticleCount; i++){
            float pX = getX() + (rnd.nextFloat() - 0.5f) * 20;
            float pY = getY() + (rnd.nextFloat() - 0.5f) * 20;
            float sX = (float)(rnd.nextInt(160)- 80);
            int sXsign = (int)Math.signum(sX);
            sX = MathUtils.clamp(sX * sXsign, 30, 80) * sXsign;
            float sY = (float)(rnd.nextInt(160)- 80);
            int sYsign = (int)Math.signum(sY);
            sY = MathUtils.clamp(sY * sYsign, 30, 80) * sYsign;
            addParticle(new Particle(pX, pY, sX, sY, startingParticleSize, startingParticleColor));
        }
    }

    @Override
    void updateSelf(double deltaTime) {

    }

    @Override
    void move(Particle p, double deltaTime) {
        p.move(deltaTime);
    }

    @Override
    void updateColors(Particle p, double deltaTime) {
        int newAlpha = (int)(MathUtils.remapTo10(MathUtils.calculateDistance(p.getX(), p.getY(), getX(), getY()), 0, maxParticleDistance) * 230);
        newAlpha = (int)MathUtils.clamp(newAlpha, 0, 255);
        Color oldColor = p.getColor();
        p.setColor(new Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), newAlpha));
    }

    @Override
    void updateSize(Particle p, double deltaTime) {
        int newSize = (int)(MathUtils.remapTo10(MathUtils.calculateDistance(p.getX(), p.getY(), getX(), getY()), 0, maxParticleDistance) * startingParticleSize);
        newSize = (int)MathUtils.clamp(newSize, 1, startingParticleSize);
        p.setSize(newSize);
    }
    @Override
    boolean isParticleDone(Particle p) {
        return p.getColor().getAlpha() <= 0;
    }
}
