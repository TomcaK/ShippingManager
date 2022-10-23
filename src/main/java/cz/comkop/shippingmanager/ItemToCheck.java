package cz.comkop.shippingmanager;

import lombok.Getter;


@Getter
public class ItemToCheck extends Item {

    private Area area;
    private boolean turnItem90Degrees;


    public ItemToCheck(ItemTemplate template) {
        super(template);
        area = new Area(template);
    }

    public ItemToCheck(Item item) {
        super(item);
        area = new Area(item.getTEMPLATE());
    }

    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public boolean collision(Area... areas) {
        return this.area.collision(this.area, areas);
    }

    public void setAreaCoordinates(int x, int y) {
        area.setCoordinates(this, x, y);
    }
}
