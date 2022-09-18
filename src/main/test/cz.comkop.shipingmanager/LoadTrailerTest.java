import cz.comkop.shipingmanager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shipingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;

public class LoadTrailerTest {
    ConsoleUI consoleUI;

    ListOfItems listOfItems;

    LoadTrailer loadTrailer;
    TrailerTemplate trailer;


    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
        loadTrailer = new LoadTrailer();

    }


    @Test
    public void TestFindSolutionHowToLoadW() {
        int a = PALLET_120X80.ordinal() + 1;
        String input = a + ".2";
        trailer = SEMITRAILER_2_48_M_X_13_6_M;
        int quantityOfItems = trailer.getWidth() / PALLET_120X80.getWidth();
        listOfItems.getItemsFromInput(input);
        listOfItems.createSelectedItems();
        listOfItems.sortSelectedItemsByArea();
        int numberOfRows = 1;
        if ((quantityOfItems * listOfItems.getSelectedItems().get(0).getTemplate().getWidth()) > trailer.getWidth()) {
            numberOfRows++;
        }
        Assertions.assertEquals(listOfItems.getSelectedItems().get(0).getTemplate().getLength() * numberOfRows, loadTrailer.findSolutionHowToLoad(listOfItems, 0, 2, quantityOfItems, false));
    }

    @Test
    public void TestFindSolutionHowToLoadL() {
        int a = PALLET_120X80.ordinal() + 1;
        String input = a + ".2";
        trailer = SEMITRAILER_2_48_M_X_13_6_M;
        int quantityOfItems = trailer.getWidth() / PALLET_120X80.getLength();
        listOfItems.getItemsFromInput(input);
        listOfItems.createSelectedItems();
        listOfItems.sortSelectedItemsByArea();
        int numberOfRows = 1;
        if ((quantityOfItems * listOfItems.getSelectedItems().get(0).getTemplate().getLength(
        )) > trailer.getWidth()) {
            numberOfRows++;
        }
        Assertions.assertEquals(listOfItems.getSelectedItems().get(0).getTemplate().getWidth() * numberOfRows, loadTrailer.findSolutionHowToLoad(listOfItems, 0, 2, quantityOfItems, true));
    }
}
