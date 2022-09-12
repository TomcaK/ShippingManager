package cz.comkop.shipingmanager;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
@Getter
public class ListOfItems {
    private List<Item> selectedItems = new ArrayList<>();
    private final List<Item> removedItems = new ArrayList<>();
    private final List<Item> loadedItems = new ArrayList<>();
    private Map<ItemTemplate, Integer> requiredItems;
//TODO zabezpečit manipulaci mimo třídu (vytvořit metody)
//    public List<Item> getSelectedItems() {
//        return selectedItems;
//    }
//
//    public List<Item> getRemovedItems() {
//        return removedItems;
//    }
//
//    public List<Item> getLoadedItems() {
//        return loadedItems;
//    }
//
//    public Map<ItemTemplate, Integer> getRequiredItems() {
//        return requiredItems;
//    }

    public void getItemsFromInput(String itemsChoice) {//TODO change method to method which will take key and create selected goods. Key will be created from ConsoleUI from new searching method
        List<ItemTemplate> list = Arrays.stream(ItemTemplate.values()).toList();
        String[] arrItemsChoice = itemsChoice.split("\\s");
        requiredItems = new HashMap<>();
        for (String s : arrItemsChoice) {
            String[] separate = s.split("\\.");
            requiredItems.put(list.get(Integer.parseInt(separate[0]) - 1), Integer.parseInt(separate[1]));

        }
    }

    public void createSelectedItems() {
        for (ItemTemplate template : requiredItems.keySet()) {
            for (int i = 0; i < requiredItems.get(template); i++) {
                selectedItems.add(new Item(template, ' ', 0, 0,false));
            }
        }
    }
    


    public void sortSelectedItemsByArea() {
        selectedItems = selectedItems.stream().sorted(Comparator.comparing(Item::getArea).reversed()).collect(Collectors.toList());
    }




}