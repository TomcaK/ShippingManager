package cz.comkop.shipingmanager;


import java.util.List;
import java.util.stream.Collectors;

public class LoadTrailer {

    private int coordinateY;
    private int coordinateX;


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

    public void loading2(ListOfItems listOfItems, Trailer trailer) {//TODO check of height, //TODO tvorba algoritmu, který se primárně zaměří na využití celé šířky auta.
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

    private int findSolutionHowToLoad(Trailer trailer, ListOfItems listOfItems, int i) {
        int quantity, quantityOfItems, numberOfRows;
        quantity = (int) listOfItems.getSelectedItems().stream().filter(item1 -> item1.getTemplate().equals(listOfItems.getSelectedItems().get(i).getTemplate()))
                .filter(item1 -> item1.getInPack() == 0).count();
        boolean doSecondOption = listOfItems.getSelectedItems().get(i).getTemplate().isCanBeRotated90Degrees() &&
                !listOfItems.getSelectedItems().get(i).getTemplate().isPreferNotToBeRotated();
        if (doSecondOption) {
            quantityOfItems = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(i).getTemplate().getLength();
        } else {
            quantityOfItems = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(i).getTemplate().getWidth();
        }
        numberOfRows = quantity / quantityOfItems;
        numberOfRows += quantity % quantityOfItems != 0 ? 1 : 0;

        if (doSecondOption) {
            return listOfItems.getSelectedItems().get(i).getTemplate().getWidth() * numberOfRows;
        } else {
            return listOfItems.getSelectedItems().get(i).getTemplate().getLength() * numberOfRows;
        }
    }

    //TODO průběh metody
    //zjistí počet itemu
    //spočitá zda se všechny vejdou
    //když ne, sníží počet, tak aby se vešly,
    //přidá similar item a opět spočitá jak otočit.
    public void createPacks(Trailer trailer, ListOfItems listOfItems) {
        int pack = 1, totalTakenLength = 0;
        for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
            Item item = listOfItems.getSelectedItems().get(i);
            List<Item> similarItems;
            int /*quantity, quantityOfItemsWidth, quantityOfItemsLength = 0, numberOfRowsW, numberOfRowsL,*/ w, l = 0, freeSpace;
//            quantity = (int) listOfItems.getSelectedItems().stream().filter(item1 -> item1.getTemplate().equals(item.getTemplate()))
//                    .filter(item1 -> item1.getInPack() == 0).count();
//
//            quantityOfItemsWidth = trailer.getTemplate().getWidth() / item.getTemplate().getWidth();
//            numberOfRowsW = quantity / quantityOfItemsWidth;
//            numberOfRowsW += quantity % quantityOfItemsWidth != 0 ? 1 : 0;
//            w = item.getTemplate().getLength() * numberOfRowsW;
            w = findSolutionHowToLoad(trailer, listOfItems, i);
          //  freeSpace = trailer.getTemplate().getWidth() - (quantityOfItemsWidth * item.getTemplate().getWidth());
//            if (item.getTemplate().isCanBeRotated90Degrees() &&
//                    !item.getTemplate().isPreferNotToBeRotated()) {
//                quantityOfItemsLength = trailer.getTemplate().getWidth() / item.getTemplate().getLength();
//                numberOfRowsL = quantity / quantityOfItemsLength;
//                numberOfRowsL += quantity % quantityOfItemsLength != 0 ? 1 : 0;
//                l = item.getTemplate().getWidth() * (numberOfRowsL);//2
            l = findSolutionHowToLoad(trailer, listOfItems, i);
//            }
            if (l == 0) {
                l = item.getTemplate().getLength();
            }
            while (w > trailer.getTemplate().getLength() - totalTakenLength || l > trailer.getTemplate().getLength() - totalTakenLength) {
                // quantity -= 1;

            }
            if (w <= l) {
                if (totalTakenLength + listOfItems.getSelectedItems().get(i).getTemplate().getLength() <= trailer.getTemplate().getLength()) {

                    if (quantity < quantityOfItemsWidth) {
                        quantityOfItemsWidth = quantity;
                    }
                    similarItems = returnSimilarItems(i, listOfItems, freeSpace, false);
                    if (similarItems.isEmpty()) {
                        for (int j = i; j < i + quantityOfItemsWidth; j++) {
                            listOfItems.getSelectedItems().get(j).setInPack(pack);
                        }
                    }
                } else {

                }
                totalTakenLength += listOfItems.getSelectedItems().get(i).getTemplate().getLength();
                i += quantityOfItemsWidth - 1;
            } else if (totalTakenLength + listOfItems.getSelectedItems().get(i).getTemplate().getWidth() <= trailer.getTemplate().getLength()) {
                if (quantity < quantityOfItemsLength) {
                    quantityOfItemsLength = quantity;
                }
              //  freeSpace = trailer.getTemplate().getWidth() - (quantityOfItemsLength * item.getTemplate().getLength());
                similarItems = returnSimilarItems(i, listOfItems, freeSpace, true);
                if (similarItems.isEmpty()) {
                    for (int j = i; j < i + quantityOfItemsLength; j++) {
                        listOfItems.getSelectedItems().get(j).setTurnItem90Degrees(true);
                        listOfItems.getSelectedItems().get(j).setInPack(pack);
                    }
                } else {

                }
                totalTakenLength += listOfItems.getSelectedItems().get(i).getTemplate().getWidth();
                i += quantityOfItemsLength - 1;
            }
            pack++;
        }
    }

