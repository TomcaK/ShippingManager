package cz.comkop.shipingmanager;

import java.util.Arrays;
import java.util.List;

public class TrailerTemplateList {
    List<TrailerTemplate> trailerTemplates = Arrays.stream(TrailerTemplate.values()).toList();

    public List<TrailerTemplate> getTrailerTemplates() {
        return trailerTemplates;
    }
}