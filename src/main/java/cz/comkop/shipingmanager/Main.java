package cz.comkop.shipingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();

    static SelectedItems selectedItems = new SelectedItems();
    static RemovedItems removedItems = new RemovedItems();


    public static void main(String[] args) {


        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer(Trailers.TEMPLATE);
            do {
                consoleUI.selectionOfItems(ListOfItems.TEMPLATE.getItems());
                selectedItems.searchItemsFromInput(ListOfItems.TEMPLATE.getItems(), consoleUI.getItemsChoice());
                consoleUI.printSelectedItems(ListOfItems.TEMPLATE.getItems());
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            selectedItems.createSelectedItems(ListOfItems.TEMPLATE.getItems());
            selectedItems.sorting(Trailers.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()));
            Trailers.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()).loading(selectedItems, removedItems);
            consoleUI.printDebugReport(Trailers.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()));
            consoleUI.printEmailReport(Trailers.TEMPLATE.getTrailers(), removedItems);
            System.out.println("--Write \"a\" to start again or write another key to finish.--");
            reset();
        } while (consoleUI.userSelection());


    }

    private static void reset() {
       //new trailers, list of items
        Trailers.reset();
        selectedItems = new SelectedItems();
        removedItems = new RemovedItems();
    }
}