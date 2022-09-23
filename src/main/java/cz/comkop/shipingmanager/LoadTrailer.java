package cz.comkop.shipingmanager;


import java.util.List;
import java.util.stream.Collectors;

public class LoadTrailer {

    private int coordinateY;
    private int coordinateX;

    public List<Item> getItemsToFit(List<Item> similarItems, int freeSpace) {
        return similarItems.stream().filter(item1 -> item1.getInPack() == 0)
                .filter(item -> item.getTemplate().getWidth() <= freeSpace || item.getTemplate().getLength() <= freeSpace && item.getTemplate().isCanBeRotated90Degrees() && !item.getTemplate().isPreferNotToBeRotated())
                .collect(Collectors.toList());
//        if (index == 0) {
//            return list;
//        }
//        return list.stream().filter(item1 -> item1.getInPack() == 0).collect(Collectors.toList());
    }

    public List<Item> getSimilarItems(List<Item> selectedItems, ItemTemplate similarToThisItem, int difference) {
        return selectedItems.stream().filter(item -> !item.getTemplate().equals(similarToThisItem))
                .filter(item -> item.getInPack() == 0)
                .filter(item -> item.getTemplate().getLength() == similarToThisItem.getLength() && item.getTemplate().getWidth() >= similarToThisItem.getWidth() - difference
                        && item.getTemplate().getWidth() <= similarToThisItem.getWidth() + difference
                        || item.getTemplate().getWidth() == similarToThisItem.getWidth() && item.getTemplate().getLength() >= similarToThisItem.getLength() - difference
                        && item.getTemplate().getLength() <= similarToThisItem.getLength() + difference)
                .collect(Collectors.toList());
    }


