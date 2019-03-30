package mana_craft.world.gen;

import mana_craft.block.BlockManaProducer;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.entity.ManaCraftVillagers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

import static mana_craft.block.ManaCraftBlocks.*;
import static net.minecraft.world.gen.structure.StructureVillagePieces.*;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class StructureVillageMP extends Village {
	@SuppressWarnings("unused")
	public StructureVillageMP() {}

	public StructureVillageMP(Start start, int type, StructureBoundingBox box, EnumFacing facing) {
		super(start, type);
		setCoordBaseMode(facing);
		boundingBox = box;
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox box) {
		if(averageGroundLvl < 0) {
			averageGroundLvl = getAverageGroundLevel(worldIn, box);
			if(averageGroundLvl < 0)
				return true;
			boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 7 - 1, 0);
		}
		IBlockState cobblestone = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
		IBlockState glass = mana_glass.getDefaultState();
		IBlockState manaBlock = ManaCraftBlocks.mana_block.getDefaultState();
		IBlockState fence = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());

		fillWithAir(worldIn, boundingBox, 0, 0, 0, 7, 7, 7);

		fillWithBlocks(worldIn, boundingBox, 1, 1, 0, 6, 1, 0, fence, fence, false);
		fillWithBlocks(worldIn, boundingBox, 0, 1, 1, 0, 1, 6, fence, fence, false);
		fillWithBlocks(worldIn, boundingBox, 1, 1, 6, 6, 1, 6, fence, fence, false);
		fillWithBlocks(worldIn, boundingBox, 6, 1, 1, 6, 1, 6, fence, fence, false);
		setBlockState(worldIn, Blocks.AIR.getDefaultState(), 3, 1, 0, boundingBox);

		fillWithBlocks(worldIn, boundingBox, 0, 0, 0, 7, 0, 7, cobblestone, cobblestone, false);
		fillWithBlocks(worldIn, boundingBox, 0, 0, 0, 0, 7, 0, cobblestone, cobblestone, false);
		fillWithBlocks(worldIn, boundingBox, 6, 0, 0, 7, 7, 0, cobblestone, cobblestone, false);
		fillWithBlocks(worldIn, boundingBox, 0, 0, 6, 0, 7, 7, cobblestone, cobblestone, false);
		fillWithBlocks(worldIn, boundingBox, 6, 0, 6, 7, 7, 7, cobblestone, cobblestone, false);
		fillWithBlocks(worldIn, boundingBox, 0, 6, 0, 7, 7, 7, cobblestone, cobblestone, false);

		setBlockState(worldIn, manaBlock, 3, 1, 3, boundingBox);
		setBlockState(worldIn, manaBlock, 2, 1, 3, boundingBox);
		setBlockState(worldIn, manaBlock, 3, 1, 2, boundingBox);
		setBlockState(worldIn, manaBlock, 4, 1, 3, boundingBox);
		setBlockState(worldIn, manaBlock, 3, 1, 4, boundingBox);
		setBlockState(worldIn, orichalcum_block.getDefaultState(), 2, 1, 2, boundingBox);
		setBlockState(worldIn, orichalcum_block.getDefaultState(), 4, 1, 2, boundingBox);
		setBlockState(worldIn, orichalcum_block.getDefaultState(), 2, 1, 4, boundingBox);
		setBlockState(worldIn, orichalcum_block.getDefaultState(), 4, 1, 4, boundingBox);
		setBlockState(worldIn, manaBlock, 2, 2, 2, boundingBox);
		setBlockState(worldIn, manaBlock, 4, 2, 2, boundingBox);
		setBlockState(worldIn, manaBlock, 2, 2, 4, boundingBox);
		setBlockState(worldIn, manaBlock, 4, 2, 4, boundingBox);

		setBlockState(worldIn, manaBlock, 2, 3, 3, boundingBox);
		setBlockState(worldIn, manaBlock, 3, 3, 2, boundingBox);
		setBlockState(worldIn, manaBlock, 4, 3, 3, boundingBox);
		setBlockState(worldIn, manaBlock, 3, 3, 4, boundingBox);
		setBlockState(worldIn, mana_lantern.getDefaultState(), 3, 4, 3, boundingBox);

		setBlockState(worldIn, glass, 3, 3, 3, boundingBox);

		setBlockState(worldIn, glass, 2, 2, 3, boundingBox);
		setBlockState(worldIn, glass, 4, 2, 3, boundingBox);
		setBlockState(worldIn, glass, 3, 2, 4, boundingBox);

		//noinspection ConstantConditions <-look at constrcutor
		setBlockState(worldIn, mana_producer.getDefaultState().withProperty(BlockManaProducer.FACING, getCoordBaseMode().getOpposite()), 3, 2, 3, boundingBox);

		spawnVillagers(worldIn, boundingBox, 3, 1, 0, 2);

		return true;
	}

	@Override
	protected VillagerProfession chooseForgeProfession(int count, VillagerProfession prof) {
		return ManaCraftVillagers.mana_priest;
	}

	public static class Handler implements IVillageCreationHandler {
		@Override
		public PieceWeight getVillagePieceWeight(Random random, int size) {
			return new PieceWeight(StructureVillageMP.class, 15, MathHelper.getInt(random, size, 1 + size));
		}

		@Override
		public Class<?> getComponentClass() {
			return StructureVillageMP.class;
		}

		@Override
		public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int size) {
			StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 7, 7, 7, facing);
			//noinspection ConstantConditions <-stupid idea thinks it always false
			return canVillageGoDeeper(box) && StructureComponent.findIntersecting(pieces, box) == null ? new StructureVillageMP(startPiece, size, box, facing) : null;
		}
	}
}
