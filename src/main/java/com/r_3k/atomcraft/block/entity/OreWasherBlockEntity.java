package com.r_3k.atomcraft.block.entity;

import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.screen.custom.OreWasherMenu;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class OreWasherBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    protected final ContainerData data;
    private  int progress = 0;
    private int maxProgress = 150;


    public OreWasherBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ORE_WASHER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> OreWasherBlockEntity.this.progress;
                    case 1 -> OreWasherBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: OreWasherBlockEntity.this.progress = value;
                    case 1: OreWasherBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.atomcraft.ore_wahser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new OreWasherMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("ore_washer.progress", progress);
        pTag.putInt("ore_washer.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("ore_washer.progress");
        maxProgress = pTag.getInt("ore_washer.max_progress");
    }

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level1, blockPos, blockState);

            if (hasCraftingFinished()) {
                craftItem();
                resetProgress();
            }

            if (level1.getGameTime() % 20 == 0 && !level1.isClientSide()) {
                level1.playSound(null, blockPos, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 10.0f, 1.0f);
            }

        } else {
            resetProgress();
        }
    }


    private void craftItem() {
        ItemStack output = new ItemStack(ModItems.PURIFIED_RAW_URANIUM_238.get());

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.extractItem(FUEL_SLOT, 1, false);
        itemHandler.setStackInSlot(FUEL_SLOT, new ItemStack(Items.BUCKET, 1));
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private void resetProgress() {
        progress = 0;
        maxProgress = 150;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        ItemStack output = new ItemStack(ModItems.PURIFIED_RAW_URANIUM_238.get());
        return itemHandler.getStackInSlot(INPUT_SLOT).is(ModItems.DIRTY_RAW_URANIUM_238) &&
                itemHandler.getStackInSlot(FUEL_SLOT).is(Items.WATER_BUCKET) &&
                        canInsertAmountIntoOutputSlot(output.getCount()) &&
                        canInsertItemIntoOuputSlot(output);
    }

    private boolean canInsertItemIntoOuputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithFullMetadata(pRegistries);
    }
}
