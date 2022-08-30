package cz.comkop.shipingmanager;

public class Item {
    private ItemTemplate template;
    private int area;
    private char codename;
    private boolean loadLater = false;
    private boolean turnItem90Degrees = false;
    private int x,y;






    public Item(ItemTemplate template, char codename, int x, int y) {
        this.template = template;
        area = template.getLength() * template.getWidth();
        this.codename = codename;
        this.x = x;
        this.y = y;
    }

    public Item(ItemTemplate template){
        this.template = template;
    }




    public ItemTemplate getTemplate() {
        return template;
    }

    public char getCodename() {
        return codename;
    }

    public void setCodename(char codename) {
        this.codename = codename;
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTurnItem90Degrees() {
        return turnItem90Degrees;
    }

    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }
}