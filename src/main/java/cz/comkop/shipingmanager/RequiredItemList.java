package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RequiredItemList {
 List<RequiredItem> requiredItems = new ArrayList<>();

    public static class RequiredItem {
        private final ItemTemplate itemTemplate;
        private int quantity;

        public RequiredItem(ItemTemplate template, int count) {
            this.itemTemplate = template;
            this.quantity = count;
        }

        public void setCount(int count) {
            quantity = count;
        }

        public ItemTemplate getItemTemplate() {
            return itemTemplate;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public void setItemCount(ItemTemplate template, int count) {
        Optional<RequiredItem> requiredItem = requiredItems.stream().filter(ri -> ri.itemTemplate == template).findFirst();
        if (requiredItem.isPresent()) {
            requiredItem.get().setCount(count);
        } else {
            requiredItems.add(new RequiredItem(template, count));
        }
    }

    public RequiredItem getForTemplate(ItemTemplate temp){
        Optional<RequiredItem> firstItem = requiredItems.stream().filter(p -> p.itemTemplate == temp).findFirst();
        if(firstItem.isPresent()){
            return firstItem.get();
        }else{
            throw new RuntimeException("Item not found");
        }
    }

    public List<RequiredItem> getRequiredItems(){
        return Collections.unmodifiableList(requiredItems);
    }
}
