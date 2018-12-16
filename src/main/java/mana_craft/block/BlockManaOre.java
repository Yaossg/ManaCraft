package mana_craft.block;

import mana_craft.item.ManaCraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import sausage_core.api.util.common.SausageUtils;

import java.util.Random;

public class BlockManaOre extends Block {
    BlockManaOre() {
        super(Material.ROCK);
        setHardness(3);
        setHarvestLevel("pickaxe", Item.ToolMaterial.STONE.getHarvestLevel());
        setLightLevel(SausageUtils.lightLevelOf(7));
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return 5 + fortune + random.nextInt(4 + fortune * 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ManaCraftItems.mana;
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        return 1 + fortune;
    }
}
