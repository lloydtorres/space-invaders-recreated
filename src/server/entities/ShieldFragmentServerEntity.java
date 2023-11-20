package server.entities;

import common.EntityType;

public class ShieldFragmentServerEntity extends ServerEntity implements Cloneable {
    public Placeholder placeholder;

    public ShieldFragmentServerEntity(float X, float Y, Placeholder placeholder) {
        super(EntityType.SHIELD, X, Y, 10, 10, 0, 0);
        this.placeholder = placeholder;
    }

    public ShieldFragmentServerEntity shallowCopy() {
        try {
            return (ShieldFragmentServerEntity) this.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
            return new ShieldFragmentServerEntity(this.getX(), this.getY(), this.placeholder);
        }
    }

    public ShieldFragmentServerEntity deepCopy() {
        ShieldFragmentServerEntity copy = this.shallowCopy();
        copy.placeholder = (Placeholder) this.placeholder.clone();
        return copy;
    }
}
