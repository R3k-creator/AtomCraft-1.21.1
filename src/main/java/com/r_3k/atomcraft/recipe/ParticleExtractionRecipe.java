package com.r_3k.atomcraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;

public record ParticleExtractionRecipe(Ingredient ingredient,
                                       ItemStack outputPrototype)
        implements Recipe<RecipeInput> {

    @Override
    public boolean matches(RecipeInput inv, Level world) {
        if (world.isClientSide) return false;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && ingredient.test(stack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput inv, HolderLookup.Provider prov) {
        // on récupère le CustomData du collecteur et on lit "ParticleCount"
        for (int i = 0; i < inv.size(); i++) {
            ItemStack in = inv.getItem(i);
            if (ingredient.test(in)) {
                CustomData data = in.get(DataComponents.CUSTOM_DATA);
                CompoundTag tag = (data == null || data.isEmpty()) ? new CompoundTag() : data.copyTag();
                int count = tag.getInt("ParticleCount");
                ItemStack out = outputPrototype.copy();
                out.setCount(count);
                return out;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(RecipeInput inv) {
        // on renvoie le collecteur mis à jour (ParticleCount = 0) grâce à getCraftingRemainingItem
        NonNullList<ItemStack> remainders = NonNullList.withSize(inv.size(), ItemStack.EMPTY);
        for (int i = 0; i < inv.size(); i++) {
            ItemStack in = inv.getItem(i);
            if (ingredient.test(in)) {
                Item item = in.getItem();
                if (item.hasCraftingRemainingItem(in)) {
                    remainders.set(i, item.getCraftingRemainingItem(in));
                }
            }
        }
        return remainders;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(ingredient);
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider prov) {
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

    // --- Sérialisation (codec + streamCodec) ---

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

        @Override public MapCodec<ParticleExtractionRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, ParticleExtractionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}