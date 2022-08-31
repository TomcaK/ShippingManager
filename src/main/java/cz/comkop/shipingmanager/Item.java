package cz.comkop.shipingmanager;

public class Item {
    private ItemTemplate template;
    private int area;
    private char codeName;
    private boolean loadLater = false;
    private boolean turnItem90Degrees = false;
    private int x,y;






    public Item(ItemTemplate template, char codeName, int x, int y) {
        this.template = template;
        area = template.getLength() * template.getWidth();
        this.codeName = codeName;
        this.x = x;
        this.y = y;
    }

    public Item(ItemTemplate template){
        this.template = template;
    }




    public ItemTemplate getTemplate() {
        return template;
    }

    public char getCodeName() {
        return codeName;
    }

    public void setCodeName(char codeName) {
        this.codeName = codeName;
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

    @Override
    public String toString() {
        return  template.getName() + ", width: " + template.getWidth() + " cm, length: " + template.getLength() +
                " cm, weight: " + template.getWeight() + " kg, height: " + template.getHeight() ;

    }


}