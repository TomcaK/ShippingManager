package cz.comkop.shipingmanager;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final String ITEM_REGEX = "([1-9]+\\d*\\.+\\d+ *)+";
    private static final String TRAILER_REGEX = "[1-5]+";
    private static final String DATE_REGEX = "\\d+\\.\\d+\\.\\d\\d\\d\\d";
    private final Scanner scanner = new Scanner(System.in);
    private String orders,date;
    private LocalDate shipingDate;
    private TrailerTemplate trailerChoice;
    private String userChoice;

    private static void printRemovedGoods(ListOfItems listOfItems) {
        for (Item item : listOfItems.getRemovedItems()
        ) {
            System.out.println(item.getTemplate().getName() + " NOT LOADED");
        }
    }


    private static void printTrailerModel(Trailer trailer) {
        for (int i = 0; i < trailer.getTrailerModel().length; i++) {
            for (int j = 0; j < trailer.getTrailerModel()[0].length; j++) {
                System.out.print(trailer.getTrailerModel()[i][j]);
            }
            System.out.println();
        }
    }

    public void welcome() {
        System.out.println("--Welcome to Shiping manager--");
    }

    public boolean userSelection() {
        String userInput = scanner.nextLine();
        return userInput.equals("a");
    }


    public void selectionOfTrailer() {
        List<TrailerTemplate> trailerTemplates = Arrays.stream(TrailerTemplate.values()).toList();
        System.out.println("--Please select trailer to be loaded--");
        int i = 1;
        for (TrailerTemplate template : trailerTemplates) {
            System.out.println((i++) + "." + template.getName());
        }
        trailerChoice = trailerTemplates.get(Integer.parseInt(inputControl(TRAILER_REGEX)) - 1);
        System.out.println("* Selected trailer: " + trailerChoice);
    }

    public void selectionOfDate() {
        System.out.println("--Please select date of shiping in format \"day.month.year\"--");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        shipingDate = LocalDate.parse(inputControl(DATE_REGEX),formatter);

    }

    public void selectionOfOrder() {
        System.out.println("--Please insert customer orders--");
        orders = scanner.nextLine();
    }


    public void selectionOfItems() {
            List<ItemTemplate> itemTemplates = Arrays.stream(ItemTemplate.values()).toList();
            System.out.println("--Please select goods and insert numbers of pieces in format \"(position number of goods).(how many pieces)\"separated by space, for example 1.2 3.5......\"--");
            System.out.println();
            int i = 1;
            for (ItemTemplate template : itemTemplates) {
                System.out.println((i++) + "." + template.getName());
            }
            userChoice = inputControl(ITEM_REGEX);
    }

    public String getUserChoice() {
        return userChoice;
    }

    public void printRequiredItems(ListOfItems listOfItems) {
        System.out.println("* Selected goods *");
        int i = 1;
        for (ItemTemplate itemTemplate : listOfItems.getRequiredItems().keySet()) {
            System.out.println((i++ + ". " + itemTemplate.getName() + ", number of pieces: " + listOfItems.getRequiredItems().get(itemTemplate)));
        }
    }

    private String inputControl(String regex) {
        String input;
        Pattern pattern = Pattern.compile(regex);
        input = scanner.nextLine();
        Matcher matcher = pattern.matcher(input);
        while (!matcher.matches()) {
            System.out.println("--Wrong format inserted, try again--");
            input = scanner.nextLine();
            matcher = pattern.matcher(input);
        }
        return input;
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

    public void printEmailData(Trailer trailer, ListOfItems listOfItems) {//TODO dodelat datum pro expedici,čísla objednávek,pridat poznamku, o tom, ze nektere stroje vyzaduji manipulaci s jerabem
        System.out.println("--Email Data--");
        System.out.println("Hi,");
        System.out.println("your order " + orders + " will be ready on " + shipingDate.getDayOfWeek() + ", " + shipingDate.format(DateTimeFormatter.ofPattern("d.M.yyyy")));
        System.out.println(trailer + ", number of pieces: " + listOfItems.getLoadedItems().size());
        System.out.println();
        System.out.println("List of loaded goods");
        int i = 1;
        for (Item item : listOfItems.getLoadedItems()) {
            System.out.println((i++) + ". " + item);
        }
    }

    public void printFinalReport(Trailer trailer, ListOfItems listOfItems) {
        System.out.println("--Final REPORT--");
        System.out.println(trailer + ", number of pieces: " + listOfItems.getLoadedItems().size());
        System.out.println();
        System.out.println("--List of loaded goods--");
        int i = 1;
        for (Item item : listOfItems.getLoadedItems()) {
            System.out.println((i++) + ". " + item.getTemplate().getName() + " codename: " + item.getCodeName());
        }
        System.out.println();
        System.out.println("--Probable storage of goods--");
        trailer.printOutlineOfTrailer();
        printRemovedGoods(listOfItems);
    }


}