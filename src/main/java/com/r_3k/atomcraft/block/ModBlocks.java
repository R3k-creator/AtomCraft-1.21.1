package com.r_3k.atomcraft.block;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.block.custom.OreWasherBlock;
import com.r_3k.atomcraft.block.custom.Uranium235OreBlock;
import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AtomCraft.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String  name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static final DeferredBlock<Block> URANIUM_235_BLOCK = registerBlock("uranium_235_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> URANIUM_238_BLOCK = registerBlock("uranium_238_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<Block> URANIUM_235_ORE = registerBlock("uranium_235_ore",
            () -> new Uranium235OreBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .lightLevel(state -> 10)));

    public static final DeferredBlock<Block> URANIUM_238_ORE = registerBlock("uranium_238_ore",
            () -> new DropExperienceBlock(UniformInt.of(4, 8), BlockBehaviour.Properties.of()
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DEEPSLATE_URANIUM_238_ORE = registerBlock("deepslate_uranium_238_ore",
            () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.of()
                    .strength(4.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> DEEPSLATE_URANIUM_235_ORE = registerBlock("deepslate_uranium_235_ore",
            () -> new Uranium235OreBlock(UniformInt.of(4, 8), BlockBehaviour.Properties.of()
                    .strength(4.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE)
                    .lightLevel(state -> 10)));

    public static final DeferredBlock<Block> LEAD_ORE = registerBlock("lead_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 4), BlockBehaviour.Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ORE_WASHER = registerBlock("ore_washer",
            () -> new OreWasherBlock(BlockBehaviour.Properties.of()));
}
