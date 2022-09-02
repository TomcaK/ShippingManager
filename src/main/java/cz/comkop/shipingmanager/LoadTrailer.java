package cz.comkop.shipingmanager;


import java.util.ArrayList;
import java.util.List;

public class LoadTrailer {

    private int checkpointY;
    private int checkpointX;

    public void loading(ListOfItems listOfItems, Trailer trailer) {//TODO check of height, //TODO tvorba algoritmu, který se primárně zaměří na využití celé šířky auta.
        int round = 0;
        while (listOfItems.getSelectedItems().size() != 0) {
            for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                if (!listOfItems.getSelectedItems().get(i).isLoadLater() || listOfItems.getSelectedItems().get(i).isLoadLater() && round > 0) {
                    if (isFreeSpaceOnTrailer(i, listOfItems, trailer) && /*searchFreeSpaceCharacterOnTrailer(i, selectedItems)*/ searchFreeSpaceOnTrailer2(i, listOfItems, trailer)) {
                        addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
                        i--;
                    } else if (round == 2) {
                        listOfItems.getRemovedItems().add(new Item(listOfItems.getSelectedItems().get(i).getTemplate()));
                        listOfItems.getSelectedItems().remove(i);
                        i--;
                    }
                }
            }
            round++;
        }
        trailer.countLDM();
    }

    public void firstLoading(ListOfItems listOfItems, Trailer trailer) {
            for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                int rest;
                int pack;
                List<Item> itemsPack = new ArrayList<>();
                pack = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(i).getTemplate().getWidth();
                rest = listOfItems.getRequiredItems().get(listOfItems.getSelectedItems().get(i).getTemplate()) % pack;
                if (rest == 0) {
                    addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
                    i--;
                    checkpointY += listOfItems.getSelectedItems().get(i).getTemplate().getLength() - 1;
                }
                if (rest > 0){
                    for (int j = 0; j < listOfItems.getSelectedItems().size(); j++) {

                    }
                }
            }

    }


    private void addItemToTrailer(int indexOfItem, int cX, int cY, ListOfItems listOfItems, Trailer trailer) {
        int maxLength, maxWidth;

        if (listOfItems.getSelectedItems().get(indexOfItem).isTurnItem90Degrees()) {
            maxLength = cY + listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getWidth();
            maxWidth = cX + listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength();
        } else {
            maxLength = cY + listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength();
            maxWidth = cX + listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getWidth();
        }
        for (int y = cY; y < maxLength; y++) {
            for (int x = cX; x < maxWidth; x++) {
                trailer.getTrailerModel()[y][x] = String.valueOf(trailer.getNextCodename());
            }
        }
        trailer.setTotalWeight(trailer.getTotalWeight() + listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getWeight());
        listOfItems.getLoadedItems().add(new Item(listOfItems.getSelectedItems().get(indexOfItem).getTemplate(), trailer.getNextCodename(), cX, cY));
        listOfItems.getSelectedItems().remove(indexOfItem);
        trailer.setFreeSquareCentimeters(trailer.getFreeSquareCentimeters() - listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength() * listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth());
        trailer.setNextCodename((char) (trailer.getNextCodename() + 1));
        //System.out.println("Coordinates X: " + loadedGoods.get(loadedGoods.size() - 1).getX() + ",Y: " + loadedGoods.get(loadedGoods.size() - 1).getY() + " Added! Goods: " + loadedGoods.get(loadedGoods.size() - 1).getName());
    }


    private boolean isFreeSpaceOnTrailer(int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        return listOfItems.getSelectedItems().get(indexOfGoods).getTemplate().getWidth() * listOfItems.getSelectedItems().get(indexOfGoods).getTemplate().getLength() <= trailer.getFreeSquareCentimeters();
    }

    private boolean searchFreeSpaceOnTrailer2(int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        if (listOfItems.getLoadedItems().size() != 0) {
            for (int y = 0; y < trailer.getTemplate().getLength(); y++) {
                for (int x = 0; x < trailer.getTemplate().getWidth(); ) {
                    for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
                        if (x >= listOfItems.getLoadedItems().get(i).getX() && x <= listOfItems.getLoadedItems().get(i).getX() + listOfItems.getLoadedItems().get(i).getTemplate().getWidth() - 1 && y >= listOfItems.getLoadedItems().get(i).getY() && y <= listOfItems.getLoadedItems().get(i).getY() + listOfItems.getLoadedItems().get(i).getTemplate().getLength() - 1) {
                            x += listOfItems.getLoadedItems().get(i).getTemplate().getWidth();
                        }
                    }
                    if (doesCurrentItemFitForTheseCoordinates(x, y, indexOfGoods, listOfItems, trailer)) {
                        checkpointY = y;
                        checkpointX = x;
                        return true;
                    } else {
                        x++;
                    }
                }
            }
            return false;
        }
        return true;
    }

////    public boolean inspectItem(ListOfItems listOfItems, Trailer trailer, Item item) {
//        int rest;
//        int pack;
//        int quantity;
//        List<Item> itemsPack = new ArrayList<>();
//        pack = trailer.getTemplate().getWidth() / item.getTemplate().getWidth();
//        rest = listOfItems.getRequiredItems().get(item.getTemplate()) % pack;
//
//        if (rest == 0) {
//            return true; //loadItem
//        }
//        if (listOfItems.getRequiredItems().get(listOfItems.getSelectedItems().get(inderOfItem).getTemplate())) {
//
//        }
//
//
//        for (int i = 0; i < selectedItems.size(); ) {
//            pack = trailer.getWidth() / selectedItems.get(i).getTemplate().getWidth();
//
//
//            quantity = requiredItems.get(selectedItems.get(i).getTemplate());
//            rest = quantity % pack;
//            for (int j = i; j < i + quantity; j++) {
//                if (j < i + rest) {
//                    selectedItems.get(i).setLoadLater(true);
//                }
//            }
//            i += quantity;
//        }
//
//        return false;
////    }

    private boolean doesCurrentItemFitForTheseCoordinates(int x, int y, int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        ItemTemplate itemTemplate = listOfItems.getSelectedItems().get(indexOfGoods).getTemplate();

        if (itemTemplate.getWidth() + x <= trailer.getTemplate().getWidth() && itemTemplate.getLength() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker2(x, (x + itemTemplate.getWidth()), y, (y + itemTemplate.getLength()), listOfItems)) {
                return true;
            }

        }
        if (itemTemplate.isCanBeRotated90Degrees() && itemTemplate.getLength() + x <= trailer.getTemplate().getWidth() && itemTemplate.getWidth() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker2(x, (x + itemTemplate.getLength()), y, (y + itemTemplate.getWidth()), listOfItems)) {
                listOfItems.getSelectedItems().get(indexOfGoods).setTurnItem90Degrees(true);
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean freeCoordinatesChecker2(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd, ListOfItems listOfItems) {
        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
                for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
                    if (x >= listOfItems.getLoadedItems().get(i).getX() && x <= listOfItems.getLoadedItems().get(i).getX() + listOfItems.getLoadedItems().get(i).getTemplate().getWidth() - 1 && y >= listOfItems.getLoadedItems().get(i).getY() && y <= listOfItems.getLoadedItems().get(i).getY() + listOfItems.getLoadedItems().get(i).getTemplate().getLength() - 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}