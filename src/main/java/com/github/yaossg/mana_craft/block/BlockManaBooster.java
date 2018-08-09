package com.github.yaossg.mana_craft.block;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.inventory.ManaCraftGUIs;
import com.github.yaossg.mana_craft.tile.TileManaBooster;
import com.github.yaossg.sausage_core.api.util.common.SausageUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockManaBooster extends BlockContainer {
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    BlockManaBooster() {
        super(Material.IRON, MapColor.PURPLE);
        setHardness(5f);
        setLightLevel(SausageUtils.lightLevelOf(11));
        setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
        setDefaultState(blockState.getBaseState().withProperty(BURNING, Boolean.FALSE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BURNING);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BURNING, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BURNING) ? 1 : 0;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileManaBooster();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote)
            playerIn.openGui(ManaCraft.instance, ManaCraftGUIs.ManaBooster.ID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) worldIn.getTileEntity(pos));
        super.breakBlock(worldIn, pos, state);
    }
}
