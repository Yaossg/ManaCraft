package mana_craft.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Optional;

public class BlockManaFoot extends BlockManaBody {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final AxisAlignedBB[] AABBs = {
			new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.75, 0.5),
			new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 0.75, 1.0),
			new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 0.75, 1.0),
			new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 0.75, 1.0),
	};

	public BlockManaFoot() {
		super(0.8f, 4);
		setLightOpacity(2);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING) {
			@Override
			protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>,
					Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
				return new StateImplementation(block, properties) {

					@Override
					public boolean isFullCube() {
						return false;
					}

					@Override
					public boolean isOpaqueCube() {
						return false;
					}

					@Override
					public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos) {
						return AABBs[((EnumFacing) properties.get(FACING)).getHorizontalIndex()];
					}

					@Override
					public IBlockState withRotation(Rotation rot) {
						return withProperty(FACING, rot.rotate(getValue(FACING)));
					}

					@Override
					public IBlockState withMirror(Mirror mirrorIn) {
						return withRotation(mirrorIn.toRotation(getValue(FACING)));
					}
				};
			}
		};
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
	                                        float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, facing.getAxis().isHorizontal() ? facing : placer.getHorizontalFacing().getOpposite());
	}
}
