package cz.comkop.shipingmanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemTemplateList {
    List<ItemTemplate> itemTemplates = Arrays.stream(ItemTemplate.values()).toList();



    public List<ItemTemplate> getItemTemplates(){
        return Collections.unmodifiableList(itemTemplates);
    }
}
