package com.r_3k.atomcraft.item.custom;

import com.r_3k.atomcraft.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class Uranium235ParticlesCollector extends Item {

    public Uranium235ParticlesCollector(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide) {
            CompoundTag tag;
            CustomData data = stack.get(DataComponents.CUSTOM_DATA);

            // Initialisation des données si elles n'existent pas
            if (data == null || data.isEmpty()) {
                tag = new CompoundTag();
                tag.putInt("NextParticleTimer", 5 * 20);  // Initialisation du timer si absent
                tag.putInt("ParticleCount", 0);  // Initialisation du compteur de particules
            } else {
                tag = data.copyTag();
            }

            int timer = tag.getInt("NextParticleTimer");
            int count = tag.getInt("ParticleCount");

            // Gestion du timer et incrémentation des particules
            if (timer > 0) {
                timer--;  // Décrémentation du timer
            } else {
                if (count < 100) {
                    count++;  // Incrémentation des particules, maximum 100
                }
                timer = 5 * 20;  // Réinitialisation du timer
            }

            tag.putInt("NextParticleTimer", timer);
            tag.putInt("ParticleCount", count);

            // Mise à jour des données de l'item
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CompoundTag tag = getOrCreateCustomData(stack);

        int particleCount = tag.getInt("ParticleCount");
        int timer = tag.getInt("NextParticleTimer");
        int seconds = timer / 20;

        tooltipComponents.add(Component.literal("Particules: " + particleCount + "/100"));
        tooltipComponents.add(Component.literal("Temps avant prochaine particule: " + seconds + " sec"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        CompoundTag tag = getOrCreateCustomData(stack);

        int particleCount = tag.getInt("ParticleCount");

        // Création de la pile de particules
        ItemStack particles = new ItemStack(ModItems.URANIUM_235_PARTICLE.get(), particleCount);

        // Réinitialisation du collecteur
        tag.putInt("ParticleCount", 0);
        tag.putInt("NextParticleTimer", 5 * 20);

        // Création de l'item collector mis à jour
        ItemStack collectorCopy = stack.copy();
        collectorCopy.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        // Ajout de l'item de collecteur et des particules au résultat du craft
        ItemStack resultStack = new ItemStack(ModItems.URANIUM_235_PARTICLE.get(), particleCount);
        resultStack.setCount(particleCount);


        return collectorCopy;  // On retourne l'item collector mis à jour
    }

    private CompoundTag getOrCreateCustomData(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return (data == null || data.isEmpty()) ? new CompoundTag() : data.copyTag();
    }
}