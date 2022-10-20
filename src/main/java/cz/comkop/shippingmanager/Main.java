package cz.comkop.shippingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();
    static ListOfItems listOfItems = new ListOfItems();
    static Trailer trailer;
    static Loading loading = new Loading();

    public static void main(String[] args) {
        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer();
            trailer = new Trailer(consoleUI.getTrailerChoice());
            do {
                consoleUI.selectionOfItems();
                //consoleUI.selectionOfOrder();
                //consoleUI.selectionOfDate();
                listOfItems.getItemsFromInput(consoleUI.getUserChoice());
                consoleUI.printRequiredItems(listOfItems);
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            listOfItems.createSelectedItems();
            loading.createPacks(trailer,listOfItems);
            consoleUI.printDebugReport(trailer, listOfItems);
            consoleUI.printShipingReport(trailer, listOfItems);
            consoleUI.printEmailData(trailer, listOfItems);
            System.out.println("--Write \"a\" to start again or write another key to finish.--");
            reset();
        } while (consoleUI.userSelection());
    }

    private static void reset() {
        loading = new Loading();
        listOfItems = new ListOfItems();

    }
}