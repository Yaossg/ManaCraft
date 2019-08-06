package mana_craft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOrichalcum extends Block {
	public BlockOrichalcum() {
		super(Material.ROCK, MapColor.PURPLE);
		setHardness(6);
		setLightLevel(10 / 15F);
		setHarvestLevel("pickaxe", Item.ToolMaterial.IRON.getHarvestLevel());
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return true;
	}
}
