package mana_craft.block;

import net.minecraft.block.BlockObsidian;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockManaObsidian extends BlockObsidian {
    BlockManaObsidian() {
        setHardness(40f);
        setResistance(3000f);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", Item.ToolMaterial.DIAMOND.getHarvestLevel());
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }
}
