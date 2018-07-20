package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.google.common.collect.Streams;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import static com.github.yaossg.mana_craft.block.ManaCraftBlocks.*;

public class ItemMPGenerator extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockPos root = pos.offset(facing);

        if(!ManaCraftConfig.replace &&
                !Streams.stream(BlockPos.getAllInBox(root.add(2, 0, 2), pos.add(-2, 3, -2))).allMatch(worldIn::isAirBlock)) {
            if(player.getServer() != null) {
                player.getServer().getPlayerList().sendMessage(new TextComponentTranslation("message.mana_craft.failed"));
            }
            return EnumActionResult.FAIL;
        }
        if(ManaCraftConfig.replace) {
            BlockPos.getAllInBox(root.add(2, 0, 2), pos.add(-2, 3, -2)).forEach(worldIn::setBlockToAir);
        }
        if(!player.isCreative())
            player.getHeldItem(hand).shrink(1);
        worldIn.setBlockState(root, manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, 0), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(0, 0, 1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, 0), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(0, 0, -1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, 1), manaIngotBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, 1), manaIngotBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, -1), manaIngotBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, -1), manaIngotBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 1, 1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 1, 1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 1, -1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 1, -1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(1, 2, 0), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, 1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(-1, 2, 0), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, -1), manaBlock.getDefaultState());
        worldIn.setBlockState(root.add(0, 3, 0), manaLantern.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, 0), manaGlass.getDefaultState());
        facing = player.getHorizontalFacing();
        root = root.up();
        worldIn.setBlockState(root.offset(facing), manaGlass.getDefaultState());
        worldIn.setBlockState(root.offset(facing.rotateY()), manaGlass.getDefaultState());
        worldIn.setBlockState(root.offset(facing.rotateYCCW()), manaGlass.getDefaultState());
        ForgeHooks.onPlaceItemIntoWorld(new ItemStack(manaProducer), player, worldIn, root, facing.getOpposite(), hitX, hitY, hitZ, hand);
        return EnumActionResult.SUCCESS;
    }
}
