package com.github.yaossg.mana_craft.world.biome;

import com.github.yaossg.mana_craft.entity.EntityManaShooter;
import com.github.yaossg.mana_craft.world.gen.ManaCraftWorldGens;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.Random;

public class BiomeMana extends Biome {
    static BiomeMana get() {
        return new BiomeMana(new BiomeProperties("Mana").setHeightVariation(0.08f));
    }
    static BiomeMana getHills() {
        return new BiomeMana(new BiomeProperties("ManaHills").setBaseHeight(1).setHeightVariation(0.12f));
    }

    protected BiomeMana(BiomeProperties properties) {
        super(properties.setWaterColor(0xFF0033));
        decorator.treesPerChunk = 2;
        decorator.grassPerChunk = 8;
        decorator.waterlilyPerChunk = 4;
        decorator.mushroomsPerChunk = 4;
        decorator.flowersPerChunk = 8;
        decorator.sandPatchesPerChunk = 0;
        decorator.gravelPatchesPerChunk = 0;
        spawnableCreatureList.clear();
        spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 1, 3));
        spawnableCreatureList.add(new SpawnListEntry(EntityManaShooter.class, 1, 1, 1));
    }

    @Override
    final public int getSkyColorByTemp(float currentTemperature) {
        return 0xFF33CC;
    }

    @Override
    final public int getModdedBiomeGrassColor(int original) {
        return 0xFF33CC;
    }

    @Override
    final public int getModdedBiomeFoliageColor(int original) {
        return 0xFF33CC;
    }

    static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
    static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
    private static final WorldGenShrub SHRUB_FEATURE = new WorldGenShrub(LOG, LEAF);

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        switch (rand.nextInt(4)) {
            case 0:
            case 1:
            default:
                return TREE_FEATURE;
            case 2:
                return SWAMP_FEATURE;
            case 3:
                return SHRUB_FEATURE;
        }
    }

    private static final WorldGenTallGrass FERN = new WorldGenTallGrass(BlockTallGrass.EnumType.FERN);
    private static final WorldGenTallGrass GRASS = new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
        return rand.nextInt(4) == 0 ? FERN : GRASS;
    }

    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
        return rand.nextInt(4) == 0 ? BlockFlower.EnumFlowerType.BLUE_ORCHID : BlockFlower.EnumFlowerType.POPPY;
    }

    public static class DoubleFlowerEntry extends WeightedRandom.Item {
        public final BlockDoublePlant.EnumPlantType type;
        public DoubleFlowerEntry(BlockDoublePlant.EnumPlantType type, int itemWeightIn) {
            super(itemWeightIn);
            this.type = type;
        }
    }

    private static final List<DoubleFlowerEntry> LIST = ImmutableList.of(
            new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.GRASS, 100),
            new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.FERN, 25),
            new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.SYRINGA, 4),
            new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.ROSE, 1));

    public List<DoubleFlowerEntry> getDoubleFlowerList() {
        return LIST;
    }

    protected static BlockPos randomPos(World worldIn, Random rand, BlockPos pos) {
        return worldIn.getTopSolidOrLiquidBlock(pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8));
    }

    public void generateDoubleFlower(World worldIn, Random rand, BlockPos pos) {
        DOUBLE_PLANT_GENERATOR.setPlantType(WeightedRandom.getRandomItem(rand, getDoubleFlowerList()).type);
        DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, randomPos(worldIn, rand, pos));
    }

    private static final WorldGenLakes LAKES = new WorldGenLakes(Blocks.WATER);

    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos) {
        LAKES.generate(worldIn, rand, pos.add(rand.nextInt(16) + 8, 256, rand.nextInt(16) + 8));
        Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
        generateDoubleFlower(worldIn, rand, pos);
        ManaCraftWorldGens.generate(rand, chunk.x, chunk.z, worldIn);
        super.decorate(worldIn, rand, pos);
    }
}
