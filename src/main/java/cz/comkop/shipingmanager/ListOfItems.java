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


    /*  private final ArrayList<ItemTemplate> itemTemplates;
        public static final ListOfItems TEMPLATE = new ListOfItems();
        public ListOfItems() {
            itemTemplates = new ArrayList<>();
            itemTemplates.add(new ItemTemplate("Test-Quarter machine", 1, 2, 235, true));
            itemTemplates.add(new ItemTemplate("Test-Half machine", 2, 3, 320, true));
            itemTemplates.add(new ItemTemplate("Test-Big machine", 3, 5, 420));
            itemTemplates.add(new ItemTemplate("Test-OZ2", 1, 1, 20, true));
            itemTemplates.add(new ItemTemplate("Test-MustturnMachine", 3, 2, 320, true));
            itemTemplates.add(new ItemTemplate("220x250 GH-LR", 60, 141, 235, true));
            itemTemplates.add(new ItemTemplate("230x280 GH-LR", 77, 144, 320, true));
            itemTemplates.add(new ItemTemplate("230x280 SHI-LR", 105, 234, 420, true));
            itemTemplates.add(new ItemTemplate("230x280 A-CNC-R", 231, 226, 780, true));
            itemTemplates.add(new ItemTemplate("300x320 GH-LR", 205, 117, 605, true));
            itemTemplates.add(new ItemTemplate("300x320 SHI-LR", 117, 205, 650, true));
            itemTemplates.add(new ItemTemplate("Box 220x250 GH-LR/230x280 GH-LR", 80, 165, 320, true));
            itemTemplates.add(new ItemTemplate("Cage 300x320/230x280 SHI-LR", 117, 231, 650, true));
            itemTemplates.add(new ItemTemplate("300x320 A-CNC-R", 231, 190, 904, true));
            itemTemplates.add(new ItemTemplate("360x500 GH-LR", 108, 280, 682, true));
            itemTemplates.add(new ItemTemplate("360x500 SHI-LR", 108, 280, 740, true));
            itemTemplates.add(new ItemTemplate("360x500 A-CNC-R", 245, 281, 1500));
            itemTemplates.add(new ItemTemplate("460x600 SHI-LR", 114, 320, 1195));
            itemTemplates.add(new ItemTemplate("340 Katana X-CNC-LR", 245, 315, 2150));
            itemTemplates.add(new ItemTemplate("400 Profi A-CNC", 215, 260, 1300));
            itemTemplates.add(new ItemTemplate("300x300 Herkules X-CNC", 228, 251, 1420));
            itemTemplates.add(new ItemTemplate("500x750 Horizontal SHI", 176, 310, 2035));
            itemTemplates.add(new ItemTemplate("600x1000 Horizontal X", 198, 434, 5678));
            itemTemplates.add(new ItemTemplate("440 Caliber X-CNC/SHI", 203, 256, 3115));
            itemTemplates.add(new ItemTemplate("440 Horizont SHI/X", 176, 350, 2080));
            itemTemplates.add(new ItemTemplate("RDL/RDR", 100, 110, 200));
            itemTemplates.add(new ItemTemplate("OZ", 15, 300, 30));
            itemTemplates.add(new ItemTemplate("Pallet 120 cm x 80 cm", 80, 120));
            itemTemplates.add(new ItemTemplate("Half pallet 80 cm x 60 cm", 80, 120));
            itemTemplates.add(new ItemTemplate("R290", 60, 60, 12));

            /*Pila	Délka/Length	Hloubka/Width	Výška/Height	Váha/Weight





            440 x600 Horizontal X - NC - BS 290 160 214 2000
            500 x750 Horizontal X 310 176 221 2120
            600 Camel X 432 136 220
            400 x400 Herkules X - CNC 326 233 221 3560
            500 x750 Horizontal X - NC - BS 360 170 221 4150
            440 x600 Horizontal SHI 295 176 215 1685
            510 x 510 Herkules X -CNC 336 240 210 6380
            540 Caliber X -CNC 370 212 239 3790
            540 Horizont X -CNC 358 137 240 3900
            600 x1100 Horizontal X 434 198 265 5678


        }

        public ArrayList<ItemTemplate> getItems() {

            return itemTemplates;
        }*/
    public void getItemsFromInput(String itemsChoice) {
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
}