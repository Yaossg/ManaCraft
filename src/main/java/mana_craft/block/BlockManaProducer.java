package mana_craft.block;

import mana_craft.ManaCraft;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.tile.TileManaProducer;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.inventory.ITileDropItems;
import sausage_core.api.util.nbt.NBTs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.List;
import java.util.Random;

public class BlockManaProducer extends BlockContainer {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool WORKING = PropertyBool.create("working");

    public static class SavedData extends WorldSavedData {
        public List<BlockPos> list = NonNullList.create();

        public SavedData(String name) {
            super(name);
        }

        public void add(BlockPos pos) {
            list.add(pos);
            markDirty();
        }

        public void remove(BlockPos pos) {
            list.remove(pos);
            markDirty();
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            list.clear();
            for (NBTBase each : nbt.getTagList("producers", Constants.NBT.TAG_COMPOUND)) {
                NBTTagCompound compound = (NBTTagCompound) each;
                list.add(new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z")));
            }
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            NBTTagList list0 = new NBTTagList();
            list.forEach(pos -> list0.appendTag(NBTs.of(
                    "x", NBTs.of(pos.getX()),
                    "y", NBTs.of(pos.getY()),
                    "z", NBTs.of(pos.getZ()))));
            compound.setTag("producers", list0);
            return compound;
        }

        public static SavedData get(World world) {
            WorldSavedData data = world.getPerWorldStorage().getOrLoadData(SavedData.class, "ManaProducers");
            if(data == null) {
                data = new SavedData("ManaProducers");
                world.getPerWorldStorage().setData("ManaProducers", data);
            }
            return (SavedData) data;
        }
    }

    BlockManaProducer() {
        super(Material.IRON, MapColor.PURPLE);
        setHardness(5f);
        setLightLevel(SausageUtils.lightLevelOf(11));
        setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, Boolean.FALSE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WORKING);
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
        SavedData.get(worldIn).add(pos);
        if(TileManaProducer.checkCharged(worldIn, pos))
            ManaCraft.giveAdvancement(placer, "energize");
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        SavedData.get(worldIn).remove(pos);
        ITileDropItems.dropAll(worldIn.getTileEntity(pos));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.05f && stateIn.getValue(WORKING))
            worldIn.playSound(pos.getX() + 0.5, pos.getY() + 3, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 1, 1, true);
    }
}