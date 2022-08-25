package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectedItemList {

    List<SelectedItem> selectedItems = new ArrayList<>();


    public List<SelectedItem> getSelectedItems() {
        return selectedItems;
    }

    public void CreateList (List < RequiredItemList > requiredItemList) {

    }

    public static class SelectedItem {
        private final ItemTemplate itemTemplate;
        private boolean loadLast;
        private boolean turnItem;


        public SelectedItem(ItemTemplate template) {
            this.itemTemplate = template;

        }

        public ItemTemplate getItemTemplate() {
            return itemTemplate;
        }

        public boolean isLoadLast() {
            return loadLast;
        }

        public void setLoadLast(boolean loadLast) {
            this.loadLast = loadLast;
        }

        public boolean isTurnItem() {
            return turnItem;
        }

        public void setTurnItem(boolean turnItem) {
            this.turnItem = turnItem;
        }
    }
}
