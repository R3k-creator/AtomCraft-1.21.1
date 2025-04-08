package com.r_3k.atomcraft;

import com.r_3k.atomcraft.block.ModBlocks;
import com.r_3k.atomcraft.block.entity.ModBlockEntities;
import com.r_3k.atomcraft.effect.ModEffects;
import com.r_3k.atomcraft.item.ModCreativeModTab;
import com.r_3k.atomcraft.item.ModItems;
import com.r_3k.atomcraft.screen.ModMenuTypes;
import com.r_3k.atomcraft.screen.custom.OreWasherScreen;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AtomCraft.MOD_ID)
public class AtomCraft
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "atomcraft";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AtomCraft(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModTab.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEffects.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.URANIUM_238_INGOT);
            event.accept(ModItems.URANIUM_235_INGOT);
            event.accept(ModItems.RAW_URANIUM_235);
            event.accept(ModItems.DIRTY_RAW_URANIUM_238);
            event.accept(ModItems.DIRTY_URANIUM_238_DUST);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.URANIUM_238_BLOCK);
            event.accept(ModBlocks.URANIUM_235_BLOCK);
            event.accept(ModBlocks.URANIUM_238_ORE);
            event.accept(ModBlocks.URANIUM_235_ORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.ORE_WASHER_MENU.get(), OreWasherScreen::new);
        }
    }
}
