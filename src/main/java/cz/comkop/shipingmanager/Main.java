package cz.comkop.shipingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();
    static  ListOfItems listOfItems = new ListOfItems();
    //static RequiredItemList requiredItems = new RequiredItemList();



    public static void main(String[] args) {

        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer();
            do {
                consoleUI.selectionOfItems();
                listOfItems.getItemsFromInput(consoleUI.getItemsChoice());
                consoleUI.printRequiredItems(listOfItems);
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            listOfItems.createSelectedItems(consoleUI.getTrailerChoice());
            listOfItems.sortSelectedItemsByArea();
            listOfItems.selectItemsToLoadLater(consoleUI.getTrailerChoice());
            TrailerTemplate.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()).loading(selectedItems, removedItems);
            consoleUI.printDebugReport(TrailerTemplate.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()));
            consoleUI.printEmailReport(TrailerTemplate.TEMPLATE.getTrailers(), removedItems);
            System.out.println("--Write \"a\" to start again or write another key to finish.--");
            reset();
        } while (consoleUI.userSelection());


    }

    private static void reset() {
        //new trailers, list of items

        selectedItems = new SelectedItems();
        removedItems = new RemovedItems();
    }
}