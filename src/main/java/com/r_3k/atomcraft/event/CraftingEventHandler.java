package com.r_3k.atomcraft.event;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.item.custom.Uranium235ParticlesCollector;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = AtomCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class CraftingEventHandler {

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();

        System.out.println("[DEBUG] Crafting event déclenché !");

        if (!(event.getInventory() instanceof CraftingContainer container)) return;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);

            if (stack.getItem() instanceof Uranium235ParticlesCollector) {
                CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                if (data == null || data.isEmpty()) continue;

                CompoundTag tag = data.copyTag();
                int particleCount = tag.getInt("ParticleCount");

                if (particleCount > 0) {
                    // Donne les particules
                    ItemStack particleStack = new ItemStack(ModItems.URANIUM_235_PARTICLES.get(), particleCount);
                    if (!player.getInventory().add(particleStack)) {
                        player.drop(particleStack, false);
                    }
                }

                // Réinitialise le collecteur
                tag.putInt("ParticleCount", 0);
                tag.putInt("NextParticleTimer", 20 * 20);

                ItemStack updatedCollector = stack.copy();
                updatedCollector.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                // Remplace dans la table de craft
                container.setItem(i, updatedCollector);
            }
        }
    }
}