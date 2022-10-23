package cz.comkop.shippingmanager;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
class Area {
    private List<Coordinates> coordinates = new ArrayList<>();

    public Area(List<LoadedItem> items) {
        for (LoadedItem loadedItem : items) {
            coordinates.addAll(loadedItem.getArea().getCoordinates());
        }
    }

    public Area(ItemTemplate template) {
        for (int i = 0; i < 2; i++) {
            coordinates.add(new Coordinates(i > 0 ? template.getWidth() - 1 : 0, i > 0 ? template.getLength() - 1 : 0));
        }
    }

    public boolean collision(Area area, Area... areas) {
        return Arrays.stream(areas).map(c->c.coordinates)
                .anyMatch(c -> area.coordinates.get(0).x >= c.get(0).x
                        && area.coordinates.get(0).x <= c.get(1).x
                        && area.coordinates.get(0).y >= c.get(0).y
                        && area.coordinates.get(0).y <= c.get(1).y
                );
    }

    public void setCoordinates(Item itemToCheck, int x, int y) {
        for (int i = 0; i < 2; i++) {
            coordinates.get(i).x = i > 0 ? x + itemToCheck.getTEMPLATE().getWidth() - 1 : x;
            coordinates.get(i).y = i > 0 ? y + itemToCheck.getTEMPLATE().getLength() - 1 : y;
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
