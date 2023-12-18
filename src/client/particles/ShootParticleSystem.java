package client.particles;

import java.awt.*;
import java.util.Random;

public class ShootParticleSystem extends ParticleSystem{
    private Random rnd;
    private Color startingParticleColor, endParticleColor;
    private int maxParticleDistance;
    private int startingParticleSize;
    private float xVariation;
    private int startingParticleCount;

    public ShootParticleSystem(float x, float y, int startingParticleCount, Random rnd, Color startingParticleColor, Color endParticleColor, int startingParticleSize, int maxParticleDistance){
        super(x, y);
        this.rnd = rnd;
        this.startingParticleColor = startingParticleColor;
        this.endParticleColor = endParticleColor;
        this.startingParticleSize = startingParticleSize;
        this.maxParticleDistance = maxParticleDistance;
        this.startingParticleCount = startingParticleCount;
        xVariation = 0;
        initializeParticles();
    }

    @Override
    public void initializeParticles() {
        for(int i = 0; i < startingParticleCount; i++){
            float pX = getX() + (rnd.nextFloat() - 0.5f) * 10;
            float pY = getY() + (rnd.nextFloat() - 0.5f) * 10;
            float sY = (float)(rnd.nextInt(300)- 300);
            sY = MathUtils.clamp(sY, -50, -125);
            addParticle(new Particle(pX, pY, rnd.nextFloat() * 10, sY, startingParticleSize, startingParticleColor));
        }
    }

    @Override
    void updateSelf(double deltaTime) {
        xVariation += 3 * deltaTime;
    }

    @Override
    void move(Particle p, double deltaTime) {
        p.setxSpeed(p.getxSpeed() + (rnd.nextFloat() > 0.5f ? -xVariation : xVariation));
        p.move(deltaTime);
    }

    @Override
    void updateColors(Particle p, double deltaTime) {
        float blendFactor = MathUtils.remapTo10(MathUtils.calculateDistance(p.getX(), p.getY(), getX(), getY()), 0, maxParticleDistance);
        int red = (int)MathUtils.clamp( MathUtils.lerp(startingParticleColor.getRed(), endParticleColor.getRed(), blendFactor), 0, 255);
        int green = (int)MathUtils.clamp( MathUtils.lerp(startingParticleColor.getGreen(), endParticleColor.getGreen(), blendFactor), 0, 255);
        int blue = (int)MathUtils.clamp( MathUtils.lerp(startingParticleColor.getBlue(), endParticleColor.getBlue(), blendFactor), 0, 255);
        Color newColor = new Color(red, green, blue, (int)MathUtils.clamp(blendFactor * 230, 0, 255));
        p.setColor(newColor);
    }

    @Override
    void updateSize(Particle p, double deltaTime) {
        int newSize = (int)(MathUtils.remapTo10(MathUtils.calculateDistance(p.getX(), p.getY(), getX(), getY()), 0, maxParticleDistance) * startingParticleSize);
        newSize = (int)MathUtils.clamp(newSize, 3, startingParticleSize);
        p.setSize(newSize);
    }
    @Override
    boolean isParticleDone(Particle p) {
        return MathUtils.calculateDistance(p.getX(), p.getY(), getX(), getY()) >= maxParticleDistance;
    }
}
