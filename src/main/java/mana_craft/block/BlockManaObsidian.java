package mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockManaObsidian extends Block {
	public BlockManaObsidian() {
		super(Material.ROCK, MapColor.PURPLE);
		setHardness(40);
		setLightLevel(2 / 15F);
		setResistance(1600);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel());
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		return false;
	}
}
