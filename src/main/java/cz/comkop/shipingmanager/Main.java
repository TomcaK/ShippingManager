package cz.comkop.shipingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();
    static ItemTemplateList itemTemplates = new ItemTemplateList();
    static TrailerTemplateList trailerTemplates = new TrailerTemplateList();

    static SelectedItems selectedItems = new SelectedItems();
    static RemovedItems removedItems = new RemovedItems();


    public static void main(String[] args) {

        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer(trailerTemplates.getTrailerTemplates());
            do {
                consoleUI.selectionOfItems(itemTemplate.getItemTemplates());
                selectedItems.searchItemsFromInput(ListOfItems.TEMPLATE.getItems(), consoleUI.getItemsChoice());
                consoleUI.printSelectedItems(ListOfItems.TEMPLATE.getItems());
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            selectedItems.createSelectedItems(ListOfItems.TEMPLATE.getItems());
            selectedItems.sorting(TrailerTemplate.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()));
            TrailerTemplate.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()).loading(selectedItems, removedItems);
            consoleUI.printDebugReport(TrailerTemplate.TEMPLATE.getTrailers().get(consoleUI.getTrailerChoice()));
            consoleUI.printEmailReport(TrailerTemplate.TEMPLATE.getTrailers(), removedItems);
            System.out.println("--Write \"a\" to start again or write another key to finish.--");
            reset();
        } while (consoleUI.userSelection());


    }

    private static void reset() {
       //new trailers, list of items
        TrailerTemplate.reset();
        selectedItems = new SelectedItems();
        removedItems = new RemovedItems();
    }
}