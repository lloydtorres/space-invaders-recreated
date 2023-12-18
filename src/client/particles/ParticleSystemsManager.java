package client.particles;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Vector;

public class ParticleSystemsManager {
    private List<ParticleSystem> systems;
    public ParticleSystemsManager(){
        systems = new Vector<>();
    }
    
    public synchronized void addSystem(ParticleSystem system){
        systems.add(system);
    }
    
    public synchronized void removeSystems(List<ParticleSystem> stms){
        systems.removeAll(stms);
    }
    
    public synchronized void update(double deltaTime){
        List<ParticleSystem> toRemove = new ArrayList<>();
        for (ParticleSystem system :
                systems) {
            if(system.isFinished()){
                toRemove.add(system);
                continue;
            }
            system.update(deltaTime);
        }
        removeSystems(toRemove);

    }
    
    public synchronized void draw(Graphics2D graphics){
        for (ParticleSystem system :
                systems) {
            system.draw(graphics);
        }
    }
    public int getCount(){
        return systems.size();
    }
    
    
}
