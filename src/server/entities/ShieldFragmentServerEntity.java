package server.entities;

import common.EntityType;

class Placeholder{
    String text;
    int number;

    public Placeholder(String text, int number){
        this.text = text;
        this.number = number;
    }

    public Placeholder(Placeholder placeholder){
        this.text = placeholder.text;
        this.number = placeholder.number;
    }
}

public class ShieldFragmentServerEntity extends ServerEntity implements Cloneable{
    public Placeholder placeholder;

    public ShieldFragmentServerEntity(float X, float Y, String text, int number) {
        super(EntityType.SHIELD, X, Y, 10, 10, 0, 0);
        placeholder = new Placeholder(text, number);
    }

    public ShieldFragmentServerEntity(ShieldFragmentServerEntity e){
        super(e);
        placeholder = new Placeholder(e.placeholder);
    }

    public ShieldFragmentServerEntity shallowCopy(){
        try{
            return (ShieldFragmentServerEntity) this.clone();
        } catch(CloneNotSupportedException ex){
            ex.printStackTrace();
            return this;
        }
    }

    public ShieldFragmentServerEntity deepCopy(){
        return new ShieldFragmentServerEntity(this);
    }
}
