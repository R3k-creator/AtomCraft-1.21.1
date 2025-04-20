package com.r_3k.atomcraft.recipe;

import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, AtomCraft.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, AtomCraft.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ParticleExtractionRecipe>> PARTICLE_EXTRACTION_SERIALIZER =
            SERIALIZERS.register("particle_extraction", ParticleExtractionRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ParticleExtractionRecipe>> PARTICLE_EXTRACTION_TYPE =
            TYPES.register("particle_extraction", () -> new RecipeType<>() {
                @Override public String toString() { return "particle_extraction"; }
            });

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}