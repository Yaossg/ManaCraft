package mana_craft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sausage_core.api.util.math.BufferedRandom;

public class ItemMana extends Item {
    private static final BufferedRandom random = BufferedRandom.shared();

    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        if(!world.isRemote && random.nextInt(4) == 0
                && world.getBlockState(pos.down()).getBlock() == Blocks.LAVA) {
            world.setBlockToAir(pos.down());
            entity.setDead();
            Block.spawnAsEntity(world, pos, new ItemStack(Blocks.OBSIDIAN));
        }
        return false;
    }
}
