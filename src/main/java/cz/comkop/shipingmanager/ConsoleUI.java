package cz.comkop.shipingmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private int trailerChoice;//TODO consider use of enum
    private String userInput;
    private String itemsChoice;
    private Scanner scanner = new Scanner(System.in);
    private static final String ITEM_REGEX = "([1-9]+\\d*\\.+\\d+ *)+";
    private static final String TRAILER_REGEX = "[1-5]+";
    private Matcher matcher;//TODO any reason to use package level visibility?


    public void welcome() {
        System.out.println("--Welcome to Shiping manager--");
    }


    public boolean userSelection() {

        userInput = scanner.nextLine();
        return switch (userInput) {
            case "a" -> true;
            default -> false;
        };
    }


    /**
     * Method prints list of available trailer and takes input from user and
     *
     * @param trailers
     */
    public void selectionOfTrailer(Trailers trailers) {
        double w, l;
        trailerChoice = 0;
        System.out.println("--Please select trailer to be loaded--");
        for (int i = 0; i < trailers.getTrailers().size(); i++) {
            System.out.println((i + 1) + "." + trailers.getTrailers().get(i).getName());
        }
        trailerChoice = Integer.parseInt(inputControl(TRAILER_REGEX)) - 1;
        w = Converter.metersFromCentimeters(trailers.getTrailers().get(trailerChoice).getTotalWidth());
        l = Converter.metersFromCentimeters(trailers.getTrailers().get(trailerChoice).getTotalLength());
        System.out.println("* Selected trailer: " + trailers.getTrailers().get(trailerChoice).getName() + "," +
                " width: " + w + " m, length: " +
                l + " m *");
    }


    /**
     * Methods takes input from user, controls it and then creates list of goods which should be loaded
     *
     * @param itemTemplates
     */
    public void selectionOfItems() {

        List<ItemTemplate> items = new ItemTemplateList().getItemTemplates();
        System.out.println("--Please select goods and insert numbers of pieces in format \"(position number of goods).(how many pieces)\"separated by space, for example 1.2 3.5......\"--");
        System.out.println();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + "." + items.get(i).getName() + "\t");
        }
        itemsChoice = inputControl(ITEM_REGEX);

    }

    public String getItemsChoice() {
        return itemsChoice;
    }

    public void printSelectedItems(ArrayList<ItemTemplate> itemTemplates) {
        System.out.println("* Selected goods *");
        for (int i = 0; i < itemTemplates.size(); i++) {
            if (itemTemplates.get(i).getQuantity() != 0) {
                System.out.println((i + 1) + ". " + itemTemplates.get(i).getName() + ", number of pieces: " + itemTemplates.get(i).getQuantity());
            }
        }
    }

    private String inputControl(String regex) {
        String input;
        Pattern pattern = Pattern.compile(regex);
        input = scanner.nextLine();
        matcher = pattern.matcher(input);
        while (!matcher.matches()) {
            System.out.println("--Wrong format inserted, try again--");
            input = scanner.nextLine();
            matcher = pattern.matcher(input);
        }
        return input;
    }

    /**
     * Method prints info for debugging
     *
     * @param trailer
     */
    public void printDebugReport(Trailer trailer) {
        System.out.println("--REPORT--");
        double centimetersToMeters = trailer.getFreeSquareCentimeters() / 100;
        printTrailerModel(trailer);
        System.out.println("Free meters in trailer: " + centimetersToMeters + ", needed LDM: " + trailer.getLDM() + ", number of pieces: " + trailer.getLoadedItems().size());
        for (int i = 0; i < trailer.getLoadedItems().size(); i++) {
            System.out.println("Goods in coordinates X: " + trailer.getLoadedItems().get(i).getX() + ",Y: " + trailer.getLoadedItems().get(i).getY() + ", " +
                    trailer.getLoadedItems().get(i).getName() + ",code: " + trailer.getLoadedItems().get(i).getFullCharName());
        }
    }

    private static void printRemovedGoods(RemovedItems removedItems) {
        for (ItemTemplate g : removedItems.getRemovedItems()
        ) {
            System.out.println(g.getName() + " NOT LOADED");
        }
    }

    public int getTrailerChoice() {
        return trailerChoice;
    }

    /**
     * Method prints centimeter sized model of trailer
     *
     * @param trailer
     */
    private static void printTrailerModel(Trailer trailer) {
        for (int i = 0; i < trailer.getTrailerModel().length; i++) {
            for (int j = 0; j < trailer.getTrailerModel()[0].length; j++) {
                System.out.print(trailer.getTrailerModel()[i][j]);
            }
            System.out.println();
        }
    }


    public void printEmailReport(ArrayList<Trailer> trailers, RemovedItems removedItems) {
        System.out.println("--Email REPORT--");
        System.out.println("Needed LDM: " + trailers.get(trailerChoice).getLDM() + ", free LDM: " + trailers.get(trailerChoice).getFreeLDM() + ", number of pieces: " + trailers.get(trailerChoice).getLoadedItems().size() +
                ", total weight: " + trailers.get(trailerChoice).getTotalWeight() + " kg");
        System.out.println();
        System.out.println("--List of loaded goods--");
        for (int i = 0; i < trailers.get(trailerChoice).getLoadedItems().size(); i++) {
            System.out.println((i + 1) + ". " + trailers.get(trailerChoice).getLoadedItems().get(i).getName() + ", width: " + trailers.get(trailerChoice).getLoadedItems().get(i).getWidth() + " cm, length: " + trailers.get(trailerChoice).getLoadedItems().get(i).getLength() +
                    " cm, weight: " + trailers.get(trailerChoice).getLoadedItems().get(i).getWeight() + "kg, codename: " + trailers.get(trailerChoice).getLoadedItems().get(i).getFullCharName());
        }
        System.out.println();
        System.out.println("--Probable storage of goods--");
        trailers.get(trailerChoice).printOutlineOfTrailer();
        printRemovedGoods(removedItems);
    }


}