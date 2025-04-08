package com.r_3k.atomcraft.datagen;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AtomCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.URANIUM_235_ORE.get())
                .add(ModBlocks.DEEPSLATE_URANIUM_235_ORE.get())

                .add(ModBlocks.URANIUM_238_ORE.get())
                .add(ModBlocks.DEEPSLATE_URANIUM_238_ORE.get())

                .add(ModBlocks.URANIUM_235_BLOCK.get())
                .add(ModBlocks.URANIUM_238_BLOCK.get())

                .add(ModBlocks.LEAD_ORE.get())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())

                .add(ModBlocks.ORE_WASHER.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.URANIUM_235_ORE.get())
                .add(ModBlocks.DEEPSLATE_URANIUM_235_ORE.get())

                .add(ModBlocks.URANIUM_238_ORE.get())
                .add(ModBlocks.DEEPSLATE_URANIUM_238_ORE.get())

                .add(ModBlocks.URANIUM_235_BLOCK.get())
                .add(ModBlocks.URANIUM_238_BLOCK.get())

                .add(ModBlocks.LEAD_ORE.get())
                .add(ModBlocks.DEEPSLATE_LEAD_ORE.get())

                .add(ModBlocks.ORE_WASHER.get());
    }
}
