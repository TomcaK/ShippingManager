package cz.comkop.shipingmanager;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TrailerTemplate {
    TEST_4_CM_X_10_CM("TEST 4 cm x 10 cm", 4, 10),
    SEMITRAILER_2_48_M_X_13_6_M("Semitrailer 2.48 m x 13.6 m", 248, 1360),
    TRUCK_2_48_M_X_7_2_M("Truck 2.48 m x 7.2 m", 248, 720),
    VAN_2_2_M_X_4_2_M("Van 2.2 m x 4.2 m", 220, 420),
    CONTAINER_2_35_M_X_12_M("Container 40 HC 2.35 m x 12 m", 235, 1200);
    private final String name;
    private final int totalWidth;
    private final int totalLength;



    TrailerTemplate(String name, int totalWidth, int totalLength/*, int totalHeight*/) {
        this.name = name;
        this.totalWidth = totalWidth;
        this.totalLength = totalLength;
        // this.totalHeight = totalHeight;
    }

    public String getName() {
        return name;
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public int getTotalLength() {
        return totalLength;
    }



}