package cz.comkop.shipingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shipingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;

public class LoadTrailerTest {
    ListOfItems listOfItems;
    LoadTrailer loadTrailer;
    TrailerTemplate trailer;
    ItemTemplate itemTemplate = PALLET_120X80;
    int positionNumberOfItemTemplate = itemTemplate.ordinal() + 1;
    String input = positionNumberOfItemTemplate + ".2";

    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
        loadTrailer = new LoadTrailer();
        trailer = SEMITRAILER_2_48_M_X_13_6_M;
        listOfItems.getItemsFromInput(input);
        listOfItems.createSelectedItems();
        listOfItems.sortSelectedItemsByArea();

    }


    @Test
    public void testFindSolutionHowToLoadW() {
        int quantityOfItems = trailer.getWidth() / itemTemplate.getWidth();

        int numberOfRows = 1;
        if ((quantityOfItems * listOfItems.getSelectedItems().get(0).getTemplate().getWidth()) > trailer.getWidth()) {
            numberOfRows++;
        }
        Assertions.assertEquals(listOfItems.getSelectedItems().get(0).getTemplate().getLength() * numberOfRows, loadTrailer.findSolutionHowToLoad(listOfItems, 0, 2, quantityOfItems, false));
    }

    @Test
    public void testFindSolutionHowToLoadL() {

        int quantityOfItems = trailer.getWidth() / itemTemplate.getLength();
        int numberOfRows = 1;
        if ((quantityOfItems * listOfItems.getSelectedItems().get(0).getTemplate().getLength(
        )) > trailer.getWidth()) {
            numberOfRows++;
        }
        Assertions.assertEquals(listOfItems.getSelectedItems().get(0).getTemplate().getWidth() * numberOfRows, loadTrailer.findSolutionHowToLoad(listOfItems, 0, 2, quantityOfItems, true));
    }
}
