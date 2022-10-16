package cz.comkop.shippingmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Scanner;

import static cz.comkop.shippingmanager.TrailerTemplate.SEMITRAILER_2_48_M_X_13_6_M;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UIConsoleTest {
    ConsoleUI consoleUI;
    @Mock
    Scanner scanner;
    @BeforeEach
    public void setUp() {
        consoleUI = new ConsoleUI();
        consoleUI.setScanner(scanner);
    }

    @Test
    public void testSelectionOfDate() {
        when(scanner.nextLine()).thenReturn("20 3 2022", "20.3.2022");
        consoleUI.selectionOfDate();
        Assertions.assertEquals("2022-03-20", consoleUI.getShipingDate().toString());
    }

    @Test
    public void testSelectionOfItem() {
        when(scanner.nextLine()).thenReturn("2", "2.2");
        consoleUI.selectionOfItems();
        Assertions.assertEquals("2.2", consoleUI.getUserChoice());
        when(scanner.nextLine()).thenReturn("2.2 3.5");
        consoleUI.selectionOfItems();
        Assertions.assertEquals("2.2 3.5", consoleUI.getUserChoice());
    }

    @Test
    public void testUserSelection() {
        when(scanner.nextLine()).thenReturn("b", "a");
        Assertions.assertFalse(consoleUI.userSelection());
        Assertions.assertTrue(consoleUI.userSelection());
    }


    @Test
    public void testSelectionOfTrailer() {
        when(scanner.nextLine()).thenReturn("9", "2");
        consoleUI.selectionOfTrailer();
        Assertions.assertEquals(SEMITRAILER_2_48_M_X_13_6_M, consoleUI.getTrailerChoice());
    }
//TODO TDD test
//    @Test
//    public void testStringConverter(){
//        when(scanner.nextLine()).thenReturn("300x320 SHI.2");
//        consoleUI.selectionOfItems();
//        Assertions.assertEquals("12.2",consoleUI.getUserChoice());
//    }



}
