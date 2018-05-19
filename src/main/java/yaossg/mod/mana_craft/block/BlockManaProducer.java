package yaossg.mod.mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.util.NBTs;
import yaossg.mod.mana_craft.util.Util;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.inventory.ManaCraftGUIs;
import yaossg.mod.mana_craft.tile.TileManaProducer;

import java.util.List;

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
            this.markDirty();
        }
        public void remove(BlockPos pos) {
            list.remove(pos);
            this.markDirty();
        }
        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            list.clear();
            NBTTagList list0 = nbt.getTagList("producers",10);
            for (NBTBase each : list0) {
                NBTTagCompound compound = (NBTTagCompound) each;
                list.add(new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z")));
            }
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound compound) {
            NBTTagList list0 = new NBTTagList();
            for(BlockPos pos : list) {
                list0.appendTag(NBTs.of(
                    "x", NBTs.of(pos.getX()),
                    "y", NBTs.of(pos.getY()),
                    "z", NBTs.of(pos.getZ())
                ));
            }
            compound.setTag("producers", list0);
            return compound;
        }

        public static SavedData get(World world) {
            WorldSavedData data = world.getPerWorldStorage().getOrLoadData(SavedData.class, "Producers");
            if (data == null)
            {
                data = new SavedData("Producers");
                world.getPerWorldStorage().setData("Producers", data);
            }
            return (SavedData) data;
        }
    }

    BlockManaProducer() {
        super(Material.IRON);
        this.setHardness(3);
        this.setLightLevel(Util.lightAt(11));
        this.setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(WORKING, Boolean.FALSE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, WORKING);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta & 3))
                .withProperty(WORKING, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(WORKING) ? 4 : 0);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, List<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
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
    public boolean onBlockActivated(World worldIn, BlockPos pos,
                                    IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(ManaCraft.instance, ManaCraftGUIs.ManaProducer.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        SavedData.get(worldIn).add(pos);
        if(((TileManaProducer)worldIn.getTileEntity(pos)).checkCharged()) {
            ManaCraft.giveAdvancement(placer, "encharge");
        }

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileManaProducer tile = (TileManaProducer) worldIn.getTileEntity(pos);
        SavedData.get(worldIn).remove(pos);
        NonNullList<ItemStack> items = NonNullList.create();
        for (int i = 0; i < tile.input.getSlots(); ++i)
            items.add(tile.input.getStackInSlot(i));
        items.add(tile.output.getStackInSlot(0));
        for (ItemStack item : items)
            Block.spawnAsEntity(worldIn, pos, item);
        super.breakBlock(worldIn, pos, state);
    }

}