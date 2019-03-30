package mana_craft.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nullable;
import java.util.Optional;

public class BlockManaObsidian extends Block {
	BlockManaObsidian() {
		super(Material.ROCK);
		setHardness(40);
		setResistance(1600);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel());
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

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return false;
	}
}
