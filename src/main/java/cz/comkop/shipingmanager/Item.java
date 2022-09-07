package cz.comkop.shipingmanager;

public class Item {
    private final ItemTemplate template;
    private int area;
    private char codeName;
    private boolean loadLater;
    private boolean loadFirst;
    private boolean turnItem90Degrees;
    private int x, y;



    public Item(ItemTemplate template, char codeName, int x, int y) {
        this.template = template;
        area = template.getLength() * template.getWidth();
        this.codeName = codeName;
        this.x = x;
        this.y = y;
    }

    public Item(ItemTemplate template) {
        this.template = template;
    }


    public ItemTemplate getTemplate() {
        return template;
    }

    public char getCodeName() {
        return codeName;
    }

    public boolean isLoadLater() {
        return loadLater;
    }

    public void setLoadLater(boolean loadLater) {
        this.loadLater = loadLater;
    }

    public int getArea() {
        return area;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isTurnItem90Degrees() {
        return turnItem90Degrees;
    }

    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    @Override
    public String toString() {
        return template.getName() + ", width: " + template.getWidth() + " cm, length: " + template.getLength() +
                " cm, height: " + template.getHeight() + " cm, weight: " + template.getWeight() + " kg";

    }

    public boolean isLoadFirst() {
        return loadFirst;
    }

    public void setLoadFirst(boolean loadFirst) {
        this.loadFirst = loadFirst;
    }
}