package cz.comkop.shippingmanager;

import lombok.Getter;

@Getter
public class LoadedItem extends ItemToCheck {
    private final char codeName;

    public LoadedItem(ItemToCheck checkedItem, char codeName) {
        super(checkedItem);
        this.codeName = codeName;
    }

}
