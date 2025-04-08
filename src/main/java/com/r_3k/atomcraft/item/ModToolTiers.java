package com.r_3k.atomcraft.item;

import com.r_3k.atomcraft.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier URANIUM_238 = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_URANIUM_238_TOOLS,
            1000, 2f, 2.5f,0, () -> Ingredient.of(ModItems.URANIUM_238_INGOT));
}
