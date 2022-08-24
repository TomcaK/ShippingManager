package cz.comkop.shipingmanager;

import java.util.ArrayList;

public class Trailers {
    private ArrayList<Trailer> trailers;
    public static Trailers TEMPLATE = new Trailers();


    public Trailers() {
        trailers = new ArrayList<>();
        trailers.add(new Trailer("TEST 4 cm x 10 cm", 4, 10));
        trailers.add(new Trailer("Semitrailer 2.48 m x 13.6 m", 248, 1360));
        trailers.add(new Trailer("Truck 2.48 m x 7.2 m", 248, 720));
        trailers.add(new Trailer("Van 2.2 m x 4.2 m", 220, 420));
        trailers.add(new Trailer("Container 40 HC 2.35 m x 12 m", 235, 1200));
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public static void reset(){
        TEMPLATE = new Trailers();
    }
}