package cz.comkop.shipingmanager;

import java.util.*;
import java.util.stream.Collectors;

public class ListOfItems {
    private List<Item> selectedItems = new ArrayList<>();
    private final List<Item> removedItems = new ArrayList<>();
    private final List<Item> loadedItems = new ArrayList<>();
    private final Map<ItemTemplate, Integer> requiredItems = new HashMap<>();

    public List<Item> getSelectedItems() {
        return selectedItems;
    }

    public List<Item> getRemovedItems() {
        return removedItems;
    }

    public List<Item> getLoadedItems() {
        return loadedItems;
    }

    public Map<ItemTemplate, Integer> getRequiredItems() {
        return requiredItems;
    }

    public void getItemsFromInput(String itemsChoice) {//TODO change method to method which will take key and create selected goods. Key will be created from ConsoleUI from new searching method
        List<ItemTemplate> list = Arrays.stream(ItemTemplate.values()).toList();
        String[] arrItemsChoice = itemsChoice.split("\\s");
        for (String s : arrItemsChoice) {
            String[] separate = s.split("\\.");
            requiredItems.put(list.get(Integer.parseInt(separate[0]) - 1), Integer.parseInt(separate[1]));
        }
    }

    public void createSelectedItems() {
        for (ItemTemplate template : requiredItems.keySet()) {
            for (int i = 0; i < requiredItems.get(template); i++) {
                selectedItems.add(new Item(template, ' ', 0, 0));
            }
        }

    }

    public void sortSelectedItemsByArea() {
        selectedItems = selectedItems.stream().sorted(Comparator.comparing(Item::getArea).reversed()).collect(Collectors.toList());
    }

    public void selectItemsToLoadLater(TrailerTemplate trailer) {
        int rest;
        int pack;
        int quantity;
        for (int i = 0; i < selectedItems.size(); ) {
            pack = trailer.getWidth() / selectedItems.get(i).getTemplate().getWidth();
            quantity = requiredItems.get(selectedItems.get(i).getTemplate());
            rest = quantity % pack;
            for (int j = i; j < i + quantity; j++) {
                if (j < i + rest) {
                    selectedItems.get(i).setLoadLater(true);
                }
            }
            i += quantity;
        }
    }

    public void createPacks(TrailerTemplate trailerTemplate) {
        int count = 1, index = 0;
        for (int i = 0; i < selectedItems.size(); i++) {

            int quantity, numberOfItemsWidth, numberOfItemsLength = 0, deviation = 10, restW, restL, numberOfRowsW, numberOfRowsL, w, l;
            quantity = requiredItems.get(selectedItems.get(i).getTemplate());


            // rest = quantity % pack;
            numberOfItemsWidth = trailerTemplate.getWidth() / selectedItems.get(i).getTemplate().getWidth();


            //3 kolik se vejde itemu na sirku do navesu
            numberOfRowsW = quantity / numberOfItemsWidth;
            restW = quantity % numberOfItemsWidth;

            numberOfRowsW += restW != 0 ? 1 : 0;

            w = selectedItems.get(i).getTemplate().getLength() * (numberOfRowsW);

            if (selectedItems.get(i).getTemplate().isCanBeRotated90Degrees() &&
                    !selectedItems.get(i).getTemplate().isPreferedNotToBeRotated()) {
                numberOfItemsLength = trailerTemplate.getWidth() / selectedItems.get(i).getTemplate().getLength();
                numberOfRowsL = quantity / numberOfItemsLength;
                restL = quantity % numberOfItemsLength;
                numberOfRowsL += restL != 0 ? 1 : 0;
                l = selectedItems.get(i).getTemplate().getWidth() * (numberOfRowsL);//2

                if (w < l) {
                    for (int j = i; j < i + (quantity * numberOfRowsW); j++) {


                        selectedItems.get(j).setInPack(count);
                        index = j;
                    }
                    count++;

                } else {
                    for (int j = i; j < i + (quantity * numberOfRowsW); j++) {
                        selectedItems.get(j).setInPack(count);
                        selectedItems.get(j).setTurnItem90Degrees(true);
                        index = j;
                    }
                    count++;

                }
            }
            else {
                selectedItems.get(i).setInPack(count++);

            }


//           while (width <= trailerTemplate.getWidth()) {
//               List<Item> similarItems = selectedItems.stream().filter(item -> item.getTemplate().getWidth() <= width )
//                width += selectedItems.get(i).getTemplate().getWidth();
//            }
//
            i = index;
        }

    }
}