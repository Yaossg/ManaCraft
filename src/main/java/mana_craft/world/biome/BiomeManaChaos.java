package mana_craft.world.biome;

import com.google.common.collect.ImmutableList;
import mana_craft.block.BlockManaFoot;
import mana_craft.block.BlockManaHead;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.Random;

import static mana_craft.block.ManaCraftBlocks.*;

public class BiomeManaChaos extends BiomeMana {
	public static BiomeManaChaos get() {
		return new BiomeManaChaos(new BiomeProperties("ManaChaos").setBaseBiome("Mana").setBaseHeight(0.08f).setHeightVariation(0.3f));
	}

	public static BiomeManaChaos getHills() {
		return new BiomeManaChaos(new BiomeProperties("ManaChaosHills").setBaseBiome("ManaHills").setBaseHeight(1).setHeightVariation(0.5f));
	}

	protected BiomeManaChaos(BiomeProperties properties) {
		super(properties);
		decorator.mushroomsPerChunk = 15;
		decorator.reedsPerChunk = 0;
		spawnableCreatureList.add(new SpawnListEntry(EntityBat.class, 20, 3, 13));
	}

	@Override
	public float getSpawningChance() {
		return 0.15f;
	}

	@Override
	public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
		super.genTerrainBlocks(worldIn, rand, chunkPrimerIn, x, z, -rand.nextInt(10));
	}

	private static final WorldGenBigTree BIG_TREE = new WorldGenBigTree(false);
	private static final WorldGenTrees HIGH_TREE = new WorldGenTrees(false, 7, LOG, LEAF, false);
	private static final WorldGenMegaJungle MEGA_TREE = new WorldGenMegaJungle(false, 11, 17, LOG, LEAF);

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		switch(rand.nextInt(4)) {
			case 0:
			case 1:
			default:
				return BIG_TREE;
			case 2:
				return HIGH_TREE;
			case 3:
				return MEGA_TREE;
		}
	}

	private static final WorldGenTallGrass DEAD_BUSH = new WorldGenTallGrass(BlockTallGrass.EnumType.DEAD_BUSH);

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand) {
		return DEAD_BUSH;
	}

	@Override
	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
		switch(rand.nextInt(4)) {
			case 0:
			default:
				return BlockFlower.EnumFlowerType.ORANGE_TULIP;
			case 1:
				return BlockFlower.EnumFlowerType.WHITE_TULIP;
			case 2:
				return BlockFlower.EnumFlowerType.RED_TULIP;
			case 3:
				return BlockFlower.EnumFlowerType.PINK_TULIP;
		}
	}

	private static final List<DoubleFlowerEntry> LIST = ImmutableList.of(
			new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.SYRINGA, 12),
			new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.ROSE, 3),
			new DoubleFlowerEntry(BlockDoublePlant.EnumPlantType.PAEONIA, 1));

	@Override
	public List<DoubleFlowerEntry> getDoubleFlowerList() {
		return LIST;
	}

	private static final WorldGenerator sky = new WorldGenerator() {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos pos) {
			int y = worldIn.getTopSolidOrLiquidBlock(pos).getY();
			IBlockState state;
			state = rand.nextInt(64) == 0
					? mana_head.getDefaultState().withProperty(BlockManaHead.FACING, EnumFacing.Plane.HORIZONTAL.random(rand))
					: rand.nextInt(4) == 0
					? mana_body.getDefaultState()
					: mana_foot.getDefaultState().withProperty(BlockManaFoot.FACING, EnumFacing.Plane.HORIZONTAL.random(rand));
			worldIn.setBlockState(pos.up(y), state);
			return true;
		}
	};

	@Override
	public void generateDoubleFlower(World worldIn, Random rand, BlockPos pos) {
		if(rand.nextInt(16) == 0)
			super.generateDoubleFlower(worldIn, rand, pos);
	}

	private static final WorldGenLakes LAVA_LAKES = new WorldGenLakes(Blocks.LAVA);

	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
		for(int i = 0; i < 4; i++)
			sky.generate(worldIn, rand, randomPos(worldIn, rand, pos).up(rand.nextInt(48) - rand.nextInt(36)));
		LAVA_LAKES.generate(worldIn, rand, pos.add(rand.nextInt(16) + 8, 256, rand.nextInt(16) + 8));
		super.decorate(worldIn, rand, pos);
	}
}
