package cz.comkop.shipingmanager;

public class Converter {
    //TODO ususally we are using classes with postfix Util means Utility pattern for classes with only static methods

    public static double metersFromCentimeters(int centimeters) {
        return (double) centimeters / 100;
    }
}
