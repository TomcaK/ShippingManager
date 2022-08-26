package cz.comkop.shipingmanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ItemTemplate {
    TEST_QUARTER_MACHINE("Test-Quarter machine", 1, 2, 235, true),
    TEST_HALF_MACHINE("Test-Half machine", 2, 3, 320, true),
    TEST_BIG_MACHINE("Test-Big machine", 3, 5, 420),
    TEST_OZ("Test-OZ", 1, 1, 20, true),
    TEST_MUST_TURN_MACHINE("Test-MustturnMachine", 3, 2, 320, true),
    BS_220X250_GH_LR("220x250 GH-LR", 60, 141, 235, true),
    BS_230X280_GH_LR("230x280 GH-LR", 77, 144, 320, true),
    BS_230X280_SHI_LR("230x280 SHI-LR", 105, 234, 420, true),
    BS_230X280_A_CNC_R("230x280 A-CNC-R", 231, 226, 780, true),
    BS_3000X320_GH_LR("300x320 GH-LR", 205, 117, 605, true),
    BS_300X320_SHI_LR("300x320 SHI-LR", 117, 205, 650, true),
    BS_BOX_220X250_GH_LR_230X280_GH_LR("Box 220x250 GH-LR/230x280 GH-LR", 80, 165, 320, true),
    BS_CAGE_300X320_GH_LR_230X280_SHI_LR("Cage 300x320/230x280 SHI-LR", 117, 231, 650, true),
    BS_300X320_A_CNC_R("300x320 A-CNC-R", 231, 190, 904, true),
    BS_360X500_GH_LR("360x500 GH-LR", 108, 280, 682, true),
    BS_360X500_SHI_LR("360x500 SHI-LR", 108, 280, 740, true),
    BS_360X500_A_CNC_R("360x500 A-CNC-R", 245, 281, 1500),
    BS_460X600_SHI_LR("460x600 SHI-LR", 114, 320, 1195),
    BS_340_KATANA_X_CNC_LR("340 Katana X-CNC-LR", 245, 315, 2150),
    BS_400_PROFI_A_CNC("400 Profi A-CNC", 215, 260, 1300),
    BS_300X300_HERKULES_x_CNC("300x300 Herkules X-CNC", 228, 251, 1420),
    BS_500X750_HORIZONTAL_SHI_LR("500x750 Horizontal SHI", 176, 310, 2035),
    BS_600X1000_HORIZONTAL_X("600x1000 Horizontal X", 198, 434, 5678),
    BS_440_CALIBER_X_CNC_SHI("440 Caliber X-CNC/SHI", 203, 256, 3115),
    BS_440_HORIZONT_SHI_X("440 Horizont SHI/X", 176, 350, 2080),
    RT_RDL_RDR("RDL/RDR", 100, 110, 200),
    M_OZ_300("OZ", 15, 300, 30),
    PALLET_120X80("Pallet 120 cm x 80 cm", 80, 120),
    PALLET_80X60("Half pallet 80 cm x 60 cm", 80, 60),
    RT_R290("R290", 60, 60, 12);


    private final String name;
    private boolean turnAble;
    private int width;
    private int length;
    private int weight;



    ItemTemplate(String name, int width, int length, int weight, boolean turnAble) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.turnAble = turnAble;


    }


    ItemTemplate(String name, int width, int length) {
        this.name = name;
        this.width = width;
        this.length = length;
    }

    ItemTemplate(String name, int width, int length, int weight) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }




    public String getName() {
        return name;
    }


    public int getWidth() {
        return width;
    }


    public int getLength() {
        return length;
    }

    public int getWeight() {
        return weight;
    }


    public boolean isTurnAble() {
        return turnAble;
    }
//      440 x600 Horizontal X - NC - BS 290 160 214 2000
//            500 x750 Horizontal X 310 176 221 2120
//            600 Camel X 432 136 220
//            400 x400 Herkules X - CNC 326 233 221 3560
//            500 x750 Horizontal X - NC - BS 360 170 221 4150
//            440 x600 Horizontal SHI 295 176 215 1685
//            510 x 510 Herkules X -CNC 336 240 210 6380
//            540 Caliber X -CNC 370 212 239 3790
//            540 Horizont X -CNC 358 137 240 3900
//            600 x1100 Horizontal X 434 198 265 5678

}