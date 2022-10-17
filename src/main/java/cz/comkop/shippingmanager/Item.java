package cz.comkop.shippingmanager;

import lombok.Getter;

@Getter

public class Item {
    private final ItemTemplate template;
    private int area;
    private char codeName;
    private int inPack;
    private boolean turnItem90Degrees;
    private Coordinates coordinates;


    public Item(ItemTemplate template, char codeName, Coordinates coordinates, boolean turnItem90Degrees) {
        this.template = template;
        area = template.getLength() * template.getWidth();
        this.codeName = codeName;
        this.coordinates = coordinates;
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public Item(ItemTemplate template) {
        this.template = template;
        area = template.getLength() * template.getWidth();
    }

    public Item(ItemTemplate template, boolean turnItem90Degrees) {
        this.template = template;
        area = template.getLength() * template.getWidth();
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public void setInPack(int inPack) {
        this.inPack = inPack;
    }

    @Override
    public String toString() {
        return template.getName() + ", width: " + template.getWidth() + " cm, length: " + template.getLength() +
                " cm, height: " + template.getHeight() + " cm, weight: " + template.getWeight() + " kg";
    }

    public int getCoordinates(Coordinates.Type type) {
        return coordinates.getCoordinates(type);
    }
}