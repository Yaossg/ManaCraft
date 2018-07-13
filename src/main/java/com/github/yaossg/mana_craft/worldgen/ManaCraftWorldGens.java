package com.github.yaossg.mana_craft.worldgen;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.sausage_core.api.util.worldgen.IWorldGenBiome;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.github.yaossg.mana_craft.config.ManaCraftConfig.OreGens.*;

public class ManaCraftWorldGens {
    private static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.manaOre.getDefaultState(), sizeManaOre);
    private static final WorldGenMinable MANA_INGOT = new WorldGenMinable(ManaCraftBlocks.manaIngotOre.getDefaultState(), sizeManaIngotOre);

    public static void init() {
        GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> {
            if(world.provider.getDimensionType() != DimensionType.OVERWORLD) return;
            for (int i = 0; i < timesManaOre; ++i)
                MANA.generate(world, random, IWorldGenBiome.randomPos(random, chunkX, chunkZ, 0, heightManaOre));
            for (int i = 0; i < timesManaIngotOre; ++i)
                MANA_INGOT.generate(world, random, IWorldGenBiome.randomPos(random, chunkX, chunkZ, 0, heightManaIngotOre));
            float chance = mixtureChance;
            do
                if(random.nextFloat() < chance) {
                    BlockPos genPos = IWorldGenBiome.randomPos(random, chunkX, chunkZ, 0, heightMixture);
                    for (int i = 0; i < 4; ++i) {
                        MANA.generate(world, random, genPos);
                        MANA_INGOT.generate(world, random, genPos);
                    }
                }
            while (--chance > 0);
        }, ManaCraft.MODID.hashCode());
    }

}
