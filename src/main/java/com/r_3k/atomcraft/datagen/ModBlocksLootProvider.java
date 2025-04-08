package com.r_3k.atomcraft.datagen;

import com.r_3k.atomcraft.block.ModBlocks;
import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlocksLootProvider extends BlockLootSubProvider {
    protected ModBlocksLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.URANIUM_235_BLOCK.get());
        dropSelf(ModBlocks.URANIUM_238_BLOCK.get());

        dropSelf(ModBlocks.ORE_WASHER.get());

        add(ModBlocks.URANIUM_235_ORE.get(),
                block -> createOreDrop(ModBlocks.URANIUM_235_ORE.get(), ModItems.RAW_URANIUM_235.get()));

        add(ModBlocks.DEEPSLATE_URANIUM_235_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_URANIUM_235_ORE.get(), ModItems.RAW_URANIUM_235.get()));

        add(ModBlocks.URANIUM_238_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.URANIUM_238_ORE.get(), ModItems.DIRTY_RAW_URANIUM_238.get(), 2, 4));

        add(ModBlocks.DEEPSLATE_URANIUM_238_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.DEEPSLATE_URANIUM_238_ORE.get(), ModItems.DIRTY_RAW_URANIUM_238.get(), 2, 4));

        add(ModBlocks.LEAD_ORE.get(),
                block -> createOreDrop(ModBlocks.LEAD_ORE.get(), ModItems.RAW_LEAD.get()));

        add(ModBlocks.DEEPSLATE_LEAD_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_LEAD_ORE.get(), ModItems.RAW_LEAD.get()));
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
