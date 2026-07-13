package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;

public class CommonClass {
    public static void init() {
        //these classes must be referenced to load, for some reason...
        Object[] classes = {
                CommonGameRules.SNAP_CUSHION_ELEVATION_TO_PIXEL_GRID
        };
    }
}