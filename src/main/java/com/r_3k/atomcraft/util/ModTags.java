package com.r_3k.atomcraft.util;

import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_URANIUM_238_TOOLS = createTag("needs_uranium_238_tool");
        public static final TagKey<Block> INCORRECT_FOR_URANIUM_238_TOOLS = createTag("incorrect_for_uranium_238_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, name));
        }
    }
}