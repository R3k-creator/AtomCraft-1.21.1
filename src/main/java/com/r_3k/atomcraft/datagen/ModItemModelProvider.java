package com.r_3k.atomcraft.datagen;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AtomCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.URANIUM_235_INGOT.get());
        basicItem(ModItems.URANIUM_238_INGOT.get());
        basicItem(ModItems.RAW_URANIUM_235.get());
        basicItem(ModItems.DIRTY_RAW_URANIUM_238.get());
        basicItem(ModItems.PURIFIED_RAW_URANIUM_238.get());
        basicItem(ModItems.DIRTY_URANIUM_238_DUST.get());

        basicItem(ModItems.RAW_LEAD.get());
        basicItem(ModItems.LEAD_INGOT.get());
        basicItem(ModItems.LEAD_PLATE.get());

        basicItem(ModItems.ANTI_RADIATION_HELMET.get());
        basicItem(ModItems.ANTI_RADIATION_CHESTPLATE.get());
        basicItem(ModItems.ANTI_RADIATION_LEGGINGS.get());
        basicItem(ModItems.ANTI_RADIATION_BOOTS.get());

        basicItem(ModItems.URANIUM_238_HAMMER.get());
    }
}
