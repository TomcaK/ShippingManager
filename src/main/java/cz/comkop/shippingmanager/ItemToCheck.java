package cz.comkop.shippingmanager;

import lombok.Getter;
@Getter
public class ItemToCheck extends Item{

    private Area area;
    private boolean turnItem90Degrees;



    public ItemToCheck(Item item, int x, int y) {
        super(item.getTemplate());
        area = new Area(this,x,y);
    }

    public ItemToCheck(ItemToCheck item) {
        super(item.getTemplate());
        area = new Area(item.area);
    }


    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public Area.Coordinates[] getAreaCoordinates() {
        return area.getCoordinates();
    }
}
