package com.r_3k.atomcraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;

public record ParticleExtractionRecipe(Ingredient ingredient, ItemStack outputPrototype) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput input, Level world) {
        if (world.isClientSide()) return false;
        if (input.size() != 1) return false;
        return ingredient.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider provider) {
        if (input.size() != 1) return ItemStack.EMPTY;
        ItemStack in = input.getItem(0);

        if (ingredient.test(in)) {
            CustomData data = in.get(DataComponents.CUSTOM_DATA);
            CompoundTag tag = (data == null || data.isEmpty()) ? new CompoundTag() : data.copyTag();
            int count = tag.getInt("ParticleCount");
            ItemStack out = outputPrototype.copy();
            out.setCount(Math.max(1, count));
            return out;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(SingleRecipeInput input) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(1, ItemStack.EMPTY);
        ItemStack in = input.item();
        if (ingredient.test(in)) {
            Item item = in.getItem();
            if (item.hasCraftingRemainingItem(in)) {
                remainders.set(0, item.getCraftingRemainingItem(in));
            }
        }
        return remainders;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(ingredient);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return outputPrototype.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.PARTICLE_EXTRACTION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.PARTICLE_EXTRACTION_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ParticleExtractionRecipe> {
        public static final MapCodec<ParticleExtractionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(ParticleExtractionRecipe::ingredient),
                ItemStack.CODEC.fieldOf("result").forGetter(ParticleExtractionRecipe::outputPrototype)
        ).apply(inst, ParticleExtractionRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ParticleExtractionRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, ParticleExtractionRecipe::ingredient,
                        ItemStack.STREAM_CODEC, ParticleExtractionRecipe::outputPrototype,
                        ParticleExtractionRecipe::new
                );

        @Override
        public MapCodec<ParticleExtractionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ParticleExtractionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}