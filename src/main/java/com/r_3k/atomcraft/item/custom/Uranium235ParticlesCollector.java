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

            if (data == null || data.isEmpty()) {
                tag = new CompoundTag();
            } else {
                tag = data.copyTag();
            }

            int timer = tag.getInt("NextParticleTimer");
            int count = tag.getInt("ParticleCount");

            if (!tag.contains("NextParticleTimer")) {
                timer = 10 * 20;
            } else if (timer > 0) {
                timer--;
            } else {
                if (count < 100) {
                    count++;
                }
                timer = 10 * 20;
            }

            tag.putInt("NextParticleTimer", timer);
            tag.putInt("ParticleCount", count);

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
        ItemStack particles = new ItemStack(ModItems.URANIUM_235_PARTICLES.get(), particleCount);

        // Réinitialise le collecteur
        tag.putInt("ParticleCount", 0);
        tag.putInt("NextParticleTimer", 20 * 20);

        ItemStack collectorCopy = stack.copy();
        collectorCopy.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        // On doit retourner le collecteur ET les particules : ça se fait via `getRecipeRemainder()`
        // mais comme on ne peut renvoyer qu’un seul ItemStack ici, on gère ça autrement (via `CraftingEvent`, cf ci-dessous)

        return collectorCopy; // On retourne au moins l’item intact ici
    }

    private CompoundTag getOrCreateCustomData(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return (data == null || data.isEmpty()) ? new CompoundTag() : data.copyTag();
    }
}