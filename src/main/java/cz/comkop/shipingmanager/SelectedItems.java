package cz.comkop.shipingmanager;

import java.util.ArrayList;

public class SelectedItems {
    private ArrayList<Item> selectedItems = new ArrayList<>();

    /**
     * Method takes selected types of items and their quantity and adds them to ArrayList.
     *
     * @param items
     */
    public void createSelectedItems(ArrayList<Item> items) {

        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < items.get(i).getQuantity(); j++) {
                selectedItems.add(new Item(items.get(i)));
                selectedItems.get(selectedItems.size() - 1).setQuantity(items.get(i).getQuantity());
            }
        }

    }

    public void searchItemsFromInput(ArrayList<Item> items, String itemsChoice) {
        String[] arrItemsChoice = itemsChoice.split("\\s");
        for (String s : arrItemsChoice) {
            String[] separate = s.split("\\.");
            items.get(Integer.parseInt(separate[0]) - 1).setQuantity(Integer.parseInt(separate[1]));
        }
    }

    private void sortSelectedItemsByDimension() {
        ArrayList<Item> sortArray = new ArrayList<>();
        int maxSize = 0;
        while (selectedItems.size() != 0) {
            for (int i = 0; i < selectedItems.size(); i++) {
                if (selectedItems.get(i).getWidth() * selectedItems.get(i).getLength() > maxSize) {
                    maxSize = selectedItems.get(i).getWidth() * selectedItems.get(i).getLength();
                }
            }
            for (int i = 0; i < selectedItems.size(); i++) {
                if (maxSize == selectedItems.get(i).getWidth() * selectedItems.get(i).getLength()) {
                    sortArray.add(selectedItems.get(i));
                    selectedItems.remove(i);
                    i--;
                }
            }
            maxSize = 0;
        }
        selectedItems = sortArray;

    }

    private void createPackFromItems(Trailer trailer) {
        int rest = 0;
        int pack = 0;
        for (int i = 0; i < selectedItems.size(); ) {
            pack = trailer.getTotalWidth() / selectedItems.get(i).getWidth();
            rest = selectedItems.get(i).getQuantity() % pack;
            for (int j = i; j < i + selectedItems.get(i).getQuantity(); j++) {
                if (j < i + rest) {
                    selectedItems.get(i).setLoadLast(true);
                }
            }
            i += selectedItems.get(i).getQuantity();
        }
    }

    public void sorting(Trailer trailer) {
        sortSelectedItemsByDimension();
        createPackFromItems(trailer);
    }

    public ArrayList<Item> getSelectedItems() {
        return selectedItems;
    }
}
