package cz.comkop.shippingmanager;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class Area {
    private List<Coordinates> coordinates = new ArrayList<>();

    public Area(List<LoadedItem> loadedItems) {
        for (LoadedItem loadedItem : loadedItems) {
            coordinates.addAll(loadedItem.getArea().getCoordinates());
        }
    }

    public Area(ItemTemplate template) {
        for (int i = 0; i < 2; i++) {
            coordinates.add(new Coordinates(i > 0 ? template.getWidth() - 1 : 0, i > 0 ? template.getLength() - 1 : 0));
        }
    }

    public static boolean overlap(Area area1, Area area2) {
        if (area1.coordinates.get(0).x >= area2.coordinates.get(0).x) {
            System.out.println();
        }

        if (area1.coordinates.get(1).x <= area2.coordinates.get(0).x) {
            System.out.println();
        }
        if (area1.coordinates.get(0).y >= area2.coordinates.get(0).y) {
            System.out.println();
        }
        if (area1.coordinates.get(1).y <= area2.coordinates.get(0).y) {
            System.out.println();
        }
//        if (area1.coordinates.get(0).x >= area2.coordinates.get(0).x && area1.coordinates.get(1).x <= area2.coordinates.get(0).x
//                && area1.coordinates.get(0).y >= area2.coordinates.get(0).y && area1.coordinates.get(1).y <= area2.coordinates.get(0).y) {
//            return false;
//        }
        return true;
    }

    public void setCoordinates(Item itemToCheck, int x, int y) {
        for (int i = 0; i < 2; i++) {
            coordinates.get(i).x = i > 0 ? x + itemToCheck.getTemplate().getWidth() - 1 : x;
            coordinates.get(i).y = i > 0 ? y + itemToCheck.getTemplate().getLength() - 1 : y;
        }
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    class Coordinates {
        protected int x;
        protected int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinates() {
        }

    }
}
