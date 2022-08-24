package cz.comkop.shipingmanager;

public class Item {
    private String name = "";
    private char fullCharName;
    private int quantity = 0;//TODO setting explicit values are unnecessary
    private boolean loadLast = false;
    private boolean turnAble = false;
    private boolean turnGoods = false;

    private int width = 0;
    private int length = 0;
    private int weight = 0;

    private int x = 0;
    private int y = 0;


    public Item(String name, int width, int length, int weight, boolean turnAble) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.turnAble = turnAble;
    }


    public Item(String name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;


    }

    public Item(String name, int width, int length, int weight) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.weight = weight;


    }

    public Item(Item goods) {
        this.name = goods.name;
        this.width = goods.width;
        this.length = goods.length;
        this.weight = goods.weight;
        this.turnAble = goods.turnAble;
    }


    public String getName() {
        return name;
    }


    public int getWidth() {
        return width;
    }


    public int getLength() {
        return length;
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

    public int getWeight() {
        return weight;
    }

    public char getFullCharName() {
        return fullCharName;
    }

    public void setFullCharName(char fullCharName) {
        this.fullCharName = fullCharName;
    }

    public boolean isTurnAble() {
        return turnAble;
    }

    public boolean isTurnGoods() {
        return turnGoods;
    }

    public void setTurnGoods(boolean turnGoods) {
        this.turnGoods = turnGoods;
    }

    public boolean isLoadLast() {
        return loadLast;
    }

    public void setLoadLast(boolean loadLast) {
        this.loadLast = loadLast;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}