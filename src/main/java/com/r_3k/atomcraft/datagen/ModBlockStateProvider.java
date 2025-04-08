package com.r_3k.atomcraft.datagen;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AtomCraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.URANIUM_235_BLOCK);
        blockWithItem(ModBlocks.URANIUM_238_BLOCK);

        blockWithItem(ModBlocks.URANIUM_235_ORE);
        blockWithItem(ModBlocks.URANIUM_238_ORE);

        blockWithItem(ModBlocks.DEEPSLATE_URANIUM_235_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_URANIUM_238_ORE);

        blockWithItem(ModBlocks.LEAD_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_LEAD_ORE);

        blockWithItem(ModBlocks.ORE_WASHER);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
