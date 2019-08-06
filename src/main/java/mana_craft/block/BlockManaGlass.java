package mana_craft.block;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockManaGlass extends BlockGlass {
	public BlockManaGlass() {
		super(Material.GLASS, false);
		setHardness(0.5f);
		setResistance(1500);
		setSoundType(SoundType.GLASS);
		setHardness(1);
		setLightLevel(9 / 15F);
		setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
