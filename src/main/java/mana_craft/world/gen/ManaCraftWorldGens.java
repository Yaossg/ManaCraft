package mana_craft.world.gen;

import mana_craft.ManaCraft;
import mana_craft.config.ManaCraftConfig;
import mana_craft.init.ManaCraftBlocks;
import mana_craft.world.biome.BiomeMana;
import mana_craft.world.biome.BiomeManaChaos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sausage_core.api.util.world.gen.IWorldGenWrapper;
import sausage_core.api.util.world.gen.WorldGenBuilder;
import sausage_core.api.util.world.gen.WorldGenUtils;

import static mana_craft.config.ManaCraftConfig.OreGens.*;
import static net.minecraft.init.Blocks.*;
import static net.minecraft.world.gen.structure.MapGenStructureIO.registerStructureComponent;
import static net.minecraftforge.fml.common.registry.GameRegistry.registerWorldGenerator;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.instance;

public class ManaCraftWorldGens {
	static final WorldGenMinable MANA = new WorldGenMinable(ManaCraftBlocks.mana_ore.getDefaultState(), sizeManaOre);
	static final WorldGenMinable ORICHALCUM = new WorldGenMinable(ManaCraftBlocks.orichalcum_ore.getDefaultState(), sizeOrichalcumOre);

	public static void init() {
		if (ManaCraftConfig.village)
			MinecraftForge.TERRAIN_GEN_BUS.register(ManaCraftWorldGens.class);

		if (ManaCraftConfig.village_structure) {
			registerStructureComponent(StructureVillageMP.class, ManaCraft.MODID + ":ViMP");
			instance().registerVillageCreationHandler(new StructureVillageMP.Handler());
			registerStructureComponent(StructureVillageML.class, ManaCraft.MODID + ":ViML");
			instance().registerVillageCreationHandler(new StructureVillageML.Handler());
		}

		WorldGenBuilder mana = new WorldGenBuilder(IWorldGenWrapper.of(MANA))
				.atDimension(DimensionType.OVERWORLD)
				.loop(timesManaOre)
				.offsetEach(WorldGenUtils::commonOffset)
				.offsetEach((random, pos) -> pos.up(random.nextInt(heightManaOre)));
		WorldGenBuilder orichalcum = new WorldGenBuilder(IWorldGenWrapper.of(ORICHALCUM))
				.atDimension(DimensionType.OVERWORLD)
				.loop(timesOrichalcumOre)
				.offsetEach(WorldGenUtils::commonOffset)
				.offsetEach((random, pos) -> pos.up(random.nextInt(heightOrichalcumOre)));
		WorldGenBuilder mixture = new WorldGenBuilder((random, world, pos) -> {
			MANA.generate(world, random, pos);
			ORICHALCUM.generate(world, random, pos);
		})
				.atDimension(DimensionType.OVERWORLD)
				.times(mixtureChance)
				.offsetEach(WorldGenUtils::commonOffset)
				.offsetEach((random, pos) -> pos.up(random.nextInt(heightMixture)));

		int weight = ManaCraft.MODID.hashCode();

		registerWorldGenerator(mana.copy().build().toIWorldGenerator(), weight);
		registerWorldGenerator(orichalcum.copy().build().toIWorldGenerator(), weight);
		registerWorldGenerator(mixture.copy().build().toIWorldGenerator(), weight);

		registerWorldGenerator(mana.copy().atBiome(BiomeMana.class::isInstance).build().toIWorldGenerator(), weight);
		registerWorldGenerator(orichalcum.copy().atBiome(BiomeMana.class::isInstance).build().toIWorldGenerator(), weight);
		registerWorldGenerator(mixture.copy().atBiome(BiomeMana.class::isInstance).build().toIWorldGenerator(), weight);

		registerWorldGenerator(mana.copy().atBiome(BiomeManaChaos.class::isInstance).build().toIWorldGenerator(), weight);
		registerWorldGenerator(orichalcum.copy().atBiome(BiomeManaChaos.class::isInstance).build().toIWorldGenerator(), weight);
		registerWorldGenerator(mixture.copy().atBiome(BiomeManaChaos.class::isInstance).build().toIWorldGenerator(), weight);
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onGetVillageBlock(BiomeEvent.GetVillageBlockID event) {
		if (event.getBiome() instanceof BiomeMana) {
			IBlockState original = event.getOriginal();
			Block block = original.getBlock();
			event.setReplacement(original);
			event.setResult(Event.Result.DENY);
			if (block == LOG || block == LOG2)
				event.setReplacement(BONE_BLOCK.getDefaultState().withProperty(BlockBone.AXIS, EnumFacing.Axis.Y));
			else if (block == GRASS_PATH)
				event.setReplacement(ManaCraftBlocks.mana_block.getDefaultState());
			else if (block == COBBLESTONE)
				event.setReplacement(STONEBRICK.getDefaultState());
			else if (block == STONE_STAIRS)
				event.setReplacement(STONE_BRICK_STAIRS.getStateFromMeta(block.getMetaFromState(original)));
			else if (block == PLANKS)
				event.setReplacement(BRICK_BLOCK.getDefaultState());
			else if (block == OAK_STAIRS)
				event.setReplacement(BRICK_STAIRS.getStateFromMeta(block.getMetaFromState(original)));
			else if (block == OAK_FENCE)
				event.setReplacement(NETHER_BRICK_FENCE.getDefaultState());
		}
	}
}
