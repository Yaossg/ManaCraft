package com.github.yaossg.mana_craft.world.gen;

import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.world.biome.BiomeMana;
import com.github.yaossg.mana_craft.world.biome.BiomeManaChaos;
import com.github.yaossg.sausage_core.api.util.worldgen.IWorldGenBiome;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.github.yaossg.mana_craft.config.ManaCraftConfig.OreGens.*;

class WorldGenMana implements IWorldGenBiome {
    private static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.manaOre.getDefaultState(), sizeManaOre);
    private static final WorldGenMinable MANA_INGOT = new WorldGenMinable(ManaCraftBlocks.manaIngotOre.getDefaultState(), sizeManaIngotOre);
    private void generate(Random random, int chunkX, int chunkZ, World world) {
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
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, Biome biome) {
        if(world.provider.getDimensionType() == DimensionType.OVERWORLD) {
            int times = 1;
            if(biome instanceof BiomeMana) ++times;
            if(biome instanceof BiomeManaChaos) ++times;
            for (int i = 0; i < times; i++) generate(random, chunkX, chunkZ, world);
        }
    }
}
