package cz.comkop.shippingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.comkop.shippingmanager.ItemTemplate.*;

public class ListOfItemsTest {

    ListOfItems listOfItems;


    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
    }

    @Test
    public void testGetItemsFromInput() {
        listOfItems.getItemsFromInput("33.1 30.2");
        Assertions.assertEquals(2, listOfItems.getRequiredItems().get(PALLET_120X80));
        Assertions.assertEquals(1, listOfItems.getRequiredItems().get(PALLET_80X60));
    }


    @Test
    public void testCreateSelectedItems() {
        listOfItems.getItemsFromInput("33.1 30.2");
        listOfItems.createSelectedItems();
        Assertions.assertEquals(3, listOfItems.getSelectedItems().size());
        Assertions.assertEquals(PALLET_120X80, listOfItems.getSelectedItems().get(0).getTemplate());
    }

    @Test
    public void testSortSelectedItemsByArea(){
        listOfItems.getItemsFromInput("1.1 33.1 30.2");
        listOfItems.createSelectedItems();
        Assertions.assertEquals(TEST_QUARTER_MACHINE, listOfItems.getSelectedItems().get(3).getTemplate());
    }

    @Test
    public void testMoveItemFromSelectedToLoaded() {
        listOfItems.getItemsFromInput("33.1 30.2");
        listOfItems.createSelectedItems();
        Item item = listOfItems.getSelectedItems().get(0);
        item.setTurnItem90Degrees(true);
        listOfItems.moveItemToAnotherList(listOfItems.getSelectedItems(),listOfItems.getLoadedItems(),0, new Coordinates(25,123), 'A');
        Assertions.assertEquals(25, listOfItems.getLoadedItems().get(0).getCoordinates(Coordinates.Type.X));
        Assertions.assertEquals(123, listOfItems.getLoadedItems().get(0).getCoordinates(Coordinates.Type.Y));
        Assertions.assertEquals(1, listOfItems.getLoadedItems().size());
        Assertions.assertTrue(listOfItems.getLoadedItems().get(0).isTurnItem90Degrees());
    }

    @Test
    public void testMoveItemFromSelectedToRemoved() {
        listOfItems.getItemsFromInput("30.1 33.1");
        listOfItems.createSelectedItems();
        listOfItems.moveItemToAnotherList(listOfItems.getSelectedItems(),listOfItems.getRemovedItems(),0, new Coordinates(), ' ');
        Assertions.assertEquals(PALLET_80X60,listOfItems.getSelectedItems().get(0).getTemplate());
        Assertions.assertEquals(PALLET_120X80,listOfItems.getRemovedItems().get(0).getTemplate());
    }

}
