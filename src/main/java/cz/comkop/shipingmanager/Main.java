package cz.comkop.shipingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();
    static ListOfItems listOfItems = new ListOfItems();
    static Trailer trailer;
    static LoadTrailer loadTrailer = new LoadTrailer();
    //static RequiredItemList requiredItems = new RequiredItemList();

//TODO test 19.1 11.1 26.1 32.1 33.1 34.1 35.2 36.1 37.1 38.1 29.2
    public static void main(String[] args) {

        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer();
            trailer = new Trailer(consoleUI.getTrailerChoice());
            do {
                consoleUI.selectionOfItems();
                listOfItems.getItemsFromInput(consoleUI.getUserChoice());
                consoleUI.printRequiredItems(listOfItems);
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            listOfItems.createSelectedItems();
            listOfItems.sortSelectedItemsByArea();
            listOfItems.selectItemsToLoadLater(consoleUI.getTrailerChoice());
            loadTrailer.firstLoading(listOfItems, trailer);
            consoleUI.printDebugReport(trailer, listOfItems);
            consoleUI.printFinalReport(trailer, listOfItems);
            consoleUI.printEmailData(trailer, listOfItems);
            System.out.println("--Write \"a\" to start again or write another key to finish.--");
            reset();
        } while (consoleUI.userSelection());


    }

    private static void reset() {
        //new trailers, list of items

        listOfItems = new ListOfItems();

    }
}