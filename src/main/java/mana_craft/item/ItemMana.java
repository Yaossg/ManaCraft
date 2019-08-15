package mana_craft.item;

import mana_craft.init.ManaCraftBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemMana extends Item {
	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		World world = entity.getEntityWorld();
		BlockPos pos = entity.getPosition();
		Random random = entity.world.rand;
		if (!world.isRemote && random.nextInt(4) == 0
				&& world.getBlockState(pos.down()).getBlock() == Blocks.LAVA) {
			world.setBlockToAir(pos.down());
			entity.setDead();
			Block.spawnAsEntity(world, pos, new ItemStack(random.nextInt(4) == 0 ? ManaCraftBlocks.mana_obsidian : Blocks.OBSIDIAN));
		}
		return false;
	}
}
