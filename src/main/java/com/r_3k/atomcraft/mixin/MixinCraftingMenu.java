package com.r_3k.atomcraft.mixin;

import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.item.custom.Uranium235ParticlesCollector;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingMenu.class)
public abstract class MixinCraftingMenu {
    @Shadow
    private Player player;

    /**
     * Cette méthode est appelée à chaque changement de la grille (slotsChanged)
     * On parcourt la grille pour détecter si le collector est présent et contient des particules.
     */
    @Inject(method = "slotsChanged", at = @At("HEAD"))
    private void onSlotsChanged(Container inventory, CallbackInfo ci) {
        System.out.println("[DEBUG] onSlotsChanged déclenché dans CraftingMenu !");
        // Affichage du nombre total de slots pour débogage
        System.out.println("[DEBUG] Taille de l'inventaire : " + inventory.getContainerSize());

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (stack.getItem() instanceof Uranium235ParticlesCollector) {
                // Récupération des données personnalisées via DataComponents
                CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                if (data == null || data.isEmpty()) continue;

                CompoundTag tag = data.copyTag();
                int particleCount = tag.getInt("ParticleCount");

                // Si le collector contient des particules, on les transfère
                if (particleCount > 0) {
                    // Création du stack de particules (votre item de particules est défini dans ModItems)
                    ItemStack particleStack = new ItemStack(ModItems.URANIUM_235_PARTICLES.get(), particleCount);

                    // Affichage de débogage avant transfert
                    System.out.println("[DEBUG] Transfert de " + particleCount + " particules.");

                    if (!player.getInventory().add(particleStack)) {
                        // Si l'inventaire du joueur est plein, on fait drop l'item au sol
                        player.drop(particleStack, false);
                    }

                    // Réinitialisation du collecteur : on remet ParticleCount à 0 et on réinitialise le timer
                    tag.putInt("ParticleCount", 0);
                    tag.putInt("NextParticleTimer", 20 * 20);

                    ItemStack updatedCollector = stack.copy();
                    updatedCollector.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                    // Mise à jour du slot dans la grille
                    inventory.setItem(i, updatedCollector);

                }
            }
        }
    }
}