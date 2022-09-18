import cz.comkop.shipingmanager.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static cz.comkop.shipingmanager.ConsoleUI.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UIConsoleTest {
    ConsoleUI consoleUI;



    @BeforeEach
    public void setUp() {
        consoleUI = new ConsoleUI();
    }

    public void TestTrailerSelection(){

    }

    @Test
    public void TestTrailerInputControlTrue() {
        Assertions.assertTrue(consoleUI.inputControl("3", TRAILER_REGEX));
    }

    @Test
    public void TestTrailerInputControlFalse() {
        Assertions.assertFalse(consoleUI.inputControl("25", TRAILER_REGEX));
    }

    @Test
    public void TestItemInputControlTrue() {
        Assertions.assertTrue(consoleUI.inputControl("1.5 2.3", ITEM_REGEX));
        Assertions.assertTrue(consoleUI.inputControl("2.3", ITEM_REGEX));
    }

    @Test
    public void TestItemInputControlFalse() {
        Assertions.assertFalse(consoleUI.inputControl("25", ITEM_REGEX));
    }

    @Test
    public void TestDateInputControlTrue() {
        Assertions.assertTrue(consoleUI.inputControl("1.5.2022", DATE_REGEX));
        Assertions.assertTrue(consoleUI.inputControl("1.05.2022", DATE_REGEX));
        Assertions.assertTrue(consoleUI.inputControl("20.3.2022", DATE_REGEX));
        Assertions.assertTrue(consoleUI.inputControl("20.03.2022", DATE_REGEX));
    }

    @Test
    public void TestDateInputControlFalse() {
        Assertions.assertFalse(consoleUI.inputControl("25", DATE_REGEX));
    }





}
