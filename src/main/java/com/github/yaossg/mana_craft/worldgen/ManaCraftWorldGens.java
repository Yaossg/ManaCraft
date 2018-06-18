package com.github.yaossg.mana_craft.worldgen;

import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.config.Config;
import com.github.yaossg.sausage_core.api.util.IWorldGenBiome;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ManaCraftWorldGens {
    private static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.manaOre.getDefaultState(), Config.sizeManaOre);
    private static final WorldGenMinable MANAINGOT = new WorldGenMinable(ManaCraftBlocks.manaIngotOre.getDefaultState(), Config.sizeManaIngotOre);
    public static void init() {
        GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> {
            if(world.provider.getDimensionType() != DimensionType.OVERWORLD) return;
            for(int i = 0; i < Config.timesManaOre; ++i)
                MANA.generate(world, random, IWorldGenBiome.randomPos(random, chunkX, chunkZ, Config.heightManaOre));
            for(int i = 0; i < Config.timesManaIngotOre; ++i)
                MANAINGOT.generate(world, random, IWorldGenBiome.randomPos(random,chunkX, chunkZ, Config.heightManaIngotOre));
            if(random.nextInt(Config.mixtureChance - 1) == 0) {
                BlockPos genPos = IWorldGenBiome.randomPos(random,chunkX, chunkZ, Config.mixtureHeight);
                for (int i = 0; i < Config.mixtureTimes; ++i) {
                    MANA.generate(world, random, genPos);
                    MANAINGOT.generate(world, random, genPos);
                }
            }
        }, 0);
    }

}