    public void loading(ListOfItems listOfItems, Trailer trailer) {
        int round = 0;
        while (listOfItems.getSelectedItems().size() != 0) {
            for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                // createPackOfItems(listOfItems, trailer, i);

                if (/*searchFreeSpaceCharacterOnTrailer(i, selectedItems)*/ searchFreeSpaceOnTrailer(i, listOfItems, trailer)) {
                    addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                    i--;
                } else if (round == 2) {
                    listOfItems.getRemovedItems().add(new Item(listOfItems.getSelectedItems().get(i).getTemplate()));
                    listOfItems.getSelectedItems().remove(i);
                    i--;
                }
            }

            round++;
        }
        trailer.countLDM();
    }

    public void loading2(ListOfItems listOfItems, Trailer trailer) {
        int round = 1, highestPack = 0;
        createPacks(trailer, listOfItems);
        for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
            if (highestPack < listOfItems.getSelectedItems().get(i).getInPack()) {
                highestPack = listOfItems.getSelectedItems().get(i).getInPack();
            }
        }
        while (listOfItems.getSelectedItems().size() != 0) {
            if (round <= 1) {
                for (int i = 1; i <= highestPack; i++) {
                    for (int j = 0; j < listOfItems.getSelectedItems().size(); j++) {
                        if (listOfItems.getSelectedItems().get(j).getInPack() == i) {
                            if (trailer.getTemplate().getWidth() < coordinateX + listOfItems.getSelectedItems().get(j).getTemplate().getWidth() && (trailer.getTemplate().getWidth() < coordinateX + listOfItems.getSelectedItems().get(j).getTemplate().getLength() ||
                                    !listOfItems.getSelectedItems().get(j).isTurnItem90Degrees())) {
                                coordinateX = 0;
                                coordinateY += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).isTurnItem90Degrees() ? listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth() : listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
                            }
                            addItemToTrailer(j, coordinateX, coordinateY, listOfItems, trailer);
                            coordinateX += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).isTurnItem90Degrees() ? listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength() : listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth();
                            j--;

                        }
                    }
                    coordinateX = 0;
                    coordinateY += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).isTurnItem90Degrees() ? listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth() : listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
                }
            } else {
                for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                    if (searchFreeSpaceOnTrailer(i, listOfItems, trailer)) {
                        addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                    } else {
                        listOfItems.getRemovedItems().add(new Item(listOfItems.getSelectedItems().get(i).getTemplate()));

                        listOfItems.getSelectedItems().remove(i);
                    }
                    i--;
                }
            }
            round++;
        }
        trailer.countLDM();
    }

    public int findSolutionHowToLoad(ItemTemplate itemTemplate, int quantity, int itemsInRow, boolean isTurnedOver) {
        int numberOfRows;
        numberOfRows = quantity / itemsInRow;
        numberOfRows += quantity % itemsInRow != 0 ? 1 : 0;
        if (isTurnedOver) {
            return itemTemplate.getWidth() * numberOfRows;
        }
        return itemTemplate.getLength() * numberOfRows;

    }


    public int findSolutionHowToLoad(ItemTemplate itemTemplate1, int itemsInRow, ItemTemplate itemTemplate2, TrailerTemplate trailerTemplate, boolean isTurnedOver) {
        int numberOfRows = 1;
        if (isTurnedOver) {
            if ((itemsInRow * itemTemplate1.getLength(
            )) + itemTemplate2.getLength() > trailerTemplate.getWidth()) {
                numberOfRows++;
            }
            return itemTemplate1.getWidth() * numberOfRows;
        }
        if ((itemsInRow * itemTemplate1.getWidth()) + itemTemplate2.getWidth() > trailerTemplate.getWidth()) {
            numberOfRows++;
        }
        return itemTemplate1.getLength() * numberOfRows;
    }


    //TODO zjistit, co se tane když se oddělá difference
    public void createPacks(Trailer trailer, ListOfItems listOfItems) {
        int pack = 1;
        int totalTakenLength = 0;

        for (int i = 0; i < listOfItems.getSelectedItems().size(); ) {
            int quantity;
            int itemsInRowWidth;
            int itemsInRowLength = 0;
            int w = 0;
            int l = 0;
            int difference = 15;
            Item bestItem = null;
            int chosenFreeSpace;
            List<Item> itemsToFit;
            ItemTemplate selectedItem = listOfItems.getSelectedItems().get(i).getTemplate();
            List<Item> similarItems = getSimilarItems(listOfItems.getSelectedItems(), selectedItem, difference);
            quantity = (int) listOfItems.getSelectedItems().stream().filter(item1 -> item1.getTemplate().equals(selectedItem)).filter(item1 -> item1.getInPack() == 0).count();
            itemsInRowWidth = trailer.getTemplate().getWidth() / selectedItem.getWidth();
            w = findSolutionHowToLoad(selectedItem, quantity, itemsInRowWidth, false);
            if (selectedItem.isCanBeRotated90Degrees() && !selectedItem.isPreferNotToBeRotated()) {
                itemsInRowLength = trailer.getTemplate().getWidth() / selectedItem.getLength();
                l = findSolutionHowToLoad(selectedItem, quantity, itemsInRowLength, true);
                itemsInRowLength = Math.min(quantity, itemsInRowLength);
            }
            itemsInRowWidth = Math.min(quantity, itemsInRowWidth);
            if (l == 0) {
                l = selectedItem.getLength();
            }
            while (w > trailer.getTemplate().getLength() - totalTakenLength && l > trailer.getTemplate().getLength() - totalTakenLength) {
                listOfItems.moveItem(listOfItems.getSelectedItems(), listOfItems.getRemovedItems(), i, 0, 0, ' ');
                quantity -= 1;
                w = findSolutionHowToLoad(selectedItem, quantity, itemsInRowWidth, false);
                l = selectedItem.isCanBeRotated90Degrees() && !selectedItem.isPreferNotToBeRotated() ? findSolutionHowToLoad(selectedItem, quantity, itemsInRowLength, true) : selectedItem.getLength();
            }
            int freeSpaceW, freeSpaceL;
            freeSpaceW = trailer.getTemplate().getWidth() - (itemsInRowWidth * selectedItem.getWidth());
            freeSpaceL = trailer.getTemplate().getWidth() - (itemsInRowLength * selectedItem.getLength());
            if (freeSpaceW > freeSpaceL || freeSpaceL != trailer.getTemplate().getWidth()) {
                itemsToFit = getItemsToFit(similarItems, freeSpaceW);
                chosenFreeSpace = freeSpaceW;
            } else {
                itemsToFit = getItemsToFit(similarItems, freeSpaceL);
                chosenFreeSpace = freeSpaceL;
            }
            if (!itemsToFit.isEmpty() || quantity != 1) {
                if (!itemsToFit.isEmpty()) {
                    bestItem = getBestItem(chosenFreeSpace, itemsToFit);
                    w = findSolutionHowToLoad(selectedItem, itemsInRowWidth, bestItem.getTemplate(), trailer.getTemplate(), false);
                    l = selectedItem.isCanBeRotated90Degrees() && !selectedItem.isPreferNotToBeRotated() ? findSolutionHowToLoad(selectedItem, itemsInRowLength, bestItem.getTemplate(), trailer.getTemplate(), true) : selectedItem.getLength();
                }
                if (l > w || itemsInRowLength == 0) {
                    for (int j = i; j < i + itemsInRowWidth; j++) {
                        listOfItems.getSelectedItems().get(j).setInPack(pack);
                    }
                    if (bestItem != null) {
                        for (Item it : listOfItems.getSelectedItems()) {
                            if (it.getTemplate().equals(bestItem.getTemplate())) {
                                it.setInPack(pack);
                                break;
                            }
                        }
                    }
                    //i++;
                    totalTakenLength += listOfItems.getSelectedItems().get(i).getTemplate().getLength();
                    // i += itemsInRowWidth - 1;
                } else {
                    for (int j = i; j < i + itemsInRowLength; j++) {
                        listOfItems.getSelectedItems().get(j).setTurnItem90Degrees(true);
                        listOfItems.getSelectedItems().get(j).setInPack(pack);
                    }
                    if (bestItem != null) {
                        for (Item it : listOfItems.getSelectedItems()) {
                            if (it.getTemplate().equals(bestItem.getTemplate())) {
                                it.setInPack(pack);
                                it.setTurnItem90Degrees(true);
                                break;
                            }
                        }
                        // i++;
                    }
                    totalTakenLength += listOfItems.getSelectedItems().get(i).getTemplate().getWidth();
                    // i += itemsInRowLength - 1;
                }
                pack++;

            }
            i++;
            while (i < listOfItems.getSelectedItems().size() && listOfItems.getSelectedItems().get(i).getInPack() != 0) {
                i++;
            }
        }

    }

    private Item getBestItem(int freeSpace, List<Item> itemsToFit) {
        int restW = 0, restL = 0, lowestRest = 0;
        Item item = null;
        for (int i = 0; i < itemsToFit.size(); i++) {
            restW = freeSpace - itemsToFit.get(i).getTemplate().getWidth();
            if (!itemsToFit.get(i).getTemplate().isPreferNotToBeRotated()) {
                restL = freeSpace - itemsToFit.get(i).getTemplate().getLength();
            }
            if (i != 0) {
                if (restW >= 0 && restW < restL && !itemsToFit.get(i).getTemplate().isPreferNotToBeRotated()) {
                    if (lowestRest > restW) {
                        lowestRest = restW;
                        item = new Item(itemsToFit.get(i).getTemplate(), false);
                    }
                } else if (restL >= 0) {
                    if (lowestRest > restL) {
                        lowestRest = restL;
                        item = new Item(itemsToFit.get(i).getTemplate(), true);
                    }
                } else {
                    if (lowestRest > restW) {
                        lowestRest = restW;
                        item = new Item(itemsToFit.get(i).getTemplate(), false);
                    }
                }
            } else {
                if (restW >= 0 && restW < restL && !itemsToFit.get(i).getTemplate().isPreferNotToBeRotated()) {
                    lowestRest = restW;
                    item = new Item(itemsToFit.get(i).getTemplate(), false);
                } else if (restL >= 0) {
                    lowestRest = restL;
                    item = new Item(itemsToFit.get(i).getTemplate(), true);
                } else {
                    lowestRest = restW;
                    item = new Item(itemsToFit.get(i).getTemplate(), false);
                }
            }
        }
        return item;
    }

    private void setCoordinates() {

//                }
//                checkpointY += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
    }

