package cz.comkop.shippingmanager;

import lombok.Getter;

@Getter

public class Item {
    private final ItemTemplate template;
    private final int area;
  //  private int inPack;

    public Item(ItemTemplate template) {
        this.template = template;
        area = template.getLength() * template.getWidth();
    }

//   // public void setInPack(int inPack) {
//        this.inPack = inPack;
//    }

    @Override
    public String toString() {
        return template.getName() + ", width: " + template.getWidth() + " cm, length: " + template.getLength() +
                " cm, height: " + template.getHeight() + " cm, weight: " + template.getWeight() + " kg";
    }
}