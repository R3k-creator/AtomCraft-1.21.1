package com.r_3k.atomcraft.screen;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.function.Predicate;

public class ModRestrictedSlot extends SlotItemHandler {

    private final Predicate<ItemStack> isItemValid;

    public ModRestrictedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> isItemValid) {
        super(itemHandler, index, xPosition, yPosition);
        this.isItemValid = isItemValid;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return isItemValid.test(stack);
    }
}