package cz.comkop.shipingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Scanner;

import static cz.comkop.shipingmanager.ConsoleUI.*;
import static cz.comkop.shipingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class UIConsoleTest {
    ConsoleUI consoleUI;


    @BeforeEach
    public void setUp() {
        consoleUI = new ConsoleUI();
    }

    @Test
    public void testTrailerRightValue() {
        Assertions.assertTrue(consoleUI.insertRightValue("2", TRAILER_REGEX));
        Assertions.assertFalse(consoleUI.insertRightValue("2.3", TRAILER_REGEX));
        Assertions.assertFalse(consoleUI.insertRightValue("8", TRAILER_REGEX));
    }

    @Test
    public void testItemRightValue() {
        Assertions.assertTrue(consoleUI.insertRightValue("1.5 2.3", ITEM_REGEX));
        Assertions.assertTrue(consoleUI.insertRightValue("2.3", ITEM_REGEX));
        Assertions.assertFalse(consoleUI.insertRightValue("2", ITEM_REGEX));
    }

    @Test
    public void testDateRightValue() {
        Assertions.assertTrue(consoleUI.insertRightValue("1.05.2022", DATE_REGEX));
        Assertions.assertTrue(consoleUI.insertRightValue("20.3.2022", DATE_REGEX));
        Assertions.assertTrue(consoleUI.insertRightValue("20.03.2022", DATE_REGEX));
        Assertions.assertFalse(consoleUI.insertRightValue("20-03-2022", DATE_REGEX));
        Assertions.assertFalse(consoleUI.insertRightValue("20 3 2022", DATE_REGEX));
    }

    @Test
    public void testInputControl(){

    }


    @Test
    public void testSelectionOfTrailer(){
        Scanner mockS = mock(Scanner.class);
        when(mockS.nextLine()).thenReturn("2");
        consoleUI.selectionOfTrailer();
        Assertions.assertEquals(SEMITRAILER_2_48_M_X_13_6_M,consoleUI.getTrailerChoice());
    }


}
