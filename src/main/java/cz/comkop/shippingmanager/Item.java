package cz.comkop.shippingmanager;

import lombok.Getter;

@Getter
public class Item {
    private final ItemTemplate template;
    private int id;
    public static int ID = 0;

  //  private int inPack;

    public Item(ItemTemplate template) {
        this.template = template;
        id = ID++;
    }
    public Item(Item item) {
        template = item.template;
        id = item.id;
    }

//   // public void setInPack(int inPack) {
//        this.inPack = inPack;
//    }
    public int getSortingArea(){
        return template.getLength() * template.getWidth();
    }


    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return template.getName() + ", width: " + template.getWidth() + " cm, length: " + template.getLength() +
                " cm, height: " + template.getHeight() + " cm, weight: " + template.getWeight() + " kg";
    }
}