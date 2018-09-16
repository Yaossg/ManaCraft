package com.github.yaossg.mana_craft.block;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.inventory.ManaCraftGUIs;
import com.github.yaossg.mana_craft.tile.TileManaBooster;
import com.github.yaossg.sausage_core.api.util.common.SausageUtils;
import com.github.yaossg.sausage_core.api.util.inventory.ITileDropItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

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
        return new BlockStateContainer(this, BURNING) {
            @Override
            protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
                return new StateImplementation(block, properties) {
                    @Override
                    public boolean hasComparatorInputOverride() {
                        return true;
                    }

                    @Override
                    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
                        return ItemHandlerHelper.calcRedstoneFromInventory(((TileManaBooster) worldIn.getTileEntity(pos)).handler);
                    }
                };
            }
        };
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
        ITileDropItems.dropAll(worldIn.getTileEntity(pos));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextFloat() < 0.1f && stateIn.getValue(BURNING))
            worldIn.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1, false);
    }

}
