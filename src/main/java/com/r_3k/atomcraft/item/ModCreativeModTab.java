package com.r_3k.atomcraft.item;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AtomCraft.MOD_ID);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

    public static final Supplier<CreativeModeTab> ATOMCRAFT_ITEMS_TAB  = CREATIVE_MODE_TAB.register("atomcraft_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.URANIUM_235_INGOT.get()))
                    .title(Component.translatable("creativetab.atomcraft.atomcraft_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.URANIUM_235_INGOT);
                        output.accept(ModItems.URANIUM_238_INGOT);
                        output.accept(ModItems.RAW_URANIUM_235);
                        output.accept(ModItems.DIRTY_RAW_URANIUM_238);
                        output.accept(ModItems.PURIFIED_RAW_URANIUM_238);
                        output.accept(ModItems.DIRTY_URANIUM_238_DUST);

                        output.accept(ModItems.LEAD_INGOT);
                        output.accept(ModItems.RAW_LEAD);
                        output.accept(ModItems.LEAD_PLATE);

                        output.accept(ModItems.ANTI_RADIATION_HELMET);
                        output.accept(ModItems.ANTI_RADIATION_CHESTPLATE);
                        output.accept(ModItems.ANTI_RADIATION_LEGGINGS);
                        output.accept(ModItems.ANTI_RADIATION_BOOTS);

                        output.accept(ModItems.URANIUM_238_HAMMER);

                        output.accept(ModItems.URANIUM_235_PARTICLES_COLLECTOR);
                        output.accept(ModItems.URANIUM_235_PARTICLE);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> ATOMCRAFT_BLOCKS_TAB  = CREATIVE_MODE_TAB.register("atomcraft_blocks_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.URANIUM_235_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, "atomcraft_items_tab"))
                    .title(Component.translatable("creativetab.atomcraft.atomcraft_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.URANIUM_235_BLOCK);
                        output.accept(ModBlocks.URANIUM_238_BLOCK);

                        output.accept(ModBlocks.URANIUM_235_ORE);
                        output.accept(ModBlocks.URANIUM_238_ORE);

                        output.accept(ModBlocks.DEEPSLATE_URANIUM_235_ORE);
                        output.accept(ModBlocks.DEEPSLATE_URANIUM_238_ORE);

                        output.accept(ModBlocks.LEAD_ORE);
                        output.accept(ModBlocks.DEEPSLATE_LEAD_ORE);

                        output.accept(ModBlocks.ORE_WASHER);
                    })
                    .build());
}
