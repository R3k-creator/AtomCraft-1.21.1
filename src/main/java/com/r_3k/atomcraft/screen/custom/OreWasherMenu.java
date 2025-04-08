package com.r_3k.atomcraft.screen.custom;

import com.r_3k.atomcraft.block.ModBlocks;
import com.r_3k.atomcraft.block.entity.OreWasherBlockEntity;
import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.screen.ModMenuTypes;
import com.r_3k.atomcraft.screen.ModRestrictedSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class OreWasherMenu extends AbstractContainerMenu {
    public final OreWasherBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public OreWasherMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public OreWasherMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ORE_WASHER_MENU.get(), pContainerId);
        this.blockEntity = ((OreWasherBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.addSlot(new ModRestrictedSlot(blockEntity.itemHandler, 0, 44, 29,
                stack -> stack.is(ModItems.DIRTY_RAW_URANIUM_238)));

        this.addSlot(new ModRestrictedSlot(blockEntity.itemHandler, 1, 44, 65,
                stack -> stack.is(Items.WATER_BUCKET)));

        this.addSlot(new ModRestrictedSlot(blockEntity.itemHandler, 2, 118, 47,
                stack -> false));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 3;
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ORE_WASHER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInventory, sj + (si + 1) * 9, 0 + 8 + sj * 18, 14 + 84 + si * 18));
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInventory, si, 0 + 8 + si * 18, 14 + 142));
    }
}
