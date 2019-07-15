package mana_craft.block;

import com.google.common.collect.ImmutableMap;
import mana_craft.ManaCraft;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.tile.TileManaProducer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.util.Constants;
import sausage_core.api.core.tile.ITileDropItems;
import sausage_core.api.util.common.DimensionalBlockPos;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.nbt.NBTs;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class BlockManaProducer extends BlockContainer {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool WORKING = PropertyBool.create("working");

	public static class Cache extends WorldSavedData {
		public final List<DimensionalBlockPos> list = NonNullList.create();

		public Cache(String name) {
			super(name);
		}

		public void add(DimensionalBlockPos pos) {
			list.add(pos);
			markDirty();
		}

		public void remove(DimensionalBlockPos pos) {
			list.remove(pos);
			markDirty();
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			list.clear();
			for(NBTBase each : nbt.getTagList("producers", Constants.NBT.TAG_COMPOUND))
				list.add(DimensionalBlockPos.fromNBT((NBTTagCompound) each));
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			compound.setTag("producers", NBTs.of(list.stream().map(DimensionalBlockPos::toNBT).collect(Collectors.toList())));
			return compound;
		}

		public static Cache get(World world) {
			WorldSavedData data = world.getPerWorldStorage().getOrLoadData(Cache.class, "ManaProducers");
			if(data == null) {
				data = new Cache("ManaProducers");
				world.getPerWorldStorage().setData("ManaProducers", data);
			}
			return (Cache) data;
		}
	}

	public BlockManaProducer() {
		super(Material.IRON, MapColor.PURPLE);
		setHardness(11);
		setLightLevel(SausageUtils.lightLevelOf(11));
		setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, Boolean.FALSE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, WORKING) {
			@Override
			protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
				return new StateImplementation(block, properties) {
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
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(WORKING, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex() | (state.getValue(WORKING) ? 4 : 0);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
	                                        float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileManaProducer();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
			playerIn.openGui(ManaCraft.instance, ManaCraftGUIs.ManaProducer.ID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		Cache.get(worldIn).add(new DimensionalBlockPos(worldIn, pos));
		if(TileManaProducer.checkMachine(worldIn, pos))
			ManaCraft.giveAdvancement(placer, "energize");
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return TileManaProducer.checkFrame(worldIn, pos);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		Cache.get(worldIn).remove(new DimensionalBlockPos(worldIn, pos));
		ITileDropItems.dropAll(worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(rand.nextFloat() < 0.05f && stateIn.getValue(WORKING))
			worldIn.playSound(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 1, 1, true);
	}
}