import cz.comkop.shipingmanager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static cz.comkop.shipingmanager.ItemTemplate.PALLET_120X80;
import static cz.comkop.shipingmanager.ItemTemplate.PALLET_80X60;
import static org.mockito.Mockito.*;

public class ListOfItemsTest {

    ListOfItems listOfItems;


    @BeforeEach
    public void setUp() {
        listOfItems = new ListOfItems();
    }

    @Test
    public void TestGetItemsFromInput() {
        int a = PALLET_120X80.ordinal() + 1;
        int b = PALLET_80X60.ordinal() + 1;
        String input = b + ".5 " + a + ".1";
        listOfItems.getItemsFromInput(input);
        Assertions.assertEquals(1, listOfItems.getRequiredItems().get(PALLET_120X80));
        Assertions.assertEquals(5, listOfItems.getRequiredItems().get(PALLET_80X60));
    }


    @Test
    public void TestCreateSelectedItems() {
        int a = PALLET_120X80.ordinal() + 1;
        int b = PALLET_80X60.ordinal() + 1;
        String input = b + ".5 " + a + ".1";
        listOfItems.getItemsFromInput(input);
        listOfItems.createSelectedItems();
        Assertions.assertFalse(listOfItems.getSelectedItems().isEmpty());
    }

    @Test
    public void TestSortSelectedItems() {
        int a = PALLET_120X80.ordinal() + 1;
        int b = PALLET_80X60.ordinal() + 1;
        String input = b + ".5 " + a + ".1";
        listOfItems.getItemsFromInput(input);
        listOfItems.createSelectedItems();
        listOfItems.sortSelectedItemsByArea();
        Assertions.assertEquals(PALLET_120X80, listOfItems.getSelectedItems().get(0).getTemplate());
    }

}
