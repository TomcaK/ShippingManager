package cz.comkop.shippingmanager;

public class LoadedItem extends ItemToCheck {
    private final char codeName;

    public LoadedItem(ItemToCheck checkedItem, char codeName) {
        super(checkedItem);
        this.codeName = codeName;
    }

}
