package cz.comkop.shippingmanager;

public class ItemToLoad extends ItemToCheck {
    private final char codeName;

    public ItemToLoad(ItemToCheck checkedItem, char codeName) {
        super(checkedItem);
        this.codeName = codeName;
    }

}
