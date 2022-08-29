package cz.comkop.shipingmanager;

public class Main {
    static ConsoleUI consoleUI = new ConsoleUI();
    static ItemTemplateList itemTemplates = new ItemTemplateList();
    static TrailerTemplateList trailerTemplates = new TrailerTemplateList();
    static RequiredItemList requiredItems = new RequiredItemList();
    static ReadyToLoadItemList readyToLoadItems = new ReadyToLoadItemList();
    static SelectedItems selectedItems = new SelectedItems();
    static RemovedItems removedItems = new RemovedItems();


    public static void main(String[] args) {

        consoleUI.welcome();
        do {
            consoleUI.selectionOfTrailer(trailerTemplates.getTrailerTemplates());
            do {
                consoleUI.selectionOfItems(itemTemplates.getItemTemplates());
                requiredItems.searchItemsFromInput(itemTemplates, consoleUI.getItemsChoice();
                consoleUI.printSelectedItems(requiredItems.getRequiredItems());
                System.out.println("--Write \"a\" to select items again or press enter to continue.--");
            } while (consoleUI.userSelection());
            //readyToLoadItems..createSelectedItems(ListOfItems.TEMPLATE.getItems());
            requiredItems.searchItemsFromInput(itemTemplates.getItemTemplates(), consoleUI.getItemsChoice());
            readyToLoadItems.addItems(requiredItems, consoleUI.getTrailerChoice());//zde

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