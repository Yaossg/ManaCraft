package mana_craft.item;

import com.google.common.collect.Streams;
import mana_craft.config.ManaCraftConfig;
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

import static mana_craft.block.ManaCraftBlocks.*;

public class ItemMPGenerator extends Item {
    public static void buildFrame(World worldIn, BlockPos root, EnumFacing facing) {
        worldIn.setBlockState(root, mana_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, 0), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(0, 0, 1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, 0), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(0, 0, -1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, 1), orichalcum_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, 1), orichalcum_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 0, -1), orichalcum_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 0, -1), orichalcum_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 1, 1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 1, 1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 1, -1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 1, -1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(1, 2, 0), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, 1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(-1, 2, 0), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, -1), mana_block.getDefaultState());
        worldIn.setBlockState(root.add(0, 3, 0), mana_lantern.getDefaultState());
        worldIn.setBlockState(root.add(0, 2, 0), mana_glass.getDefaultState());
        worldIn.setBlockState(root.up().offset(facing), mana_glass.getDefaultState());
        worldIn.setBlockState(root.up().offset(facing.rotateY()), mana_glass.getDefaultState());
        worldIn.setBlockState(root.up().offset(facing.rotateYCCW()), mana_glass.getDefaultState());
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        BlockPos root = pos.offset(facing);

        if(!ManaCraftConfig.replace &&
                !Streams.stream(BlockPos.getAllInBox(root.add(2, 0, 2), pos.add(-2, 3, -2))).allMatch(worldIn::isAirBlock)) {
            if(player.getServer() != null) {
                player.getServer().getPlayerList().sendMessage(new TextComponentTranslation("message.mana_craft.failed"));
            }
            return EnumActionResult.PASS;
        }
        if(ManaCraftConfig.replace) {
            BlockPos.getAllInBox(root.add(2, 0, 2), root.add(-2, 4, -2)).forEach(worldIn::setBlockToAir);
        }
        if(!player.isCreative())
            player.getHeldItem(hand).shrink(1);
        buildFrame(worldIn, root, player.getHorizontalFacing());
        ForgeHooks.onPlaceItemIntoWorld(new ItemStack(mana_producer), player, worldIn, root, facing, hitX, hitY, hitZ, hand);
        return EnumActionResult.SUCCESS;
    }
}
