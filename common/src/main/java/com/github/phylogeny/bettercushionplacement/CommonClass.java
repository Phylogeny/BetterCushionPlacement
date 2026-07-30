package com.github.phylogeny.bettercushionplacement;

import com.github.phylogeny.bettercushionplacement.registry.CommonGameRules;

public class CommonClass {
    public static void init() {
        //these classes must be referenced to load, for some reason...
        Object[] classes = {
                CommonGameRules.ALLOW_INNER_WALL_CUSHION_PLACEMENT
        };
    }
}
