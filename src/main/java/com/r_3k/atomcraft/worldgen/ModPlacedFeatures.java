package com.r_3k.atomcraft.worldgen;

import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> OVERWORLD_URANIUM_238_ORE_PLACED_KEY = registerKey("uranium_238_placed");
    public static final ResourceKey<PlacedFeature> OVERWORLD_URANIUM_235_ORE_PLACED_KEY = registerKey("uranium_235_placed");
    public static final ResourceKey<PlacedFeature> OVERWORLD_LEAD_ORE_PLACED_KEY = registerKey("lead_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, OVERWORLD_URANIUM_238_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_URANIUM_238_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(30))));

        register(context, OVERWORLD_URANIUM_235_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_URANIUM_235_ORE_KEY),
                ModOrePlacement.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(30))));

        register(context, OVERWORLD_LEAD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LEAD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(30))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}