//    public void firstLoading(ListOfItems listOfItems, Trailer trailer) {
//        for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
//
//            List<Item> itemsPack = new ArrayList<>();
//
//            if (listOfItems.getSelectedItems().get(i).getTemplate().isPreferedNotToBeRotated() || w > l && l <= trailer.getTemplate().getWidth()) {
//                for (int j = i; j < i + quantity; j++) {
//                    addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
//                    checkpointX += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth();
//                }
//                checkpointY += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
//            } else {
//                for (int j = i; j < i + quantity; j++) {
//                    addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
//                    checkpointX += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
//                }
//                checkpointY += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth();
//
//            }
//            checkpointX = 0;
//            i--;
//
//            if (rest > 0) {
//
//            } else {
//
//            }
//        }
//
//    }


    private void addItemToTrailer(int indexOfItem, int cX, int cY, ListOfItems listOfItems, Trailer trailer) {
        int maxLength, maxWidth;
        Item item = listOfItems.getSelectedItems().get(indexOfItem);
        if (item.isTurnItem90Degrees()) {
            maxLength = cY + item.getTemplate().getWidth();
            maxWidth = cX + item.getTemplate().getLength();
        } else {
            maxLength = cY + item.getTemplate().getLength();
            maxWidth = cX + item.getTemplate().getWidth();
        }
        System.out.println("");
        for (int y = cY; y < maxLength; y++) {
            for (int x = cX; x < maxWidth; x++) {
                trailer.getTrailerModel()[y][x] = String.valueOf(trailer.getNextCodename());
            }
        }
        trailer.setTotalWeight(trailer.getTotalWeight() + item.getTemplate().getWeight());
        listOfItems.moveItem(listOfItems.getSelectedItems(), listOfItems.getLoadedItems(), indexOfItem, cX, cY, trailer.getNextCodename());
        trailer.setFreeSquareCentimeters(trailer.getFreeSquareCentimeters() - listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength() * listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth());
        trailer.setNextCodename((char) (trailer.getNextCodename() + 1));
    }


    private boolean searchFreeSpaceOnTrailer(int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        if (listOfItems.getLoadedItems().size() != 0) {
            for (int y = 0; y < trailer.getTemplate().getLength(); y++) {
                for (int x = 0; x < trailer.getTemplate().getWidth(); ) {
                    for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
                        Item item = listOfItems.getLoadedItems().get(i);
                        if (!item.isTurnItem90Degrees() && x >= item.getX() && x <= item.getX() + item.getTemplate().getWidth() - 1 && y >= item.getY() && y <= item.getY() + item.getTemplate().getLength() - 1) {
                            x += item.getTemplate().getWidth();
                        } else if (x >= item.getX() && x <= item.getX() + item.getTemplate().getLength() - 1 && y >= item.getY() && y <= item.getY() + item.getTemplate().getWidth() - 1) {
                            x += item.getTemplate().getLength();
                        }
                    }
                    if (doesCurrentItemFitForTheseCoordinates(x, y, indexOfGoods, listOfItems, trailer)) {
                        coordinateY = y;
                        coordinateX = x;
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


    private boolean doesCurrentItemFitForTheseCoordinates(int x, int y, int indexOfGoods, ListOfItems//TODO rework to turt object if its possible and take it less space in length
            listOfItems, Trailer trailer) {
        ItemTemplate itemTemplate = listOfItems.getSelectedItems().get(indexOfGoods).getTemplate();
        int w = 0;
        int l = 0;
        if (itemTemplate.getWidth() + x <= trailer.getTemplate().getWidth() && itemTemplate.getLength() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker(x, (x + itemTemplate.getWidth()), y, (y + itemTemplate.getLength()), listOfItems)) {
                w = itemTemplate.getWidth();
            }
        }
        if (itemTemplate.isCanBeRotated90Degrees() && itemTemplate.getLength() + x <= trailer.getTemplate().getWidth() && itemTemplate.getWidth() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker(x, (x + itemTemplate.getLength()), y, (y + itemTemplate.getWidth()), listOfItems)) {
                l = itemTemplate.getLength();
            }
        }
        if (w < l && l != 0) {
            listOfItems.getSelectedItems().get(indexOfGoods).setTurnItem90Degrees(true);
            return true;
        } else if (w != 0) {
            return true;
        }
        return false;
    }

    private boolean freeCoordinatesChecker(int coordinateXStart, int coordinateXEnd, int coordinateYStart,
                                           int coordinateYEnd, ListOfItems listOfItems) {
        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
                for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
                    if (!listOfItems.getLoadedItems().get(i).isTurnItem90Degrees() && x >= listOfItems.getLoadedItems().get(i).getX() && x <= listOfItems.getLoadedItems().get(i).getX() + listOfItems.getLoadedItems().get(i).getTemplate().getWidth() - 1 && y >= listOfItems.getLoadedItems().get(i).getY() && y <= listOfItems.getLoadedItems().get(i).getY() + listOfItems.getLoadedItems().get(i).getTemplate().getLength() - 1) {
                        return false;
                    } else if (x >= listOfItems.getLoadedItems().get(i).getX() && x <= listOfItems.getLoadedItems().get(i).getX() + listOfItems.getLoadedItems().get(i).getTemplate().getLength() - 1 && y >= listOfItems.getLoadedItems().get(i).getY() && y <= listOfItems.getLoadedItems().get(i).getY() + listOfItems.getLoadedItems().get(i).getTemplate().getWidth() - 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}