    private List<Item> returnSimilarItems(int indexOfItem, ListOfItems listOfItems, int freeSpace, boolean turn) {
        List<Item> list = listOfItems.getSelectedItems().stream().filter(item -> !item.getTemplate().equals(listOfItems.getSelectedItems().get(indexOfItem).getTemplate())).collect(Collectors.toList());
//        if (turn) {
        list = list.stream().filter(item -> item.getTemplate().getWidth() <= freeSpace).
                filter(item -> item.getTemplate().getLength() <= freeSpace)
                .collect(Collectors.toList());

//        } else {
//            list = list.stream().filter(item -> item.getTemplate().getLength() == listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength()).
//                    filter(item -> item.getTemplate().getWidth() <= freeSpace).sorted()
//                    .collect(Collectors.toList());
//        }
        return list;
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
        listOfItems.getLoadedItems().add(new Item(listOfItems.getSelectedItems().get(indexOfItem).getTemplate(), trailer.getNextCodename(), cX, cY, listOfItems.getSelectedItems().get(indexOfItem).isTurnItem90Degrees()));
        listOfItems.getSelectedItems().remove(indexOfItem);
        trailer.setFreeSquareCentimeters(trailer.getFreeSquareCentimeters() - listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength() * listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth());
        trailer.setNextCodename((char) (trailer.getNextCodename() + 1));
        //System.out.println("Coordinates X: " + loadedGoods.get(loadedGoods.size() - 1).getX() + ",Y: " + loadedGoods.get(loadedGoods.size() - 1).getY() + " Added! Goods: " + loadedGoods.get(loadedGoods.size() - 1).getName());
    }


    private boolean searchFreeSpaceOnTrailer(int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        if (listOfItems.getLoadedItems().size() != 0) {
            for (int y = 0; y < trailer.getTemplate().getLength(); y++) {
                for (int x = 0; x < trailer.getTemplate().getWidth(); ) {
                    for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
                        if (x >= listOfItems.getLoadedItems().get(i).getX() && x <= listOfItems.getLoadedItems().get(i).getX() + listOfItems.getLoadedItems().get(i).getTemplate().getWidth() - 1 && y >= listOfItems.getLoadedItems().get(i).getY() && y <= listOfItems.getLoadedItems().get(i).getY() + listOfItems.getLoadedItems().get(i).getTemplate().getLength() - 1) {
                            x += listOfItems.getLoadedItems().get(i).getTemplate().getWidth();
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


    private boolean doesCurrentItemFitForTheseCoordinates(int x, int y, int indexOfGoods, ListOfItems listOfItems, Trailer trailer) {
        ItemTemplate itemTemplate = listOfItems.getSelectedItems().get(indexOfGoods).getTemplate();

        if (itemTemplate.getWidth() + x <= trailer.getTemplate().getWidth() && itemTemplate.getLength() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker(x, (x + itemTemplate.getWidth()), y, (y + itemTemplate.getLength()), listOfItems)) {
                return true;
            }
        }
        if (itemTemplate.isCanBeRotated90Degrees() && itemTemplate.getLength() + x <= trailer.getTemplate().getWidth() && itemTemplate.getWidth() + y <= trailer.getTemplate().getLength()) {
            if (freeCoordinatesChecker(x, (x + itemTemplate.getLength()), y, (y + itemTemplate.getWidth()), listOfItems)) {
                listOfItems.getSelectedItems().get(indexOfGoods).setTurnItem90Degrees(true);
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean freeCoordinatesChecker(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd, ListOfItems listOfItems) {
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