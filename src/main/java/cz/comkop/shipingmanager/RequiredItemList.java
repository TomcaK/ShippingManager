package cz.comkop.shipingmanager;

import java.util.*;

public class RequiredItemList {
 List<RequiredItem> requiredItems = new ArrayList<>();




    public List<RequiredItem> getRequiredItems(){
        return Collections.unmodifiableList(requiredItems);
    }


    }






}
