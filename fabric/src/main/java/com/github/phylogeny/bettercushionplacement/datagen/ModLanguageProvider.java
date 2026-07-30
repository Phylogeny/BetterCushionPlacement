package com.github.phylogeny.bettercushionplacement.datagen;

import com.github.phylogeny.bettercushionplacement.Constants;
import com.github.phylogeny.bettercushionplacement.mixin.MixinConfigPlugin;
import com.github.phylogeny.bettercushionplacement.registry.CommonTags;
import com.github.phylogeny.bettercushionplacement.registry.RegistryObj;
import com.github.phylogeny.bettercushionplacement.util.LangUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    protected ModLanguageProvider(
            FabricPackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> registryLookup
    ) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(
            HolderLookup.Provider registryLookup,
            TranslationBuilder builder
    ) {
        String modMenuKey = "modmenu.%s." + Constants.MOD_ID;
        builder.add(
                modMenuKey.formatted("nameTranslation"),
                Constants.MOD_DISPLAY_NAME
        );
        builder.add(
                modMenuKey.formatted("summaryTranslation"),
                "Block/pixel grid snapping, placement previews, cushion stacking, & more."
        );
        builder.add(
                modMenuKey.formatted("descriptionTranslation"),
                Constants.MOD_DESCRIPTION
        );
        builder.add(
                "fml.menu.mods.info.description." + Constants.MOD_ID,
                Constants.MOD_DESCRIPTION
        );
        for (MixinConfigPlugin.Mixin mixin : MixinConfigPlugin.Mixin.values()) {
            String key = "gamerule.%s.%s".formatted(Constants.MOD_ID, mixin.registryName);
            String name = LangUtil.snakeCaseToTitleCase(mixin.registryName);
            builder.add(key, name);
            key += ".description";
            builder.add(key, mixin.comment);
        }
        addTagEntries(builder, CommonTags.Blocks.TAGS);
        LangUtil.addConfigLangEntries(builder::add);
    }

    private void addTagEntries(
            TranslationBuilder builder,
            List<TagKey<?>> tags
    ) {
        tags.forEach(tag ->
                builder.add(
                    tag,
                    LangUtil.snakeCaseToTitleCase(tag.location().getPath())
                )
        );
    }

    private void add(
            TranslationBuilder builder,
            RegistryObj<?> holder
    ) {
        String name = LangUtil.snakeCaseToTitleCase(holder);
        if (holder.get() instanceof Item item)
            builder.add(item, name);
        else if (holder.get() instanceof Block block)
            builder.add(block, name);
        else
            throw new IllegalArgumentException("holder must be an instance of Item or Block");
    }
}
