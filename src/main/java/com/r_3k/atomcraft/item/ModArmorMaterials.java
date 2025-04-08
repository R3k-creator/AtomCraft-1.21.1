package com.r_3k.atomcraft.item;

import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final Holder<ArmorMaterial> ANTI_RADIATION_SUIT_MATERIAL = register("anti_radiation_suit",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attributes -> {
                attributes.put(ArmorItem.Type.BOOTS, 2);
                attributes.put(ArmorItem.Type.LEGGINGS, 3);
                attributes.put(ArmorItem.Type.CHESTPLATE, 4);
                attributes.put(ArmorItem.Type.HELMET, 2);
            }), 0,1.5f,0f,
            () -> Ingredient.of(    // Matériaux multiples
                    Items.LEATHER,
                    ModItems.LEAD_PLATE.get()
            ),
            SoundEvents.ARMOR_EQUIP_LEATHER
    );

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchantability, float toughness, float knockbackResistance,
                                                  Supplier<Ingredient> ingredientItem, Holder<SoundEvent> equipSound) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(AtomCraft.MOD_ID, name);
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredientItem, layers, toughness, knockbackResistance));
    }
}