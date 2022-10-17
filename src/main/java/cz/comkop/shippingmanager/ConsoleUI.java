package cz.comkop.shippingmanager;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class ConsoleUI {
    public static final String ITEM_REGEX = "([1-9]+\\d*\\.+\\d+ *)+";
    public static final String TRAILER_REGEX = "[1-6]";
    public static final String LETTERS_REGEX = "[a-zA-Z]+";
    public static final String DATE_REGEX = "\\d+\\.\\d+\\.\\d\\d\\d\\d";
    private Scanner scanner = new Scanner(System.in);
    private String orders;
    private LocalDate shipingDate = LocalDate.now();
    private TrailerTemplate trailerChoice;
    private String userChoice;

    private static void printRemovedGoods(ListOfItems listOfItems) {
        for (Item item : listOfItems.getRemovedItems()
        ) {
            System.out.println(item.getTemplate().getName() + " NOT LOADED");
        }
        System.out.println();
    }


    private static void printTrailerModel(Trailer trailer) {
        for (int i = 0; i < trailer.getTrailerModel().length; i++) {
            for (int j = 0; j < trailer.getTrailerModel()[0].length; j++) {
                System.out.print(trailer.getTrailerModel()[i][j]);
            }
            System.out.println();
        }
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void welcome() {
        System.out.println("--Welcome to Shipping manager--");
    }

    public boolean userSelection() {
        String userInput = scanner.nextLine();
        return userInput.equals("a");
    }


    public void selectionOfTrailer() {
        List<TrailerTemplate> trailerTemplates = Arrays.stream(TrailerTemplate.values()).toList();
        System.out.println("--Please select trailer to be loaded--");
        for (int i = 0; i < trailerTemplates.size(); i++) {
            System.out.println((i + 1) + "." + trailerTemplates.get(i).getName());
        }
        String input = inputControl(scanner.nextLine(), TRAILER_REGEX);
        trailerChoice = trailerTemplates.get(Integer.parseInt(input) - 1);
        System.out.printf("* Selected trailer:  %S \n",trailerChoice);
    }

    public void selectionOfDate() {
        System.out.println("--Please select date of shiping in format \"day.month.year\"--");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        String input = inputControl(scanner.nextLine(), DATE_REGEX);
        shipingDate = LocalDate.parse(input, formatter);
    }

    public void selectionOfOrder() {
        System.out.println("--Please insert customer orders--");
        orders = scanner.nextLine();
    }


    public void selectionOfItems() {

        List<ItemTemplate> itemTemplates = Arrays.stream(ItemTemplate.values()).toList();
        System.out.println("--Please select goods and insert numbers of pieces in format \"(position number of goods).(how many pieces)\"separated by space, for example 1.2 3.5......\"--\n");
        for (int i = 0; i < itemTemplates.size(); i++) {
            System.out.println((i + 1) + "." + itemTemplates.get(i).getName());
        }
        String input = inputControl(scanner.nextLine(), ITEM_REGEX);
        userChoice = input;
    }

    public String getUserChoice() {
        return userChoice;
    }

    public void printRequiredItems(ListOfItems listOfItems) {
        System.out.println("* Selected goods *");
        int i = 1;
        for (ItemTemplate itemTemplate : listOfItems.getRequiredItems().keySet()) {
            System.out.println(i++ + ". " + itemTemplate.getName() + ", number of pieces: " + listOfItems.getRequiredItems().get(itemTemplate));
        }
    }

    public String inputControl(String input, String RegEX) {
//        Pattern pattern = Pattern.compile(LETTERS_REGEX);
//        Matcher matcher = pattern.matcher(input);
//        if (matcher.find()) {
//            input = stringConverter(input);
//        }
        while (!insertRightValue(input, RegEX)) {
            input = scanner.nextLine();
        }
        return input;
    }

    public boolean insertRightValue(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            System.out.println("--Wrong format inserted, try again--");
            return false;
        }
        return true;
    }

//TODO create new search method which will take name of bands and create list of requested items
    public void printDebugReport(Trailer trailer, ListOfItems listOfItems) {
        System.out.println("--REPORT--");
        double centimetersToMeters = trailer.getFreeSquareCentimeters() / 100;
        printTrailerModel(trailer);
        System.out.println("Free meters in trailer: " + centimetersToMeters + ", needed LDM: " + trailer.getLDM() + ", number of pieces: " + listOfItems.getLoadedItems().size());
        for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
            System.out.println("Goods in coordinates X: " + listOfItems.getLoadedItems().get(i).getX() + ",Y: " + listOfItems.getLoadedItems().get(i).getY() + ", " +
                    listOfItems.getLoadedItems().get(i).getTemplate().getName() + ",code: " + listOfItems.getLoadedItems().get(i).getCodeName());
        }
    }

    public TrailerTemplate getTrailerChoice() {
        return trailerChoice;
    }

    public void printEmailData(Trailer trailer, ListOfItems listOfItems) {//TODO doladit výstup ohledně stejných itemů
        System.out.println("--Email Data--");
        System.out.println("Hi,");
        System.out.println("your order " + orders + " will be ready on " + shipingDate.getDayOfWeek() + ", " + shipingDate.format(DateTimeFormatter.ofPattern("d.M.yyyy")));
        Optional<Item> crane = listOfItems.getLoadedItems().stream().filter(item -> item.getTemplate().isLoadingByCrane()).findFirst();
        if (crane.isPresent()) {
            System.out.println("Please pay attention that one of the items is adapted only for crane loading.");
        }
        System.out.println(trailer + ", number of pieces: " + listOfItems.getLoadedItems().size());
        System.out.println("\nList of loaded goods");
        listOfItems.removeDuplicates();
        for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
            String quantity = listOfItems.getRequiredItems().get(listOfItems.getLoadedItems().get(i).getTemplate()) > 1 ? listOfItems.getRequiredItems().get(listOfItems.getLoadedItems().get(i).getTemplate()) + "x " : "";
            System.out.println((i + 1) + ". " + quantity + listOfItems.getLoadedItems().get(i));
        }
    }

    public void printShippingReport(Trailer trailer, ListOfItems listOfItems) {
        System.out.println("--Final REPORT--");
        System.out.println(trailer + ", number of pieces: " + listOfItems.getLoadedItems().size());
        System.out.println("\n--List of loaded goods--");
        for (int i = 0; i < listOfItems.getLoadedItems().size(); i++) {
            String turned = listOfItems.getLoadedItems().get(i).isTurnItem90Degrees() ? ", turned over" : "";
            System.out.println((i + 1) + ". " + listOfItems.getLoadedItems().get(i).getTemplate().getName() + " codename: " + listOfItems.getLoadedItems().get(i).getCodeName() + turned);
        }
        System.out.println("\n--Probable storage of goods--");
        trailer.printOutlineOfTrailer();
        printRemovedGoods(listOfItems);
    }

    public String stringConverter(String input) {
        System.out.println("inside searchItemMethods");
        String[] arr = input.split("\\s");
        List<ItemTemplate> values = Arrays.stream(ItemTemplate.values()).toList();
        StringBuilder stringBuilder = new StringBuilder();
        for (ItemTemplate t : values) {

        }

        String items = null;

        return items;
    }


}