package cz.comkop.shipingmanager;



public class Trailer {
    private final TrailerTemplate template;
    /**
     * Holds centimeter sized model of trailer
     */
    private final String[][] trailerModel;

    private char nextCodename = 'A';
    private double freeSquareCentimeters;
    private double LDM, freeLDM;
    private int totalWeight;



    public Trailer(TrailerTemplate template) {
        this.template = template;
        freeSquareCentimeters = template.getLength() * template.getWidth();
        trailerModel = new String[template.getLength()][template.getWidth()];
        for (int i = 0; i < trailerModel.length; i++) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                char freeSpaceChar = '-';
                if (trailerModel[i][j] != null) {
                   trailerModel[i][j] += freeSpaceChar;
                } else {
                    trailerModel[i][j] = String.valueOf(freeSpaceChar);
               }
            }
        }
    }

    public void setFreeSquareCentimeters(double freeSquareCentimeters) {
        this.freeSquareCentimeters = freeSquareCentimeters;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public char getNextCodename() {
        return nextCodename;
    }

    public void setNextCodename(char nextCodename) {
        this.nextCodename = nextCodename;
    }

    /**
     * Method adds goods, which will fit on the trailer, to set coordinates
     *
     * @param indexOfGoods gives index of selected goods which was controlled
     * @param cX           gives X coordinate where to put goods in trailer
     * @param cY           gives X coordinate where to put goods in trailer
     **/
//    private void addItemToTrailer(int indexOfGoods, int cX, int cY, SelectedItems selectedItems) {
//        int maxLength = cY + selectedItems.getSelectedItems().get(indexOfGoods).getLength();
//        int maxWidth = cX + selectedItems.getSelectedItems().get(indexOfGoods).getWidth();
//        if (selectedItems.getSelectedItems().get(indexOfGoods).isTurnGoods()) {
//            maxLength = cY + selectedItems.getSelectedItems().get(indexOfGoods).getWidth();
//            maxWidth = cX + selectedItems.getSelectedItems().get(indexOfGoods).getLength();
//        }
//        for (int y = cY; y < maxLength; y++) {
//            for (int x = cX; x < maxWidth; x++) {
//                trailerModel[y][x] = String.valueOf(nextCodename);
//            }
//        }
//        totalWeight += selectedItems.getSelectedItems().get(indexOfGoods).getWeight();
//        setFieldsOfLoadedGoods(indexOfGoods, cX, cY, selectedItems);
//        freeSquareCentimeters -= loadedItemTemplates.get(loadedItemTemplates.size() - 1).getLength() * loadedItemTemplates.get(loadedItemTemplates.size() - 1).getWidth();
//        nextCodename++;
//        //System.out.println("Coordinates X: " + loadedGoods.get(loadedGoods.size() - 1).getX() + ",Y: " + loadedGoods.get(loadedGoods.size() - 1).getY() + " Added! Goods: " + loadedGoods.get(loadedGoods.size() - 1).getName());
//    }



    /**
     * Method calculates size in square centimeters unit
     *
     * @param indexOfGoods
     * @return true or false
     */
//    private boolean isFreeSpaceOnTrailer(int indexOfGoods, SelectedItems selectedItems) {
//        return selectedItems.getSelectedItems().get(indexOfGoods).getWidth() * selectedItems.getSelectedItems().get(indexOfGoods).getLength() <= freeSquareCentimeters;
//    }


    /**
     * Method searches free space characters in TrailerModel.
     *
     * @return if it is possible to load goods on specific coordinates on the trailer
     **/
//    private boolean searchFreeSpaceCharacterOnTrailer(int indexOfGoods, SelectedItems selectedItems) {
//        for (int y = 0; y < trailerModel.length; y++) {
//            for (int x = 0; x < trailerModel[0].length; x++) {
//                if (trailerModel[y][x].equals(freeSpaceChar)) {
//                    if (doesCurrentGoodsFitForTheseCoordinates(x, y, indexOfGoods, selectedItems)) {
//                        checkpointY = y;
//                        checkpointX = x;
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

//    /**
//     * Calculates if goods fit from set coordinates to the end of trailer, sets area for searching in square centimeters of goods size and controls every single centimeter if another goods are not loaded.
//     * If method finds another goods were put, it tries to turn goods to change dimensions and checks space again.
//     *
//     * @param            x coordinate where goods could be put
//     * @param             y coordinate where goods could be put
//     * @param  index of selected goods
//     * @return true or false
//     */
//    private boolean doesCurrentGoodsFitForTheseCoordinates(int x, int y, int indexOfGoods, SelectedItems selectedItems) {
//        ItemTemplate itemTemplate = selectedItems.getSelectedItems().get(indexOfGoods);
//        breakHere:
//        if (itemTemplate.getWidth() + x <= totalWidth && itemTemplate.getLength() + y <= totalLength) {
//            if (freeCoordinatesChecker2(x, (x + itemTemplate.getWidth()), y, (y + itemTemplate.getLength()))) {
//                return true;
//            }
//            break breakHere;
//        }
//        if (itemTemplate.isCanBeRotated90Degrees() && itemTemplate.getLength() + x <= totalWidth && itemTemplate.getWidth() + y <= totalLength) {
//            if (freeCoordinatesChecker2(x, (x + itemTemplate.getLength()), y, (y + itemTemplate.getWidth()))) {
//                itemTemplate.setTurnGoods(true);
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }

