package cz.comkop.shipingmanager;


public enum TrailerTemplate {
    TEST_4_CM_X_10_CM("TEST 4 cm x 10 cm", 4, 10),
    SEMITRAILER_2_48_M_X_13_6_M("Semitrailer 2.48 m x 13.6 m", 248, 1360),
    TRUCK_2_48_M_X_7_2_M("Truck 2.48 m x 7.2 m", 248, 720),
    VAN_2_2_M_X_4_2_M("Van 2.2 m x 4.2 m", 220, 420),
    CONTAINER_2_35_M_X_12_M("Container 40 HC 2.35 m x 12 m", 235, 1200);
    private final String name;
    private final int width;
    private final int length;



    TrailerTemplate(String name, int width, int length/*, int totalHeight*/) {
        this.name = name;
        this.width = width;
        this.length = length;
        // this.totalHeight = totalHeight;
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

    @Override
    public String toString() {
        return "* Selected trailer: " + name + "," +
                " width: " + NumberUtil.metersFromCentimeters(width) + " m, length: " +
                NumberUtil.metersFromCentimeters(length) + " m *";
    }
}