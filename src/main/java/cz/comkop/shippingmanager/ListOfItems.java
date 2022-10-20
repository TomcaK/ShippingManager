package cz.comkop.shippingmanager;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class ListOfItems {

    private final List<Item> removedItems = new ArrayList<>();
    private final List<LoadedItem> loadedItems = new ArrayList<>();
    private List<Item> selectedItems = new ArrayList<>();
    private Map<ItemTemplate, Integer> requiredItems;
//TODO zabezpečit manipulaci mimo třídu (vytvořit metody)
//    public List<Item> getSelectedItems() {
//        return selectedItems;
//    }

    /**
     *
     * @param itemsChoice
     */
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
                selectedItems.add(new Item(template));
            }
        }
        selectedItems = selectedItems.stream().sorted(Comparator.comparing(Item::getSortingArea).reversed()).collect(Collectors.toList());
    }

    public void moveItemToAnotherList(List<Item> selectedItems, List<Item> listOfItemsForMove, int i, char codename) {
        listOfItemsForMove.add(new LoadedItem((ItemToCheck) selectedItems.get(i), codename));
        if (listOfItemsForMove.hashCode() == removedItems.hashCode()) {
            requiredItems.put(selectedItems.get(i).getTemplate(), requiredItems.get(selectedItems.get(i).getTemplate()) - 1);
            if (requiredItems.get(selectedItems.get(i).getTemplate()) == 0)
                requiredItems.remove(selectedItems.get(i).getTemplate());
        }
        selectedItems.remove(i);
    }

    public void removeDuplicates() {
        for (int i = 0; i < loadedItems.size(); i++) {
            for (int j = i + 1; j < loadedItems.size(); j++) {
                if (loadedItems.get(i).getTemplate().equals(loadedItems.get(j).getTemplate())) {
                    loadedItems.remove(j);
                    j--;
                }
            }

        }
    }


}