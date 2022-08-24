package cz.comkop.shipingmanager;

import java.util.ArrayList;

public class Trailer {

    private final String freeSpaceChar = "-";
    /**
     * Holds centimeter sized model of trailer
     */
    private final String[][] trailerModel;

    private final ArrayList<Item> loadedItems = new ArrayList<>();
    private String name = "";
    private char fullSpaceChar = 'A';
    private int totalWidth = 0;
    private int totalLength = 0;
    private double freeSquareCentimeters;
    private double LDM, freeLDM;
    private int totalWeight = 0;
    private int checkpointX = 0;
    private int checkpointY = 0;


    public Trailer(String name, int totalWidth, int totalLength) {
        this.name = name;
        this.totalWidth = totalWidth;
        this.totalLength = totalLength;
        freeSquareCentimeters = totalLength * totalWidth;
        trailerModel = new String[totalLength][totalWidth];
        for (int i = 0; i < trailerModel.length; i++) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (trailerModel[i][j] != null) {
                    trailerModel[i][j] += freeSpaceChar;
                } else {
                    trailerModel[i][j] = freeSpaceChar;
                }
            }
        }
    }

    /**
     * Method adds goods, which will fit on the trailer, to set coordinates
     *
     * @param indexOfGoods gives index of selected goods which was controlled
     * @param cX           gives X coordinate where to put goods in trailer
     * @param cY           gives X coordinate where to put goods in trailer
     **/
    private void addItemToTrailer(int indexOfGoods, int cX, int cY, SelectedItems selectedItems) {
        int maxLength = cY + selectedItems.getSelectedItems().get(indexOfGoods).getLength();
        int maxWidth = cX + selectedItems.getSelectedItems().get(indexOfGoods).getWidth();
        if (selectedItems.getSelectedItems().get(indexOfGoods).isTurnGoods()) {
            maxLength = cY + selectedItems.getSelectedItems().get(indexOfGoods).getWidth();
            maxWidth = cX + selectedItems.getSelectedItems().get(indexOfGoods).getLength();
        }
        for (int y = cY; y < maxLength; y++) {
            for (int x = cX; x < maxWidth; x++) {
                trailerModel[y][x] = String.valueOf(fullSpaceChar);
            }
        }
        totalWeight += selectedItems.getSelectedItems().get(indexOfGoods).getWeight();
        setFieldsOfLoadedGoods(indexOfGoods, cX, cY, selectedItems);
        freeSquareCentimeters -= loadedItems.get(loadedItems.size() - 1).getLength() * loadedItems.get(loadedItems.size() - 1).getWidth();
        fullSpaceChar++;
        //System.out.println("Coordinates X: " + loadedGoods.get(loadedGoods.size() - 1).getX() + ",Y: " + loadedGoods.get(loadedGoods.size() - 1).getY() + " Added! Goods: " + loadedGoods.get(loadedGoods.size() - 1).getName());
    }

    private void setFieldsOfLoadedGoods(int indexOfGoods, int cX, int cY, SelectedItems selectedItems) {
        loadedItems.add(selectedItems.getSelectedItems().get(indexOfGoods));
        loadedItems.get(loadedItems.size() - 1).setX(cX);
        loadedItems.get(loadedItems.size() - 1).setY(cY);
        loadedItems.get(loadedItems.size() - 1).setFullCharName(fullSpaceChar);
        selectedItems.getSelectedItems().remove(indexOfGoods);
    }

    /**
     * Method calculates size in square centimeters unit
     *
     * @param indexOfGoods
     * @return true or false
     */
    private boolean isFreeSpaceOnTrailer(int indexOfGoods, SelectedItems selectedItems) {
        return selectedItems.getSelectedItems().get(indexOfGoods).getWidth() * selectedItems.getSelectedItems().get(indexOfGoods).getLength() <= freeSquareCentimeters;
    }


    /**
     * Method searches free space characters in TrailerModel.
     *
     * @return if it is possible to load goods on specific coordinates on the trailer
     **/
    private boolean searchFreeSpaceCharacterOnTrailer(int indexOfGoods, SelectedItems selectedItems) {
        for (int y = 0; y < trailerModel.length; y++) {
            for (int x = 0; x < trailerModel[0].length; x++) {
                if (trailerModel[y][x].equals(freeSpaceChar)) {
                    if (doesCurrentGoodsFitForTheseCoordinates(x, y, indexOfGoods, selectedItems)) {
                        checkpointY = y;
                        checkpointX = x;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Calculates if goods fit from set coordinates to the end of trailer, sets area for searching in square centimeters of goods size and controls every single centimeter if another goods are not loaded.
     * If method finds another goods were put, it tries to turn goods to change dimensions and checks space again.
     *
     * @param x            x coordinate where goods could be put
     * @param y            y coordinate where goods could be put
     * @param indexOfGoods index of selected goods
     * @return true or false
     */
    private boolean doesCurrentGoodsFitForTheseCoordinates(int x, int y, int indexOfGoods, SelectedItems selectedItems) {
        Item item = selectedItems.getSelectedItems().get(indexOfGoods);
        breakHere:
        if (item.getWidth() + x <= totalWidth && item.getLength() + y <= totalLength) {
            if (freeCoordinatesChecker2(x, (x + item.getWidth()), y, (y + item.getLength()))) {
                return true;
            }
            break breakHere;
        }
        if (item.isTurnAble() && item.getLength() + x <= totalWidth && item.getWidth() + y <= totalLength) {
            if (freeCoordinatesChecker2(x, (x + item.getLength()), y, (y + item.getWidth()))) {
                item.setTurnGoods(true);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Method searches character in set coordinates
     *
     * @param coordinateXStart
     * @param coordinateXEnd
     * @param coordinateYStart
     * @param coordinateYEnd
     * @return
     */
    private boolean freeCoordinatesChecker(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd) {
        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
                if (!trailerModel[y][x].equals(freeSpaceChar)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean freeCoordinatesChecker2(int coordinateXStart, int coordinateXEnd, int coordinateYStart, int coordinateYEnd) {
        for (int y = coordinateYStart; y < coordinateYEnd; y++) {
            for (int x = coordinateXStart; x < coordinateXEnd; x++) {
                for (int i = 0; i < loadedItems.size(); i++) {
                    if (x >= loadedItems.get(i).getX() && x <= loadedItems.get(i).getX() + loadedItems.get(i).getWidth() - 1 && y >= loadedItems.get(i).getY() && y <= loadedItems.get(i).getY() + loadedItems.get(i).getLength() - 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean searchFreeSpaceOnTrailer2(SelectedItems selectedItems, int indexOfGoods) {
        if (loadedItems.size() != 0) {
            for (int y = 0; y < totalLength; y++) {
                for (int x = 0; x < totalWidth; ) {
                    for (int i = 0; i < loadedItems.size(); i++) {
                        if (x >= loadedItems.get(i).getX() && x <= loadedItems.get(i).getX() + loadedItems.get(i).getWidth() - 1 && y >= loadedItems.get(i).getY() && y <= loadedItems.get(i).getY() + loadedItems.get(i).getLength() - 1) {
                            x += loadedItems.get(i).getWidth();
                        }
                    }
                    if (doesCurrentGoodsFitForTheseCoordinates(x, y, indexOfGoods, selectedItems)) {
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

    public ArrayList<Item> getLoadedItems() {
        return loadedItems;
    }

    public void loading(SelectedItems selectedItems, RemovedItems removedItems) {
        int round = 0;
        while (selectedItems.getSelectedItems().size() != 0) {
            for (int i = 0; i < selectedItems.getSelectedItems().size(); i++) {
                if (!selectedItems.getSelectedItems().get(i).isLoadLast() || selectedItems.getSelectedItems().get(i).isLoadLast() && round > 0) {
                    if (isFreeSpaceOnTrailer(i, selectedItems) && /*searchFreeSpaceCharacterOnTrailer(i, selectedItems)*/ searchFreeSpaceOnTrailer2(selectedItems, i)) {
                        addItemToTrailer(i, checkpointX, checkpointY, selectedItems);
                        i--;
                    } else if (round == 2) {
                        removedItems.getRemovedItems().add(selectedItems.getSelectedItems().get(i));
                        selectedItems.getSelectedItems().remove(i);
                        i--;
                    }
                }
            }
            round++;
        }
        countLDM();
    }

    public double getLDM() {
        return LDM;
    }

    /**
     * Counts total length used in trailer
     **/
    private void countLDM() {
        double count = 0;
        for (String[] strings : trailerModel) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!strings[j].contains("-")) {
                    count++;
                    break;
                }
            }
        }
        LDM = count / 100;
        freeLDM = (double) totalLength / 100 - LDM;
    }


    public int getTotalWeight() {
        return totalWeight;
    }

    public double getFreeLDM() {
        return freeLDM;
    }


    public void printOutlineOfTrailer() {
        String usedCharnames = "";
        String foundCharnames = "";
        int count = 0;
        for (int i = 0; i < trailerModel.length; i++) {
            for (int j = 0; j < trailerModel[0].length; j++) {
                if (!usedCharnames.contains(trailerModel[i][j]) && !trailerModel[i][j].equals("-")) {
                    usedCharnames += trailerModel[i][j];
                    foundCharnames += trailerModel[i][j];
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

    public void printOutlineOfTrailer2() {
        String usedCharnames = "";
        String foundCharnames = "";
        Item[][] outlineItems = new Item[loadedItems.size()][loadedItems.size()];
        ArrayList<String> outline = new ArrayList<>();
        int countYDifference = 0;
        int xS = 0;
        int xE = 0;
        int yS = 0;
        int yE = 0;
        int previousY = 0;
        for (int i = 0; i < loadedItems.size(); i++) {
            if (i == 0) {
                outline.add(String.valueOf(loadedItems.get(0).getFullCharName()));
                usedCharnames += "A";
            } else if (loadedItems.get(i).getY() > previousY) {
                outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
                previousY = loadedItems.get(i).getY();
                usedCharnames += loadedItems.get(i).getFullCharName();
            }
        }

        for (int i = 0; i < outline.size(); i++) {
            for (int j = 0; j < loadedItems.size(); j++) {
                if (outline.get(i).equals(String.valueOf(loadedItems.get(j).getFullCharName()))) {
                    xS = loadedItems.get(j).getX();
                    yS = loadedItems.get(j).getY();
                    xE = xS + loadedItems.get(j).getX() - 1;
                    yE = yE + loadedItems.get(j).getY() - 1;
                    for (int k = j + 1; k < loadedItems.size(); k++) {
                        if (xS >= loadedItems.get(i).getX() && xE <= loadedItems.get(i).getX() + loadedItems.get(i).getWidth() - 1 && yS >= loadedItems.get(i).getY() && yE <= loadedItems.get(i).getY() + loadedItems.get(i).getLength() - 1) {
                            if (loadedItems.get(i).getY() > yS) {

                            } else if (loadedItems.get(i).getY() == yS) {
                                //  outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
                            } else {

                            }
                        } else if (loadedItems.get(i).getX() == xS) {
                            if (loadedItems.get(i).getY() > yS) {
                                // outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
                            } else if (loadedItems.get(i).getY() == yS) {

                            } else {

                            }
                        } else {

                        }

                    }
                }

            }
        }
//
//
//
//        for (int i = 0; i < loadedItems.size(); i++) {
//            if (i != 0) {
//                if (loadedItems.get(i).getX() > xS) {
//                    if (loadedItems.get(i).getY() > yS) {
//
//                    } else if (loadedItems.get(i).getY() == yS) {
//                      //  outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
//                    } else {
//
//                    }
//                } else if (loadedItems.get(i).getX() == xS) {
//                    if (loadedItems.get(i).getY() > yS) {
//                       // outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
//                    } else if (loadedItems.get(i).getY() == yS) {
//
//                    } else {
//
//                    }
//                } else {
//                    if (loadedItems.get(i).getY() > yS) {
//                       // outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
//                    } else if (loadedItems.get(i).getY() == yS) {
//                    } else {
//
//                    }
//                }
//            } else {
//               // outline.add(String.valueOf(loadedItems.get(i).getFullCharName()));
//                xS = loadedItems.get(i).getX();
//                yS = loadedItems.get(i).getY();
//                xE = xS + loadedItems.get(i).getWidth() - 1;
//                yE = yS + loadedItems.get(i).getLength() - 1;
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
    }


    public String[][] getTrailerModel() {
        return trailerModel;
    }

    public double getFreeSquareCentimeters() {
        return freeSquareCentimeters;
    }

    public String getName() {
        return name;
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalLength() {
        return totalLength;
    }


}