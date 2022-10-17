package cz.comkop.shippingmanager;

import lombok.Getter;
@Getter
public class ItemToCheck extends Item{

    private Coordinates coordinates;
    private boolean turnItem90Degrees;
    private boolean checkFinished;


    public ItemToCheck(ItemTemplate template) {
        super(template);
    }
    public ItemToCheck(ItemToCheck checkedItem) {
        super(checkedItem.getTemplate());
    }

    public void setTurnItem90Degrees(boolean turnItem90Degrees) {
        this.turnItem90Degrees = turnItem90Degrees;
    }

    public void setCheckFinished(boolean checkFinished) {
        this.checkFinished = checkFinished;
    }


    public int getCoordinates(Coordinates.Type type) {
        return coordinates.getCoordinates(type);
    }
}
