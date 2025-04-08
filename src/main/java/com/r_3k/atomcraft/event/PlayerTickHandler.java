package com.r_3k.atomcraft.event;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.effect.ModEffects;
import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = AtomCraft.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerTickHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onPlayerTick(Post event) {
        handleTick(event);
    }

    private static void handleTick(PlayerTickEvent event) {
        Player player = event.getEntity();
        Level level = player.level();
        RandomSource random = level.random;

        boolean hasUranium235 = false;

        ItemStack helmet = player.getInventory().getArmor(3); // Casque
        ItemStack chestplate = player.getInventory().getArmor(2); // Plastron
        ItemStack leggings = player.getInventory().getArmor(1); // Jambières
        ItemStack boots = player.getInventory().getArmor(0); // Bottes

        boolean hasAntiRadiationFullSuit =
                        helmet.getItem() == ModItems.ANTI_RADIATION_HELMET.get() &&
                        chestplate.getItem() == ModItems.ANTI_RADIATION_CHESTPLATE.get() &&
                        leggings.getItem() == ModItems.ANTI_RADIATION_LEGGINGS.get() &&
                        boots.getItem() == ModItems.ANTI_RADIATION_BOOTS.get();

        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.RAW_URANIUM_235.get() || stack.getItem() == ModItems.URANIUM_235_INGOT.get()) {
                hasUranium235 = true;
            }
        }

        if (hasUranium235 && !hasAntiRadiationFullSuit)
        {
            player.addEffect(new MobEffectInstance(ModEffects.RADIOACTIVITY_EFFECT.getDelegate(), 40, 0, false, true));

        }
        if (!hasUranium235) {
            player.removeEffect(ModEffects.RADIOACTIVITY_EFFECT.getDelegate());
            player.removeEffect(MobEffects.POISON.getDelegate());
        }
        if (hasAntiRadiationFullSuit) {
            player.removeEffect(ModEffects.RADIOACTIVITY_EFFECT.getDelegate());
            player.removeEffect(MobEffects.POISON.getDelegate());
        }
    }
}