package com.r_3k.atomcraft.worldgen;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_URANIUM_238_ORE_KEY = registerKey("uranium_238_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_URANIUM_235_ORE_KEY = registerKey("uranium_235_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LEAD_ORE_KEY = registerKey("lead_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldUranium238Ores = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.URANIUM_238_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_URANIUM_238_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldUranium235Ores = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.URANIUM_235_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_URANIUM_235_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldLeadOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.LEAD_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_LEAD_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_URANIUM_238_ORE_KEY, Feature.ORE, new OreConfiguration(overworldUranium238Ores, 9));
        register(context, OVERWORLD_URANIUM_235_ORE_KEY, Feature.ORE, new OreConfiguration(overworldUranium235Ores, 9));
        register(context, OVERWORLD_LEAD_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLeadOres, 9));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}