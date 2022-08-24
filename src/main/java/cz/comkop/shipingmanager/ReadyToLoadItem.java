package cz.comkop.shipingmanager;

public class ReadyToLoadItem {
    private ItemTemplate itemTemplate;
    private boolean loadLast;
    private char fullCharName;
    private boolean turnGoods;
    private int x ;
    private int y ;

    public boolean isTurnGoods() {
        return turnGoods;
    }

    public char getFullCharName() {
        return fullCharName;
    }

    public void setFullCharName(char fullCharName) {
        this.fullCharName = fullCharName;
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

}
