package server.entities;

public class Placeholder implements Cloneable {
    String text;
    int number;

    public Placeholder(String text, int number) {
        this.text = text;
        this.number = number;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
            return new Placeholder(this.text, this.number);
        }
    }
}
