package cz.comkop.shippingmanager;

import lombok.Getter;

@Getter
public class Item {
    private final ItemTemplate TEMPLATE;
    private final int ID;
    public static int id = 0;

  //  private int inPack;

    public Item(ItemTemplate template) {
        this.TEMPLATE = template;
        ID = id++;
    }
    public Item(Item item) {
        TEMPLATE = item.TEMPLATE;
        ID = item.ID;
    }

//   // public void setInPack(int inPack) {
//        this.inPack = inPack;
//    }
    public int getSortingArea(){
        return TEMPLATE.getLength() * TEMPLATE.getWidth();
    }


    public int getId() {
        return ID;
    }

    @Override
    public String toString() {
        return TEMPLATE.getName() + ", width: " + TEMPLATE.getWidth() + " cm, length: " + TEMPLATE.getLength() +
                " cm, height: " + TEMPLATE.getHeight() + " cm, weight: " + TEMPLATE.getWeight() + " kg";
    }
}