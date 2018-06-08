package com.github.yaossg.mana_craft.worldgen;

import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.config.Config;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

public class ManaCraftWorldGens {
    private static BlockPos randomBlockPos(int chunkX, int chunkZ, Random random, int highest) {
        return new BlockPos((chunkX << 4) + random.nextInt(16), 1 + random.nextInt(highest), (chunkZ << 4) + random.nextInt(16));
    }
    private static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.manaOre.getDefaultState(), Config.sizeManaOre);
    private static final WorldGenMinable MANAINGOT = new WorldGenMinable(ManaCraftBlocks.manaIngotOre.getDefaultState(), Config.sizeManaIngotOre);
    public static void init() {
        GameRegistry.registerWorldGenerator((random, chunkX, chunkZ, world, chunkGenerator, chunkProvider) -> {
            for(int i = 0; i < Config.timesManaOre; ++i)
                MANA.generate(world, random, randomBlockPos(chunkX, chunkZ, random, Config.heightManaOre));
            for(int i = 0; i < Config.timesManaIngotOre; ++i)
                MANAINGOT.generate(world, random, randomBlockPos(chunkX, chunkZ, random, Config.heightManaIngotOre));
            if(random.nextInt(Config.mixtureChance - 1) == 0) {
                BlockPos genPos = randomBlockPos(chunkX, chunkZ, random, Config.mixtureHeight);
                for (int i = 0; i < Config.mixtureTimes; ++i) {
                    MANA.generate(world, random, genPos);
                    MANAINGOT.generate(world, random, genPos);
                }
            }
        }, 0);
    }

}
