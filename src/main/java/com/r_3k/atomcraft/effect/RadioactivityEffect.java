package com.r_3k.atomcraft.effect;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class RadioactivityEffect extends MobEffect {

    public RadioactivityEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static void applyPoisonEffect(LivingEntity livingEntity, int level, int duration) {
        if (!livingEntity.hasEffect(MobEffects.POISON)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, duration, level - 1, false, false));
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        RandomSource random = level.random;

        // Si l'entité n'a pas encore de compteur de ticks, initialise-le
        int tickCounter = livingEntity.getPersistentData().getInt("radioactivityTickCounter");

        // Incrémente le compteur à chaque tick
        tickCounter++;

        // Si le compteur atteint 10, réinitialise-le et applique l'effet
        if (tickCounter >= 10) {
            tickCounter = 0;

            // Applique l'effet de poison et les dégâts
            applyPoisonEffect(livingEntity, 1, 80);
            livingEntity.hurt(livingEntity.damageSources().generic(), 1.0F);
            livingEntity.hurt(livingEntity.damageSources().mobAttack(livingEntity), 0.25F);

            // Crée les particules
            for (int i = 0; i < 3; i++) { // Nombre de particules
                double x = livingEntity.getX() + (random.nextDouble() - 0.5);
                double y = livingEntity.getY() + 1.0 + random.nextDouble(); // au-dessus du joueur
                double z = livingEntity.getZ() + (random.nextDouble() - 0.5);

                level.addParticle(new DustParticleOptions(
                                new Vector3f(0.2f, 1.0f, 0.2f), // vert toxique
                                1.5f),
                        x, y, z,
                        0.0, 0.0, 0.0 // particules statiques
                );
            }
        }

        // Sauvegarde le compteur pour le prochain appel
        livingEntity.getPersistentData().putInt("radioactivityTickCounter", tickCounter);

        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
