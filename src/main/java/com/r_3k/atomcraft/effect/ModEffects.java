package com.r_3k.atomcraft.effect;

import com.r_3k.atomcraft.AtomCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, AtomCraft.MOD_ID);

    public static final Holder<MobEffect> RADIOACTIVITY_EFFECT = MOB_EFFECTS.register("radioactivity",
            () -> new RadioactivityEffect(MobEffectCategory.HARMFUL, 0x33FF33));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
