package cz.comkop.shipingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X70;
import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shipingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;

public class LoadTrailerTest {
    ListOfItems listOfItems;
    LoadTrailer loadTrailer;
    TrailerTemplate trailer = SEMITRAILER_2_48_M_X_13_6_M;

    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
        loadTrailer = new LoadTrailer();
//        listOfItems.getItemsFromInput("30.2 31.1");
//        listOfItems.createSelectedItems();
//        listOfItems.sortSelectedItemsByArea();
    }

    @Test
    public void testFindSolutionHowToLoad() {
        Assertions.assertEquals(120, loadTrailer.findSolutionHowToLoad(PALLET_120X80, 2, 3, false));
        Assertions.assertEquals(80, loadTrailer.findSolutionHowToLoad(PALLET_120X80, 2, 2, true));
    }

    @Test
    public void testFindSolutionHowToLoadWithAdditionalItem() {
        Assertions.assertEquals(120, loadTrailer.findSolutionHowToLoad(PALLET_120X80, 2, PALLET_120X70, trailer, false));
        Assertions.assertEquals(160, loadTrailer.findSolutionHowToLoad(PALLET_120X80, 2, PALLET_120X70, trailer, true));
    }

}
