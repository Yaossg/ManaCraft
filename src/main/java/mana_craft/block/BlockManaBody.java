package mana_craft.block;

import mana_craft.item.ManaCraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sausage_core.api.util.common.SausageUtils;

import java.util.Random;

public class BlockManaBody extends Block {
    int value;
    BlockManaBody() {
        this(1.2f, 9);
    }

    BlockManaBody(float hardness, int value) {
        super(Material.SAND, MapColor.PURPLE);
        setSoundType(SoundType.SAND);
        setLightLevel(SausageUtils.lightLevelOf(5));
        setHardness(hardness);
        this.value = value;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ManaCraftItems.mana_dust;
    }

    @Override
    public int quantityDropped(Random random) {
        int base = value / 2;
        return base + random.nextInt(value - base);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        worldIn.destroyBlock(pos, true);
    }
}
