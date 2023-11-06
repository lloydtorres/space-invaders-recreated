package server.entities;

import common.EntityType;
 
public class EnemyServerEntity extends ServerEntity implements Cloneable {
    private int pointWorth;

    public EnemyServerEntity(float X, float Y, int pointWorth){
        super(EntityType.ENEMY, X, Y, 37, 24, 18, 24);
        this.pointWorth = pointWorth;
    }

    public EnemyServerEntity(EnemyServerEntity e){
        super(EntityType.ENEMY, e.getX(), e.getY(), e.getWidth(), e.getHeight(), e.getXSpeed(), e.getYSpeed());
        this.pointWorth = e.pointWorth;
    }

    public void setPointWorth(int pointWorth) {
        this.pointWorth = pointWorth;
    }

    public int getPointWorth() {
        return pointWorth;
    }

    public EnemyServerEntity shallowCopy(){
        try{
            return (EnemyServerEntity) this.clone();
        } catch(CloneNotSupportedException ex){
            ex.printStackTrace();
            return this;
        }
    }
    
    public EnemyServerEntity deepCopy(){
        return new EnemyServerEntity(this);
    }
}
