package server.entities;

import common.EntityType;

class Placeholder implements Cloneable{
    String text;
    int number;

    public Placeholder(String text, int number){
        this.text = text;
        this.number = number;
    }

    @Override
    public Object clone(){
        try{
            return (Placeholder) super.clone();
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
            return new Placeholder(this.text, this.number);
        }
    }
}

public class ShieldFragmentServerEntity extends ServerEntity implements Cloneable{
    public Placeholder placeholder;

    public ShieldFragmentServerEntity(float X, float Y, String text, int number) {
        super(EntityType.SHIELD, X, Y, 10, 10, 0, 0);
        placeholder = new Placeholder(text, number);
    }

    public ShieldFragmentServerEntity shallowCopy(){
        try{
            return (ShieldFragmentServerEntity) this.clone();
        } catch(CloneNotSupportedException ex){
            ex.printStackTrace();
            return new ShieldFragmentServerEntity(this.getX(), this.getY(), this.placeholder.text, this.placeholder.number);
        }
    }

    public ShieldFragmentServerEntity deepCopy(){
        ShieldFragmentServerEntity copy = this.shallowCopy();
        copy.placeholder = (Placeholder) this.placeholder.clone();
        return copy;
    }
}
