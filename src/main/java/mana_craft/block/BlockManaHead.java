package mana_craft.block;

import mana_craft.ManaCraft;
import mana_craft.entity.EntityManaShooter;
import mana_craft.init.ManaCraftItems;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import static mana_craft.init.ManaCraftBlocks.*;
import static net.minecraft.block.state.BlockWorldState.hasState;
import static net.minecraft.block.state.pattern.BlockStateMatcher.forBlock;
import static net.minecraft.init.Blocks.AIR;
import static sausage_core.api.util.common.Conversions.To.item;

public class BlockManaHead extends BlockManaBody {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	private static BlockPattern patternBase;
	private static BlockPattern pattern;

	public BlockManaHead() {
		super(1.5f, 8);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		drops.add(new ItemStack(ManaCraftItems.mana_diamond));
	}

	public static void init() {
		patternBase = FactoryBlockPattern.start().aisle("___", "_#_", "M#M")
				.where('_', hasState(forBlock(AIR)))
				.where('#', hasState(forBlock(mana_body)))
				.where('M', BlockManaHead::foot)
				.build();
		pattern = FactoryBlockPattern.start().aisle("_^_", "_#_", "M#M")
				.where('^', hasState(forBlock(mana_head)))
				.where('_', hasState(forBlock(AIR)))
				.where('#', hasState(forBlock(mana_body)))
				.where('M', BlockManaHead::foot)
				.build();

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item(mana_head), new Bootstrap.BehaviorDispenseOptional() {
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				BlockPos pos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
				if (successful = patternBase.match(world, pos) != null) {
					if (!world.isRemote)
						world.setBlockState(pos, mana_head.getDefaultState(), 3);
					stack.shrink(1);
				}
				return stack;
			}
		});
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
	                                        float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	private static boolean foot(BlockWorldState bws) {
		IBlockState state = bws.getBlockState();
		if (state.getBlock() != mana_foot)
			return false;
		EnumFacing facing = state.getValue(BlockManaFoot.FACING).getOpposite();
		IBlockState other = bws.world.getBlockState(bws.getPos().offset(facing, 2));
		return other.getBlock() == mana_foot && other.getValue(BlockManaFoot.FACING) == facing;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (worldIn.isRemote)
			return;
		BlockPattern.PatternHelper helper = pattern.match(worldIn, pos);
		if (helper != null) {
			for (int i = 0; i < pattern.getPalmLength(); ++i)
				for (int j = 0; j < pattern.getThumbLength(); ++j)
					for (int k = 0; k < pattern.getFingerLength(); ++k)
						worldIn.setBlockState(helper.translateOffset(i, j, k).getPos(), AIR.getDefaultState());
			BlockPos pos0 = helper.translateOffset(1, 2, 0).getPos();
			EntityManaShooter shooter = new EntityManaShooter(worldIn);
			shooter.setLocationAndAngles(pos0.getX() + 0.5, pos0.getY() + 0.05, pos0.getZ() + 0.5, 0, 0);
			worldIn.spawnEntity(shooter);
			ManaCraft.giveAdvancement(placer, "tall_guy");
		}
	}

	@Override
	@Deprecated
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	@Deprecated
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
}
