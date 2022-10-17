package cz.comkop.shippingmanager;

public class Coordinates {
    private int x;
    private int y;
    public enum Type {
        X,Y
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    public int getCoordinates(Type type){
        return switch (type){
            case X -> x;
            case Y -> y;
        };
    }
}
