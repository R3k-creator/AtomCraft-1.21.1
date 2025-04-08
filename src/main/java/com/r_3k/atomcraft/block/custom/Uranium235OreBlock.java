package com.r_3k.atomcraft.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;

public class Uranium235OreBlock extends DropExperienceBlock {
    public Uranium235OreBlock(IntProvider xpRange, Properties properties) {
        super(xpRange, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        for (int i = 0; i < 5; i++) { // ← génère 5 particules à chaque tick
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5);
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            // couleur : vert toxique, durée : simulée via vitesse nulle
            level.addParticle(new DustParticleOptions(
                            new Vector3f(0.2f, 1.0f, 0.2f), // Couleur
                            1.5f), // ← taille (aussi affecte la "présence")
                    x, y, z,
                    0.0, 0.0, 0.0 // ← pas de mouvement = désintégration plus lente visuellement
            );
        }
    }
}