package cz.comkop.shippingmanager;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Getter
class Area {
    private List<Coordinates> coordinates = new ArrayList<>();
    ;

    public Area(List<LoadedItem> loadedItems) {
        for (LoadedItem loadedItem : loadedItems) {
            coordinates.addAll(loadedItem.getArea().getCoordinates());
        }
    }

    public Area(ItemTemplate template) {
        for (int i = 0; i < 4; i++) {
            coordinates.add(new Coordinates(i % 2 == 1 ? template.getWidth() - 1 : 0, i > 1 ? template.getLength() - 1 : 0));
        }
    }


    public void setCoordinates(Item itemToCheck, int x, int y) {
        for (int i = 0; i < 4; i++) {
            coordinates.get(i).x = i % 2 == 1 ? x + itemToCheck.getTemplate().getWidth() - 1 : x;
            coordinates.get(i).y = i > 2 ? y + itemToCheck.getTemplate().getLength() - 1 : y;
        }
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public boolean compare(Area area) {
        return false;
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
