package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReadyToLoadItemList {
    List<ReadyToLoadItem> readyToLoadItems = new ArrayList<>();

    public List<ReadyToLoadItem> getReadyToLoadItems() {
        return readyToLoadItems;
    }

    public void addItems(RequiredItemList requiredItems, TrailerTemplate trailer) {
        for (RequiredItemList.RequiredItem ri : requiredItems.getRequiredItems()) {
            for (int i = 0; i < ri.getQuantity(); i++) {
                readyToLoadItems.add(new ReadyToLoadItem(ri.getItemTemplate()));
            }
        }
        sortItemsByArea();
        createPack(trailer, requiredItems);
    }

    public void sortItemsByArea() {
        readyToLoadItems.stream().sorted(Comparator.comparing(ReadyToLoadItemList.ReadyToLoadItem::getArea).reversed()).collect(Collectors.toList());
    }

    public void createPack(TrailerTemplate trailer, RequiredItemList requiredItems) {
        int rest = 0;
        int pack = 0;
        int quantity = 0;
        for (int i = 0; i < readyToLoadItems.size(); ) {
            pack = trailer.getWidth() / readyToLoadItems.get(i).getItemTemplate().getWidth();
            quantity = requiredItems.getForTemplate(readyToLoadItems.get(i).getItemTemplate()).getQuantity();
            rest = quantity % pack;
            for (int j = i; j < i + quantity; j++) {
                if (j < i + rest) {
                    readyToLoadItems.get(i).setLoadLast(true);
                }
            }
            i += quantity;
        }
    }

    public class ReadyToLoadItem {
        private ItemTemplate itemTemplate;
        private int area;
        private boolean loadLast;
        private boolean turnItem;

        public ReadyToLoadItem(ItemTemplate template) {
            this.itemTemplate = template;
            area = template.getWidth() * template.getLength();
        }

        public int getArea() {
            return area;
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

        public ItemTemplate getItemTemplate() {
            return itemTemplate;
        }
    }


}
