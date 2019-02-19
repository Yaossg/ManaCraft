package mana_craft.block;

import mana_craft.entity.EntityManaBall;
import mana_craft.item.ManaCraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sausage_core.api.util.common.SausageUtils;

import java.util.Random;

public class BlockManaBody extends Block {
    protected final int value;
    BlockManaBody() {
        this(1.2f, 9);
    }

    BlockManaBody(float hardness, int value) {
        super(Material.SAND, MapColor.PURPLE);
        setSoundType(SoundType.SAND);
        setLightLevel(SausageUtils.lightLevelOf(5));
        setHardness(hardness);
        setTickRandomly(true);
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

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        for (EnumFacing facing : EnumFacing.values()) {
            BlockPos offset = pos.offset(facing);
            int times = random.nextInt(3);
            for (int i = 0; i < times; i++) {
                EntityManaBall ball = EntityManaBall.get(worldIn,
                        offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, true)
                        .setDamage(4);
                shoot(ball, random.nextFloat() * 360 - 180, random.nextFloat() * 360 - 180);
                worldIn.spawnEntity(ball);
            }
        }
    }

    static void shoot(EntityManaBall ball, float rotationPitchIn, float rotationYawIn) {
        float x = -MathHelper.sin(rotationYawIn * 0.017453292f) * MathHelper.cos(rotationPitchIn * 0.017453292f);
        float y = -MathHelper.sin(rotationPitchIn * 0.017453292f);
        float z = MathHelper.cos(rotationYawIn * 0.017453292f) * MathHelper.cos(rotationPitchIn * 0.017453292f);
        ball.shoot(x, y, z, EntityManaBall.lowVelocity, EntityManaBall.defaultInaccuracy);
    }
}