//    /**
//     * Method searches character in set coordinates
//     *
//     * @param coordinateXStart
//     * @param coordinateXEnd
//     * @param coordinateYStart
//     * @param coordinateYEnd
//     * @return
//     */
//    private boolean freeCoordinatesChecker(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd) {
//        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
//            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
//                if (!trailerModel[y][x].equals( freeSpaceChar)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

//    private boolean freeCoordinatesChecker2(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd) {
//        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
//            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
//                for (int i = 0; i < loadedItemTemplates.size(); i++) {
//                    if (x >= loadedItemTemplates.get(i).getX() && x <= loadedItemTemplates.get(i).getX() + loadedItemTemplates.get(i).getWidth() - 1 && y >= loadedItemTemplates.get(i).getY() && y <= loadedItemTemplates.get(i).getY() + loadedItemTemplates.get(i).getLength() - 1) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }

//    private boolean searchFreeSpaceOnTrailer2(SelectedItems selectedItems, int indexOfGoods) {
//        if (loadedItemTemplates.size() != 0) {
//            for (int y = 0; y < totalLength; y++) {
//                for (int x = 0; x < totalWidth; ) {
//                    for (int i = 0; i < loadedItemTemplates.size(); i++) {
//                        if (x >= loadedItemTemplates.get(i).getX() && x <= loadedItemTemplates.get(i).getX() + loadedItemTemplates.get(i).getWidth() - 1 && y >= loadedItemTemplates.get(i).getY() && y <= loadedItemTemplates.get(i).getY() + loadedItemTemplates.get(i).getLength() - 1) {
//                            x += loadedItemTemplates.get(i).getWidth();
//                        }
//                    }
//                    if (doesCurrentGoodsFitForTheseCoordinates(x, y, indexOfGoods, selectedItems)) {
//                        checkpointY = y;
//                        checkpointX = x;
//                        return true;
//                    } else {
//                        x++;
//                    }
//                }
//            }
//            return false;
//        }
//        return true;
//    }



//    public void loading(SelectedItems selectedItems, RemovedItems removedItems) {
//        int round = 0;
//        while (selectedItems.getSelectedItems().size() != 0) {
//            for (int i = 0; i < selectedItems.getSelectedItems().size(); i++) {
//                if (!selectedItems.getSelectedItems().get(i).isLoadLast() || selectedItems.getSelectedItems().get(i).isLoadLast() && round > 0) {
//                    if (isFreeSpaceOnTrailer(i, selectedItems) && /*searchFreeSpaceCharacterOnTrailer(i, selectedItems)*/ searchFreeSpaceOnTrailer2(selectedItems, i)) {
//                        addItemToTrailer(i, checkpointX, checkpointY, selectedItems);
//                        i--;
//                    } else if (round == 2) {
//                        removedItems.getRemovedItems().add(selectedItems.getSelectedItems().get(i));
//                        selectedItems.getSelectedItems().remove(i);
//                        i--;
//                    }
//                }
//            }
//            round++;
//        }
//        countLDM();
//    }

    public double getLDM() {
        return LDM;
    }

    /**
     * Counts total length used in trailer
     **/
    public void countLDM() {
        double count = 0;
        for (String[] strings : trailerModel) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!strings[j].equals("-")) {
                    count++;
                    break;
                }
            }
        }
        LDM = count / 100;
        freeLDM = (double) template.getLength() / 100 - LDM;
    }


    public int getTotalWeight() {
        return totalWeight;
    }



    public void printOutlineOfTrailer() {
        String usedCharnames = "";
        String foundCharnames = "";
        int count = 0;
        for (String[] strings : trailerModel) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!usedCharnames.contains(strings[j]) && !strings[j].equals("-")) {
                    usedCharnames += strings[j];
                    foundCharnames += strings[j];
                    count++;
                }
            }
            if (count > 0) {
                foundCharnames += "\n";
                count = 0;
            }
        }
        System.out.println(foundCharnames);
    }


