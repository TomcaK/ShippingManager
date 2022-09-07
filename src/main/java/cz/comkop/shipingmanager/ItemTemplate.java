package cz.comkop.shipingmanager;

public enum ItemTemplate {//TODO check all dimensions, add height, loading by crane/forklift
    TEST_QUARTER_MACHINE("Test-Quarter machine", 1, 2, 1, 235, true),
    TEST_HALF_MACHINE("Test-Half machine", 2, 3, 1, 320, true),
    TEST_BIG_MACHINE("Test-Big machine", 3, 5, 1, 420),
    TEST_OZ("Test-OZ", 1, 1, 1, 20, true),
    TEST_NEW("Test-OZ", 2, 4, 1, 20, true),
    TEST_MUST_TURN_MACHINE("Test-MustturnMachine", 3, 2, 1, 320, true),
    BS_220X250_GH_LR("220x250 GH-LR", 60, 141, 1, 235, true,true),
    BS_230X280_GH_LR("230x280 GH-LR", 77, 144, 1, 320, true,true),
    BS_230X280_SHI_LR("230x280 SHI-LR", 105, 234, 1, 420, true,true),
    BS_230X280_A_CNC_R("230x280 A-CNC-R", 231, 226, 1, 780, true,true),
    BS_3000X320_GH_LR("300x320 GH-LR", 205, 117, 1, 605, true,true),
    BS_300X320_SHI_LR("300x320 SHI-LR", 117, 205, 1, 650, true,true),
    BS_BOX_220X250_GH_LR_230X280_GH_LR("Box 220x250 GH-LR/230x280 GH-LR", 80, 165, 1, 320, true,true),
    BS_CAGE_300X320_GH_LR_230X280_SHI_LR("Cage 300x320/230x280 SHI-LR", 117, 231, 1, 650, true,true),
    BS_300X320_A_CNC_R("300x320 A-CNC-R", 231, 190, 1, 904, true,true),
    BS_360X500_GH_LR("360x500 GH-LR", 108, 280, 1, 682, true,true),
    BS_360X500_SHI_LR("360x500 SHI-LR", 108, 280, 1, 740, true,true),
    BS_360X500_A_CNC_R("360x500 A-CNC-R", 245, 281, 1, 1500),
    BS_460X600_SHI_LR("460x600 SHI-LR", 114, 320, 1, 1195),
    BS_340_KATANA_X_CNC_LR("340 Katana X-CNC-LR", 245, 315, 1, 2150),
    BS_400_PROFI_A_CNC("400 Profi A-CNC", 215, 260, 1, 1300),
    BS_300X300_HERKULES_x_CNC("300x300 Herkules X-CNC", 228, 251, 1, 1420),
    BS_500X750_HORIZONTAL_SHI_LR("500x750 Horizontal SHI", 176, 310, 1, 2035),
    BS_600X1000_HORIZONTAL_X("600x1000 Horizontal X", 198, 434, 1, 5678),
    BS_440_CALIBER_X_CNC_SHI("440 Caliber X-CNC/SHI", 203, 256, 1, 3115),
    BS_440_HORIZONT_SHI_X("440 Horizont SHI/X", 176, 350, 1, 2080),
    THOR_5("THOR 5", 220, 374,258, 7735),
    RT_RDL_RDR("RDL/RDR", 100, 110,80, 200),
    M_OZ_3000("OZ 3000", 10, 300,10, 30,true),
    PALLET_120X80("Pallet 120 cm x 80 cm", 80, 120,50,100,true),
    PALLET_110X80("Pallet 110 cm x 80 cm", 80, 110,50,100,true),
    PALLET_80X60("Half pallet 80 cm x 60 cm", 80, 60, 50,100,true),
    RT_R290("R290", 60, 60, 80,12),
    RT_RDT_1000_350("RDT 2000/350", 55, 205, 80,151, true),
    RT_RDZ_1000_350("RDZ 1000/350", 95, 110, 80,100, true),
    RT_RDT_2000_450("RDT 2000/450", 65, 205, 80,158,true),
    RT_RDT_2000_520("RDT 2000/520", 70, 205, 80,164,true),
    RT_RDT_2000_620("RDT 2000/620", 90, 205, 80,221,true),
    RT_RDT_2000_800("RDT 2000/800", 105, 205, 80,270,true),
    RT_RDP_800("RDP 800", 90, 90, 80,230);


    private final String name;
    private boolean canBeRotated90Degrees;
    private boolean preferedNotToBeRotated;
    private final int width;
    private final int length;
    private int weight;
    private final int height;


    ItemTemplate(String name, int width, int length, int height, int weight, boolean canBeRotated90Degrees, boolean preferedNotToBeRotated) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.canBeRotated90Degrees = canBeRotated90Degrees;
        this.preferedNotToBeRotated = preferedNotToBeRotated;


    }

    ItemTemplate(String name, int width, int length, int height, int weight, boolean canBeRotated90Degrees) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
        this.weight = weight;
        this.canBeRotated90Degrees = canBeRotated90Degrees;



    }


    ItemTemplate(String name, int width, int length, int height) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;

    }

    ItemTemplate(String name, int width, int length, int height, int weight) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.height = height;
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


    public boolean isCanBeRotated90Degrees() {
        return canBeRotated90Degrees;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPreferedNotToBeRotated() {
        return preferedNotToBeRotated;
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