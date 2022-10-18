package cz.comkop.shippingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.comkop.shippingmanager.ItemTemplate.PALLET_120X70;
import static cz.comkop.shippingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shippingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;

public class LoadingTest {
    ListOfItems listOfItems;
    Loading loading;
    TrailerTemplate trailer = SEMITRAILER_2_48_M_X_13_6_M;

    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
        loading = new Loading();
    }

    @Test
    public void testFindSolutionHowToLoad() {
        Assertions.assertEquals(120, loading.findSolutionHowToLoad(PALLET_120X80, 2, 3, false));
        Assertions.assertEquals(80, loading.findSolutionHowToLoad(PALLET_120X80, 2, 2, true));
    }

    @Test
    public void testFindSolutionHowToLoadWithAdditionalItem() {
        Assertions.assertEquals(120, loading.findSolutionHowToLoad(PALLET_120X80, 2, PALLET_120X70, trailer, false));
        Assertions.assertEquals(160, loading.findSolutionHowToLoad(PALLET_120X80, 2, PALLET_120X70, trailer, true));
    }

    @Test
    public void testGetSimilarItem(){
        int difference = 15;
        listOfItems.getItemsFromInput("27.1 20.1 12.1 28.1 35.1 37.1 39.2 40.1 41.1 42.1");
        listOfItems.createSelectedItems();
        Trailer trailer = new Trailer(SEMITRAILER_2_48_M_X_13_6_M);
        List<Item> similarItems = loading.getSimilarItems(listOfItems.getSelectedItems(),listOfItems.getSelectedItems().get(2).getTemplate(),difference);
        System.out.println(listOfItems.getSelectedItems().get(2));
        System.out.println(similarItems.size());
        System.out.println(similarItems.get(0));
    }

    @Test
    public void testGetItemWithOneSameDimension(){
        int difference = 15;
        listOfItems.getItemsFromInput("27.1 20.1 12.1 28.1 35.1 37.1 39.2 40.1 41.1 42.1");
        listOfItems.createSelectedItems();
        Trailer trailer = new Trailer(SEMITRAILER_2_48_M_X_13_6_M);
        List<Item> similarItems = loading.getItemWithOneSameDimension(listOfItems.getSelectedItems(),listOfItems.getSelectedItems().get(2).getTemplate());
        System.out.println(listOfItems.getSelectedItems().get(2));
        System.out.println(similarItems.size());
        for (Item it: similarItems
             ) {
            System.out.println(it);
        }
    }

    @Test
    public void testGetBestItem(){
        listOfItems.getItemsFromInput("27.1 20.1 12.1 28.1 35.1 37.1 39.2 40.1 41.1 42.1");
        listOfItems.createSelectedItems();
        Trailer trailer = new Trailer(SEMITRAILER_2_48_M_X_13_6_M);
        List<Item> similarItems = loading.getItemWithOneSameDimension(listOfItems.getSelectedItems(),listOfItems.getSelectedItems().get(2).getTemplate());
        System.out.println(listOfItems.getSelectedItems().get(2));
        System.out.println(similarItems.size());
        for (Item it: similarItems
        ) {
            System.out.println(it);
        }
        int freeSpaceW = trailer.getTemplate().getWidth() - listOfItems.getSelectedItems().get(2).getTemplate().getWidth() * 2;
        Item bestItem = loading.getBestItem(freeSpaceW,similarItems);
        System.out.println("\n W " + bestItem);
        int freeSpaceL = trailer.getTemplate().getWidth() - listOfItems.getSelectedItems().get(2).getTemplate().getLength();
         bestItem = loading.getBestItem(freeSpaceL,similarItems);
        System.out.println("\n L " + bestItem);
    }

    @Test
    public void testCreatePack(){
        listOfItems.getItemsFromInput("27.1 20.1 12.1 28.1 35.1 37.1 39.2 40.1 41.1 42.1");
        listOfItems.createSelectedItems();
        Trailer trailer = new Trailer(SEMITRAILER_2_48_M_X_13_6_M);
        loading.createPacks(trailer,listOfItems);
//        Assertions.assertEquals(0,listOfItems.getSelectedItems().get(0).getInPack());
//        Assertions.assertEquals(0,listOfItems.getSelectedItems().get(1).getInPack());
//        Assertions.assertEquals(0,listOfItems.getSelectedItems().get(2).getInPack());
//        Assertions.assertEquals(1,listOfItems.getSelectedItems().get(3).getInPack());

    }
    //TODO TDD
    @Test
    public void testPackCreator(){

    }

}
