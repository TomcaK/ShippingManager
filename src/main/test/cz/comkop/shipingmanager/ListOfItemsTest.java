package cz.comkop.shipingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shipingmanager.ItemTemplate.PALLET_80X60;

public class ListOfItemsTest {

    ListOfItems listOfItems;


    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
    }

    @Test
    public void testGetItemsFromInput() {
        listOfItems = new ListOfItems();
        listOfItems.getItemsFromInput("33.1 30.2");
        Assertions.assertEquals(2, listOfItems.getRequiredItems().get(PALLET_120X80));
        Assertions.assertEquals(1, listOfItems.getRequiredItems().get(PALLET_80X60));
    }


    @Test
    public void testCreateSelectedItems() {
        listOfItems.getItemsFromInput("33.1 30.2");
        listOfItems.createSelectedItems();
        Assertions.assertEquals(3, listOfItems.getSelectedItems().size());
    }

    @Test
    public void testMoveItemFromSelectedToLoaded() {
        listOfItems.getItemsFromInput("33.1 30.2");
        listOfItems.createSelectedItems();
        Item item = listOfItems.getSelectedItems().get(0);
        item.setTurnItem90Degrees(true);
        listOfItems.moveItemFromSelectedToLoaded(0, 25, 123, 'A');
        Assertions.assertEquals(25, listOfItems.getLoadedItems().get(0).getX());
        Assertions.assertEquals(123, listOfItems.getLoadedItems().get(0).getY());
        Assertions.assertEquals(1, listOfItems.getLoadedItems().size());
        Assertions.assertTrue(listOfItems.getLoadedItems().get(0).isTurnItem90Degrees());
    }

    @Test
    public void testRemoveItem() {
        listOfItems.getItemsFromInput("30.1");
        listOfItems.createSelectedItems();
        listOfItems.removeItem(0);
        Assertions.assertTrue(listOfItems.getSelectedItems().isEmpty());
        Assertions.assertFalse(listOfItems.getRemovedItems().isEmpty());
    }

}
