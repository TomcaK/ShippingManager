package cz.comkop.shipingmanager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemTemplateList {
    List<ItemTemplate> itemTemplates = Arrays.stream(ItemTemplate.values()).toList();

    public List<ItemTemplate> getItemTemplates() {
        return itemTemplates;
    }

    public void sort(){
        itemTemplates = itemTemplates.stream().sorted(Comparator.comparing(itemTemplate -> itemTemplate.getWidth() * itemTemplate.getLength())).collect(Collectors.toList());
    }
}
