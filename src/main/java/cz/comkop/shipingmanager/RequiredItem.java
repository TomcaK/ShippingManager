package cz.comkop.shipingmanager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RequiredItem {

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





//    public RequiredItem getForTemplate(ItemTemplate temp) {
//        Optional<RequiredItem> firstItem = requiredItems.stream().filter(p -> p.itemTemplate == temp).findFirst();
//        if (firstItem.isPresent()) {
//            return firstItem.get();
//        } else {
//            throw new RuntimeException("Item not found");
//        }
//
//    }

    private void setItemCount(ItemTemplate template, int count, ListOfItems listOfItems) {
        Optional<RequiredItem> requiredItem = listOfItems.getRequiredItems().stream().filter(ri -> ri.itemTemplate == template).findFirst();
        if (requiredItem.isPresent()) {
            setCount(count);
        } else {
            listOfItems.getRequiredItems().add(new RequiredItem(template, count));
        }
    }

    public void getItemsFromInput(ListOfItems listOfItems, String itemsChoice) {
        List<ItemTemplate> list = Arrays.stream(ItemTemplate.values()).toList();
        String[] arrItemsChoice = itemsChoice.split("\\s");
        for (String s : arrItemsChoice) {
            String[] separate = s.split("\\.");
            setItemCount(list.get(Integer.parseInt(separate[0]) - 1), Integer.parseInt(separate[1]),listOfItems);
        }
    }
}
