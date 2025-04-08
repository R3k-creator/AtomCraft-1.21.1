package com.r_3k.atomcraft.datagen;

import com.r_3k.atomcraft.block.ModBlocks;
import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> LEAD_SMELTABLES = List.of(ModItems.RAW_LEAD,
                ModBlocks.LEAD_ORE, ModBlocks.DEEPSLATE_LEAD_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.URANIUM_235_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.URANIUM_235_INGOT.get())
                .unlockedBy("has_uranium_235_ingot", has((ModItems.URANIUM_235_INGOT))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.URANIUM_238_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.URANIUM_238_INGOT.get())
                .unlockedBy("has_uranium_238_ingot", has((ModItems.URANIUM_238_INGOT))).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIRTY_RAW_URANIUM_238.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.DIRTY_URANIUM_238_DUST.get())
                .unlockedBy("has_uranium_238_dust", has((ModItems.DIRTY_URANIUM_238_DUST))).save(recipeOutput, "atomcraft:raw_uranium_238_from_dust");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ANTI_RADIATION_HELMET.get())
                .pattern("BBB")
                .pattern("BCB")
                .pattern("   ")
                .define('B', Items.LEATHER)
                .define('C', ModItems.LEAD_PLATE.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .unlockedBy("has_lead_plate", has(ModItems.LEAD_PLATE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ANTI_RADIATION_CHESTPLATE.get())
                .pattern("BCB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', Items.LEATHER)
                .define('C', ModItems.LEAD_PLATE.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .unlockedBy("has_lead_plate", has(ModItems.LEAD_PLATE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ANTI_RADIATION_LEGGINGS.get())
                .pattern("BBB")
                .pattern("BCB")
                .pattern("B B")
                .define('B', Items.LEATHER)
                .define('C', ModItems.LEAD_PLATE.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .unlockedBy("has_lead_plate", has(ModItems.LEAD_PLATE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ANTI_RADIATION_BOOTS.get())
                .pattern("   ")
                .pattern("BBB")
                .pattern("BCB")
                .define('B', Items.LEATHER)
                .define('C', ModItems.LEAD_PLATE.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .unlockedBy("has_lead_plate", has(ModItems.LEAD_PLATE.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.URANIUM_238_HAMMER.get())
                .pattern("BBB")
                .pattern("BCB")
                .pattern(" C ")
                .define('B', ModItems.URANIUM_238_INGOT.get())
                .define('C', Items.STICK)
                .unlockedBy("has_uranium_238_ingot", has(ModItems.URANIUM_238_INGOT.get()))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LEAD_PLATE.get())
                .pattern("BC ")
                .pattern("   ")
                .pattern("   ")
                .define('B', ModItems.URANIUM_238_HAMMER.get())
                .define('C', ModItems.LEAD_INGOT.get())
                .unlockedBy("has_uranium_238_hammer", has(ModItems.URANIUM_238_INGOT.get()))
                .unlockedBy("has_lead_ingot", has(ModItems.LEAD_INGOT.get()))
                .save(recipeOutput);

        oreSmelting(recipeOutput, LEAD_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 200, "lead_ingot");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.URANIUM_235_INGOT.get(), 9)
                .requires(ModBlocks.URANIUM_235_BLOCK)
                .unlockedBy("has_uranium_235_block", has(ModBlocks.URANIUM_235_BLOCK)).save(recipeOutput, "atomcraft:uranium_235_ingot_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.URANIUM_238_INGOT.get(), 9)
                .requires(ModBlocks.URANIUM_238_BLOCK)
                .unlockedBy("has_uranium_238_block", has(ModBlocks.URANIUM_238_BLOCK)).save(recipeOutput, "atomcraft:uranium_238_ingot_from_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STONE) // n'importe quel item de sortie
                .requires(ModItems.URANIUM_235_PARTICLES_COLLECTOR.get())
                .unlockedBy("has_collector", has(ModItems.URANIUM_235_PARTICLES_COLLECTOR.get()))
                .save(recipeOutput, "atomcraft:test_collector_usage");
    }
}
