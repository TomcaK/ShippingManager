package cz.comkop.shippingmanager;

import lombok.Getter;
@Getter
public class ItemToCheck extends Item{

    private Coordinates coordinates;
    private boolean turnItem90Degrees;



    public ItemToCheck(Item item) {
        super(item.getTemplate());
    }


    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public int getCoordinates(Coordinates.Type type) {
        return coordinates.getCoordinates(type);
    }
}
