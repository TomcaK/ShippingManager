package cz.comkop.shipingmanager;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoadTrailer {

    private int coordinateY;
    private int coordinateX;


    public void loading(ListOfItems listOfItems, Trailer trailer) {//TODO check of height, //TODO tvorba algoritmu, který se primárně zaměří na využití celé šířky auta.
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
        int round = 1;


        while (listOfItems.getSelectedItems().size() != 0) {
            if (round <= 1) {
                createPacks( trailer, listOfItems);
                for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {







                }
           } else {
                for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {
                    if (searchFreeSpaceOnTrailer(i, listOfItems, trailer)) {
                        addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                        i--;
                    } else {
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

    public void createPacks( Trailer trailer, ListOfItems listOfItems) {
        int count = 1, index = 0;
        for (int i = 0; i < listOfItems.getSelectedItems().size(); i++) {

            Item item = listOfItems.getSelectedItems().get(i);
            List<Item> similarItems;
            //dostávám katanu
            int quantity, quantityOfItemsWidth, quantityOfItemsLength = 0, deviation = 15, restW, restL, numberOfRowsW, numberOfRowsL, w, l = 0, freeSpace;
            quantity = listOfItems.getRequiredItems().get(item.getTemplate());
            quantityOfItemsWidth = trailer.getTemplate().getWidth() / item.getTemplate().getWidth();
            //   numberOfRowsW = quantity / quantityOfItemsWidth;
            // restW = quantity % quantityOfItemsWidth;
            // numberOfRowsW += quantity % quantityOfItemsWidth != 0 ? 1 : 0;
            w = item.getTemplate().getLength();
            freeSpace = trailer.getTemplate().getWidth() - (quantityOfItemsWidth * item.getTemplate().getWidth());


            if (item.getTemplate().isCanBeRotated90Degrees() &&
                    !item.getTemplate().isPreferedNotToBeRotated()) {

                quantityOfItemsLength = trailer.getTemplate().getWidth() / item.getTemplate().getLength();
                numberOfRowsL = quantity / quantityOfItemsLength;
                // restL = quantity % quantityOfItemsLength;
                numberOfRowsL += quantity % quantityOfItemsLength != 0 ? 1 : 0;
                l = item.getTemplate().getWidth() * (numberOfRowsL);//2
            }
            if (l == 0) {
                l = item.getTemplate().getLength();
            }


            if (w < l) {
                if (quantity < quantityOfItemsWidth) {
                    quantityOfItemsWidth = quantity;
                }
                similarItems = returnSimilarItems(i, listOfItems, freeSpace, false);
                if (similarItems.isEmpty()) {
                    for (int j = i; j < i + quantityOfItemsWidth; j++) {
                        listOfItems.getSelectedItems().get(j).setLoadFirst(true);
                        addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                        coordinateX += item.getTemplate().getWidth();
                    }
                    coordinateX = 0;
                    coordinateY += item.getTemplate().getLength();
                } else {

                }

            } else {
                if (quantity < quantityOfItemsLength) {
                    quantityOfItemsLength = quantity;
                }

                freeSpace = trailer.getTemplate().getWidth() - (quantityOfItemsLength * item.getTemplate().getLength());

                similarItems = returnSimilarItems(i, listOfItems, freeSpace, true);
                if (similarItems.isEmpty()) {
                    for (int j = i; j < i + quantityOfItemsLength; j++) {
                        listOfItems.getSelectedItems().get(j).setTurnItem90Degrees(true);
                        listOfItems.getSelectedItems().get(j).setLoadFirst(true);
                        addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                        coordinateX += item.getTemplate().getLength();
                    }
                    coordinateX = 0;
                    coordinateY += item.getTemplate().getWidth();
                } else {

                }

            }
        /*else {
            for (int i = indexOfItem; i < indexOfItem + quantity; i++) {
                addItemToTrailer(i, coordinateX, coordinateY, listOfItems, trailer);
                coordinateX += item.getTemplate().getLength();
            }
            similarItems = returnSimilarItem(indexOfItem, listOfItems, freeSpace,true);
            if (similarItems.isEmpty()){
                coordinateX = 0;
                coordinateY += item.getTemplate().getLength();
            }else {

            }


        }*/


//           while (width <= trailerTemplate.getWidth()) {
//               List<Item> similarItems = selectedItems.stream().filter(item -> item.getTemplate().getWidth() <= width )
//                width += selectedItems.get(i).getTemplate().getWidth();
//            }
            i--;
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