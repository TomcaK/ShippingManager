package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.List;

public class ReadyToLoadItemList {
List<ReadyToLoadItem> readyToLoadItems = new ArrayList<>();

public void addItem(ItemTemplate template, boolean loadLast, boolean turnItem){
    readyToLoadItems.add(new ReadyToLoadItem(template,loadLast,turnItem));
}
    public class ReadyToLoadItem {
        private ItemTemplate itemTemplate;
        private boolean loadLast;
        private boolean turnItem;

        public ReadyToLoadItem(ItemTemplate template, boolean loadLast, boolean turnItem) {
            this.itemTemplate = template;
            this.loadLast = loadLast;
            this.turnItem = turnItem;
        }

        public boolean isTurnItem() {
            return turnItem;
        }

        public void setTurnItem(boolean turnItem) {
            this.turnItem = turnItem;
        }

        public boolean isLoadLast() {
            return loadLast;
        }

        public void setLoadLast(boolean loadLast) {
            this.loadLast = loadLast;
        }


    }
}
