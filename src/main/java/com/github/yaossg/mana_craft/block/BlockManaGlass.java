package com.github.yaossg.mana_craft.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Optional;

class BlockManaGlass extends BlockGlass {
    BlockManaGlass() {
        super(Material.GLASS, false);
        setSoundType(SoundType.GLASS);
        setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this) {
            @Override
            protected StateImplementation createState(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties, @Nullable ImmutableMap<IUnlistedProperty<?>, Optional<?>> unlistedProperties) {
                return new StateImplementation(block, properties) {
                    @Override
                    public MapColor getMapColor(IBlockAccess p_185909_1_, BlockPos p_185909_2_) {
                        return MapColor.PURPLE;
                    }
                };
            }
        };
    }
}
