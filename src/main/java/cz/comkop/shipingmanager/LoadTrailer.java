package cz.comkop.shipingmanager;


import java.util.List;
import java.util.stream.Collectors;

public class LoadTrailer {

    private int checkpointY;
    private int checkpointX;


    public void loading(ListOfItems listOfItems, Trailer trailer) {//TODO check of height, //TODO tvorba algoritmu, který se primárně zaměří na využití celé šířky auta.
        int round = 0;
        while (listOfItems.getSelectedItems().size() != 0) {
            for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
               // createPackOfItems(listOfItems, trailer, i);

                if (/*searchFreeSpaceCharacterOnTrailer(i, selectedItems)*/ searchFreeSpaceOnTrailer(i, listOfItems, trailer)) {
                    addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
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
        int round = 0, lastPack = 0;
        for (Item item:listOfItems.getSelectedItems()) {
            if (lastPack < item.getInPack()){
                lastPack = item.getInPack();
            }
        }

        while (listOfItems.getSelectedItems().size() != 0) {
            for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                              if ( searchFreeSpaceOnTrailer(i, listOfItems, trailer)) {
                    addItemToTrailer(i, checkpointX, checkpointY, listOfItems, trailer);
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

    private void createPackOfItems(int x, int y, ListOfItems listOfItems, Trailer trailer, int indexOfItem) {
        int quantity, numberOfItemsWidth, numberOfItemsLength = 0, packW = 0, packL = 0,deviation = 10, restW, restL;
        quantity = listOfItems.getRequiredItems().get(listOfItems.getSelectedItems().get(indexOfItem).getTemplate());

        if (listOfItems.getSelectedItems().get(indexOfItem).getTemplate().isCanBeRotated90Degrees() &&
                !listOfItems.getSelectedItems().get(indexOfItem).getTemplate().isPreferedNotToBeRotated()) {
            numberOfItemsLength = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength();//2
            packL = quantity / numberOfItemsLength;
        }




        // rest = quantity % pack;
        numberOfItemsWidth = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getWidth();
        packW = quantity / numberOfItemsWidth;

        if (listOfItems.getSelectedItems().get(indexOfItem).getTemplate().isCanBeRotated90Degrees() &&
                !listOfItems.getSelectedItems().get(indexOfItem).getTemplate().isPreferedNotToBeRotated()) {
            numberOfItemsLength = trailer.getTemplate().getWidth() / listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength();//2
            packL = quantity / numberOfItemsLength;
        }

        int numberOfRowsW, numberOfRowsL, w, l;

        //3 kolik se vejde itemu na sirku do navesu
        numberOfRowsW = quantity / numberOfItemsWidth;
        restW = quantity % numberOfItemsWidth;
        numberOfRowsL = quantity / numberOfItemsLength;
        restL = quantity % numberOfItemsLength;
        restW = restW != 0 ? 1 : 0;
        restL = restL != 0 ? 1 : 0;
        w = listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getLength() * (numberOfRowsW + restW );
        l = listOfItems.getSelectedItems().get(indexOfItem).getTemplate().getWidth() * (numberOfRowsL + restL);

        if (w < l) {
            for (int i = 0; i < quantity * numberOfRowsW; i++) {

                addItemToTrailer(0, checkpointX, checkpointY, listOfItems, trailer);
                checkpointX += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getWidth();
            }
        } else {
            for (int i = 0; i < quantity * numberOfRowsL; i++) {
                listOfItems.getSelectedItems().get(0).setTurnItem90Degrees(true);
                addItemToTrailer(0,  checkpointX, checkpointY, listOfItems, trailer);
                checkpointX += listOfItems.getLoadedItems().get(listOfItems.getLoadedItems().size() - 1).getTemplate().getLength();
            }
        }

        List<Item> similarItems = listOfItems.getSelectedItems().stream().filter(item -> item.getTemplate().getWidth() > item.getTemplate().getWidth() + deviation || item.getTemplate().getWidth() > item.getTemplate().getWidth() + deviation).collect(Collectors.toList());

    }

//    public void firstLoading(ListOfItems listOfItems, Trailer trailer) {//TODO vyresit problem 2 palety 120x80
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
        listOfItems.getLoadedItems().add(new Item(listOfItems.getSelectedItems().get(indexOfItem).getTemplate(), trailer.getNextCodename(), cX, cY));
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