package com.r_3k.atomcraft.item;

import com.r_3k.atomcraft.AtomCraft;
import com.r_3k.atomcraft.item.custom.HammerItem;
import com.r_3k.atomcraft.item.custom.Uranium235ParticlesCollector;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AtomCraft.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static final DeferredItem<Item> URANIUM_238_INGOT = ITEMS.register("uranium_238_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> URANIUM_235_INGOT = ITEMS.register("uranium_235_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DIRTY_RAW_URANIUM_238 = ITEMS.register("dirty_raw_uranium_238",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PURIFIED_RAW_URANIUM_238 = ITEMS.register("purified_raw_uranium_238",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_URANIUM_235 = ITEMS.register("raw_uranium_235",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DIRTY_URANIUM_238_DUST = ITEMS.register("dirty_uranium_238_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_LEAD = ITEMS.register("raw_lead",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> LEAD_PLATE = ITEMS.register("lead_plate",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> URANIUM_235_PARTICLES = ITEMS.register("uranium_235_particules",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<ArmorItem> ANTI_RADIATION_HELMET = ITEMS.register("anti_radiation_helmet",
            () -> new ArmorItem(ModArmorMaterials.ANTI_RADIATION_SUIT_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));

    public static final DeferredItem<ArmorItem> ANTI_RADIATION_CHESTPLATE = ITEMS.register("anti_radiation_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ANTI_RADIATION_SUIT_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));

    public static final DeferredItem<ArmorItem> ANTI_RADIATION_LEGGINGS = ITEMS.register("anti_radiation_leggings",
            () -> new ArmorItem(ModArmorMaterials.ANTI_RADIATION_SUIT_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));

    public static final DeferredItem<ArmorItem> ANTI_RADIATION_BOOTS = ITEMS.register("anti_radiation_boots",
            () -> new ArmorItem(ModArmorMaterials.ANTI_RADIATION_SUIT_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));

    public static final DeferredItem<HammerItem> URANIUM_238_HAMMER = ITEMS.register("uranium_238_hammer",
            () -> new HammerItem(ModToolTiers.URANIUM_238, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.URANIUM_238, 7f, -3.5f))));

    public static final DeferredItem<Item> URANIUM_235_PARTICLES_COLLECTOR = ITEMS.register("uranium_235_particles_collector",
            () -> new Uranium235ParticlesCollector(new Item.Properties()));
}