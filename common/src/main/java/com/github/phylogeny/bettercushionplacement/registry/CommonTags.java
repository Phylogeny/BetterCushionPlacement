package com.github.phylogeny.bettercushionplacement.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CommonTags {
    private static final Identifier COMMON = Identifier.fromNamespaceAndPath("c", "");
    private static final Identifier NORMAL_CUSHION_PLACEMENT_NAME = COMMON.withPath("normal_cushion_placement");

    public static class Blocks {
        public static final List<TagKey<?>> TAGS = new ArrayList<>();
        public static final TagKey<Block> NORMAL_CUSHION_PLACEMENT = createTag(NORMAL_CUSHION_PLACEMENT_NAME);

        private static TagKey<Block> createTag(Identifier name) {
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, name);
            TAGS.add(tag);
            return tag;
        }
    }
}