//    public void printOutlineOfTrailer2() {
//        String usedCharnames = "";
//        String foundCharnames = "";
//        ItemTemplate[][] outlineItemTemplates = new ItemTemplate[loadedItemTemplates.size()][loadedItemTemplates.size()];
//        ArrayList<String> outline = new ArrayList<>();
//        int countYDifference = 0;
//        int xS = 0;
//        int xE = 0;
//        int yS = 0;
//        int yE = 0;
//        int previousY = 0;
//        for (int i = 0; i < loadedItemTemplates.size(); i++) {
//            if (i == 0) {
//                outline.add(String.valueOf(loadedItemTemplates.get(0).getFullCharName()));
//                usedCharnames += "A";
//            } else if (loadedItemTemplates.get(i).getY() > previousY) {
//                outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                previousY = loadedItemTemplates.get(i).getY();
//                usedCharnames += loadedItemTemplates.get(i).getFullCharName();
//            }
//        }
//
//        for (int i = 0; i < outline.size(); i++) {
//            for (int j = 0; j < loadedItemTemplates.size(); j++) {
//                if (outline.get(i).equals(String.valueOf(loadedItemTemplates.get(j).getFullCharName()))) {
//                    xS = loadedItemTemplates.get(j).getX();
//                    yS = loadedItemTemplates.get(j).getY();
//                    xE = xS + loadedItemTemplates.get(j).getX() - 1;
//                    yE = yE + loadedItemTemplates.get(j).getY() - 1;
//                    for (int k = j + 1; k < loadedItemTemplates.size(); k++) {
//                        if (xS >= loadedItemTemplates.get(i).getX() && xE <= loadedItemTemplates.get(i).getX() + loadedItemTemplates.get(i).getWidth() - 1 && yS >= loadedItemTemplates.get(i).getY() && yE <= loadedItemTemplates.get(i).getY() + loadedItemTemplates.get(i).getLength() - 1) {
//                            if (loadedItemTemplates.get(i).getY() > yS) {
//
//                            } else if (loadedItemTemplates.get(i).getY() == yS) {
//                                //  outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                            } else {
//
//                            }
//                        } else if (loadedItemTemplates.get(i).getX() == xS) {
//                            if (loadedItemTemplates.get(i).getY() > yS) {
//                                // outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                            } else if (loadedItemTemplates.get(i).getY() == yS) {
//
//                            } else {
//
//                            }
//                        } else {
//
//                        }
//
//                    }
//                }
//
//            }
//        }
//
//
//
//        for (int i = 0; i < loadedItemTemplates.size(); i++) {
//            if (i != 0) {
//                if (loadedItemTemplates.get(i).getX() > xS) {
//                    if (loadedItemTemplates.get(i).getY() > yS) {
//
//                    } else if (loadedItemTemplates.get(i).getY() == yS) {
//                      //  outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                    } else {
//
//                    }
//                } else if (loadedItemTemplates.get(i).getX() == xS) {
//                    if (loadedItemTemplates.get(i).getY() > yS) {
//                       // outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                    } else if (loadedItemTemplates.get(i).getY() == yS) {
//
//                    } else {
//
//                    }
//                } else {
//                    if (loadedItemTemplates.get(i).getY() > yS) {
//                       // outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                    } else if (loadedItemTemplates.get(i).getY() == yS) {
//                    } else {
//
//                    }
//                }
//            } else {
//               // outline.add(String.valueOf(loadedItemTemplates.get(i).getFullCharName()));
//                xS = loadedItemTemplates.get(i).getX();
//                yS = loadedItemTemplates.get(i).getY();
//                xE = xS + loadedItemTemplates.get(i).getWidth() - 1;
//                yE = yS + loadedItemTemplates.get(i).getLength() - 1;
//            }
//        }


//        for (int i = 0; i < trailerModel.length; i++) {
//            for (int j = 0; j < trailerModel[0].length; j++) {
//                if (!usedCharnames.contains(trailerModel[i][j]) && !trailerModel[i][j].equals("-")) {
//                    usedCharnames += trailerModel[i][j];
//                    foundCharnames += trailerModel[i][j];
//                    count++;
//                }
//            }
//            if (count > 0) {
//                foundCharnames += "\n";
//                count = 0;
//            }
//        }
//        System.out.println(foundCharnames);
   // }


    public String[][] getTrailerModel() {
        return trailerModel;
    }

    public double getFreeSquareCentimeters() {
        return freeSquareCentimeters;
    }

    public TrailerTemplate getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return "Needed LDM: " + LDM + " m, free LDM: " + freeLDM + " m, total weight: " +totalWeight + " kg";
    }
}