package cz.comkop.shippingmanager;

class Area {
    Coordinates[] coordinates;

    public Area(ItemToCheck itemsToCheck, int x, int y) {
        coordinates = generateArea(itemsToCheck,x,y);
    }
    public Area(ItemToCheck itemsToCheck) {
        coordinates = generateArea(itemsToCheck,x,y);
    }

    public Coordinates[] getCoordinates() {
        return coordinates;
    }

    private Coordinates[] generateArea(ItemToCheck itemsToCheck, int x, int y) {
        return new Coordinates[]{new Coordinates(x,y),new Coordinates(x+itemsToCheck.getTemplate().getWidth()-1,y),new Coordinates(x,y+itemsToCheck.getTemplate().getLength()-1),new Coordinates(x+itemsToCheck.getTemplate().getWidth() - 1, y+itemsToCheck.getTemplate().getLength()-1)};
    }

    public boolean compare(Area area){
        return false;
    }

    protected class Coordinates {
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
