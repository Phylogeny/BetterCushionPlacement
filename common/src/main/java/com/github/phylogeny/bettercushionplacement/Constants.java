package com.github.phylogeny.bettercushionplacement;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String MOD_ID = "bettercushionplacement";
    public static final String MOD_NAME = "BetterCushionPlacement";
    public static final String MOD_DISPLAY_NAME = "Better Cushion Placement";
    public static final String MOD_INITIALS = "bcp";
    public static final Identifier MOD_IDENTIFIER = Identifier.fromNamespaceAndPath(MOD_ID, "");
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String YACL_MOD_ID = "yet_another_config_lib_v3";
    public static final String MOD_DESCRIPTION = """
            Cushions can be placed as follows:
            
            1. Holding Shift:
             - Snap to vertices of the block grid.
             - Stack a cushion on another one.
            2. Holding Control:
             - Snap to vertices of the pixel grid.
            3. Holding Shift + Control:
             - No snapping, completely off-grid.
            4. Holding neither:
             - Place normally.
            
            
            Game rules:
            
            Instead of only being supported by blocks, cushions can also be supported by other cushions, allowing direct stacking.
             - ID: cushions_support_each_other
             - Default: Disabled
            
            Ignore #cushion_uses_collision_shape block tags, thus allowing sub-pixel cushion placement on the inner walls of cauldrons, composters, and hoppers without a data pack.
             - ID: allow_inner_wall_cushion_placement
             - Default: Disabled
            
            
            Block Tags:
            
            Force the normal center-of-block placement of cushions when placing them against blocks with this tag.
             - ID: normal_cushion_placement
             - Default: Sign blocks
            
            
            Configs:
            
            Game rule features can be set to always enabled or always disabled in the bettercushionplacement-gamerules.properties file in the config folder.
             - Always enabled/disabled:
               - The rule will not exist.
             - Always disabled:
               - The mixin will not be applied.
               - All performance cost prevented.
            
            
            Placement Preview:
            
            A box renders where a cushion will place with the color it will have when placed.
            
            
            Server-only Installation:
            
            If this mod is installed on a server, but not a client, everything will work as intended, with the following purely visual exceptions:
             - The player's hand will swing when:
               - Placement fails.
             - The player's hand will not swing when:
               - Placing on inner walls of cauldrons, etc.
               - Stacking cushions on one another.

            """;
}
