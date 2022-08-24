package cz.comkop.shipingmanager;

import java.util.ArrayList;

public class ListOfItems {
    private final ArrayList<Item> items;
    public static final ListOfItems TEMPLATE = new ListOfItems();
    public ListOfItems() {
        items = new ArrayList<>();
        items.add(new Item("Test-Quarter machine", 1, 2, 235, true));
        items.add(new Item("Test-Half machine", 2, 3, 320, true));
        items.add(new Item("Test-Big machine", 3, 5, 420));
        items.add(new Item("Test-OZ2", 1, 1, 20, true));
        items.add(new Item("Test-MustturnMachine", 3, 2, 320, true));
        items.add(new Item("220x250 GH-LR", 60, 141, 235, true));
        items.add(new Item("230x280 GH-LR", 77, 144, 320, true));
        items.add(new Item("230x280 SHI-LR", 105, 234, 420, true));
        items.add(new Item("230x280 A-CNC-R", 231, 226, 780, true));
        items.add(new Item("300x320 GH-LR", 205, 117, 605, true));
        items.add(new Item("300x320 SHI-LR", 117, 205, 650, true));
        items.add(new Item("Box 220x250 GH-LR/230x280 GH-LR", 80, 165, 320, true));
        items.add(new Item("Cage 300x320/230x280 SHI-LR", 117, 231, 650, true));
        items.add(new Item("300x320 A-CNC-R", 231, 190, 904, true));
        items.add(new Item("360x500 GH-LR", 108, 280, 682, true));
        items.add(new Item("360x500 SHI-LR", 108, 280, 740, true));
        items.add(new Item("360x500 A-CNC-R", 245, 281, 1500));
        items.add(new Item("460x600 SHI-LR", 114, 320, 1195));
        items.add(new Item("340 Katana X-CNC-LR", 245, 315, 2150));
        items.add(new Item("400 Profi A-CNC", 215, 260, 1300));
        items.add(new Item("300x300 Herkules X-CNC", 228, 251, 1420));
        items.add(new Item("500x750 Horizontal SHI", 176, 310, 2035));
        items.add(new Item("600x1000 Horizontal X", 198, 434, 5678));
        items.add(new Item("440 Caliber X-CNC/SHI", 203, 256, 3115));
        items.add(new Item("440 Horizont SHI/X", 176, 350, 2080));
        items.add(new Item("RDL/RDR", 100, 110, 200));
        items.add(new Item("OZ", 15, 300, 30));
        items.add(new Item("Pallet 120 cm x 80 cm", 80, 120));
        items.add(new Item("Half pallet 80 cm x 60 cm", 80, 120));
        items.add(new Item("R290", 60, 60, 12));

        /*Pila	Délka/Length	Hloubka/Width	Výška/Height	Váha/Weight





        440 x600 Horizontal X - NC - BS 290 160 214 2000
        500 x750 Horizontal X 310 176 221 2120
        600 Camel X 432 136 220
        400 x400 Herkules X - CNC 326 233 221 3560
        500 x750 Horizontal X - NC - BS 360 170 221 4150
        440 x600 Horizontal SHI 295 176 215 1685
        510 x 510 Herkules X -CNC 336 240 210 6380
        540 Caliber X -CNC 370 212 239 3790
        540 Horizont X -CNC 358 137 240 3900
        600 x1100 Horizontal X 434 198 265 5678
                */

    }

    public ArrayList<Item> getItems() {
        //TODO for TEMPLATE instance shoud be unmodifable list
        return items;
    }

}