package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.List;

public class LoadedItemList {

    List<LoadedItem> loadedItems = new ArrayList<>();

    public static class LoadedItem {
        private final ItemTemplate itemTemplate;
        private final String codename;
        private final int x;
        private final int y;
        private final boolean turned;


        public LoadedItem(ItemTemplate template, String codename, int x, int y, boolean turned) {
            this.itemTemplate = template;
            this.codename = codename;
            this.x = x;
            this.y = y;
            this.turned = turned;
        }

        public ItemTemplate getItemTemplate() {
            return itemTemplate;
        }

        public String getCodename() {
            return codename;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isTurned() {
            return turned;
        }
    }


}
