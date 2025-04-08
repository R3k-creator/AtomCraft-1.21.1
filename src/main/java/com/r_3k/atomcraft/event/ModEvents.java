package com.r_3k.atomcraft.event;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.item.custom.HammerItem;
import com.r_3k.atomcraft.item.custom.Uranium235ParticlesCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = AtomCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();

            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            // Compter combien de blocs on casse vraiment (optionnel)
            int blocksBroken = 0;

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                boolean success = serverPlayer.gameMode.destroyBlock(pos);
                if (success) blocksBroken++;
                HARVESTED_BLOCKS.remove(pos);
            }

            // Réparer la durabilité excessive et retirer 1 seul point
            // Ne rien faire si aucun bloc n'a été cassé
            if (blocksBroken > 0) {
                // Répare les points perdus automatiquement (sauf 1)
                mainHandItem.setDamageValue(mainHandItem.getDamageValue() - (blocksBroken - 1));
            }
        }
    }
}
