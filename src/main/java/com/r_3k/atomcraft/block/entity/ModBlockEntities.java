package com.r_3k.atomcraft.block.entity;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AtomCraft.MOD_ID);

    public static final Supplier<BlockEntityType<OreWasherBlockEntity>> ORE_WASHER_BE =
            BLOCK_ENTITIES.register("ore_washer_be", () -> BlockEntityType.Builder.of(
                    OreWasherBlockEntity::new, ModBlocks.ORE_WASHER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}