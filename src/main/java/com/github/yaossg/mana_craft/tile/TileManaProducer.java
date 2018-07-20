package com.github.yaossg.mana_craft.tile;

import com.github.yaossg.mana_craft.api.IngredientStack;
import com.github.yaossg.mana_craft.api.registry.IMPRecipe;
import com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries;
import com.github.yaossg.sausage_core.api.util.inventory.IDefaultInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.instance;
import static com.github.yaossg.mana_craft.block.BlockManaProducer.FACING;
import static com.github.yaossg.mana_craft.block.BlockManaProducer.WORKING;
import static com.github.yaossg.mana_craft.block.ManaCraftBlocks.*;

public class TileManaProducer extends TileEntity implements ITickable, IDefaultInventory {
    public int work_time;
    public int total_work_time;
    public ItemStackHandler input = new ItemStackHandler(4);
    public ItemStackHandler output = new ItemStackHandler();

    @Override
    public ItemStackHandler[] getItemStackHandlers() {
        return new ItemStackHandler[] {input, output};
    }

    @Override
    public Optional<TileEntity> getTileEntity() {
        return Optional.of(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        input.deserializeNBT(compound.getCompoundTag("input"));
        output.deserializeNBT(compound.getCompoundTag("output"));
        work_time = compound.getInteger("work_time");
        total_work_time = compound.getInteger("total_work_time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("input", this.input.serializeNBT());
        compound.setTag("output", this.output.serializeNBT());
        compound.setInteger("work_time", this.work_time);
        compound.setInteger("total_work_time", this.total_work_time);
        return super.writeToNBT(compound);
    }

    public static boolean checkCharged(World world, BlockPos pos, EnumFacing facing) {
        return world.getBlockState(pos.down()).getBlock() == manaBlock
                && world.getBlockState(pos.up()).getBlock() == manaGlass
                && world.getBlockState(pos.up(2)).getBlock() == manaLantern

                && world.isAirBlock(pos.offset(facing))
                && world.getBlockState(pos.offset(facing.rotateY())).getBlock() == manaGlass
                && world.getBlockState(pos.offset(facing.getOpposite())).getBlock() == manaGlass
                && world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() == manaGlass

                && world.isAirBlock(pos.offset(facing, 2))
                && world.isAirBlock(pos.offset(facing.rotateY(), 2))
                && world.isAirBlock(pos.offset(facing.rotateY().rotateY(), 2))
                && world.isAirBlock(pos.offset(facing.rotateY().rotateY().rotateY(), 2))

                && world.getBlockState(pos.add(1, -1, 0)).getBlock() == manaBlock
                && world.getBlockState(pos.add(0, -1, 1)).getBlock() == manaBlock
                && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == manaBlock
                && world.getBlockState(pos.add(0, -1, -1)).getBlock() == manaBlock

                && world.getBlockState(pos.add(1, 0, 1)).getBlock() == manaBlock
                && world.getBlockState(pos.add(-1, 0, 1)).getBlock() == manaBlock
                && world.getBlockState(pos.add(-1, 0, -1)).getBlock() == manaBlock
                && world.getBlockState(pos.add(1, 0, -1)).getBlock() == manaBlock

                && world.getBlockState(pos.add(1, 1, 0)).getBlock() == manaBlock
                && world.getBlockState(pos.add(0, 1, 1)).getBlock() == manaBlock
                && world.getBlockState(pos.add(-1, 1, 0)).getBlock() == manaBlock
                && world.getBlockState(pos.add(0, 1, -1)).getBlock() == manaBlock

                && world.getBlockState(pos.add(1, -1, 1)).getBlock() == manaIngotBlock
                && world.getBlockState(pos.add(-1, -1, 1)).getBlock() == manaIngotBlock
                && world.getBlockState(pos.add(-1, -1, -1)).getBlock() == manaIngotBlock
                && world.getBlockState(pos.add(1, -1, -1)).getBlock() == manaIngotBlock

                && world.getLightFor(EnumSkyBlock.SKY, pos.up(3)) > 13;
    }

    public boolean isSorted = false;

    ItemStackHandler getSorted() {
        ItemStackHandler handler = new ItemStackHandler(4);
        for (int i = 0; i < input.getSlots(); ++i)
            ItemHandlerHelper.insertItemStacked(handler, input.getStackInSlot(i).copy(), false);
        int empty = 0;
        for (int i = 0; i < handler.getSlots(); ++i)
            if(handler.getStackInSlot(i).isEmpty()) ++empty;
        ItemStack[] items = new ItemStack[handler.getSlots() - empty];
        for (int i = 0; i < items.length; ++i)
            items[i] = handler.getStackInSlot(i);
        Arrays.sort(items, ManaCraftRegistries.IComparators.itemStack);
        handler = new ItemStackHandler(items.length);
        for (int i = 0; i < handler.getSlots(); ++i)
            handler.setStackInSlot(i, items[i]);
        return handler;
    }

    @Nullable
    IMPRecipe detect() {
        ItemStackHandler handler = getSorted();
        for (IMPRecipe recipe : instance().getRecipes()) {
            IngredientStack[] matches = recipe.input();
            boolean found = handler.getSlots() >= matches.length;
            for (int i = 0;
                 i < matches.length && found && (found =
                         matches[i].test(handler.extractItem(i, matches[i].getCount(), true)));
                 ++i);
            if(found)
                return recipe;
        }
        return null;
    }

    void copyToInput(ItemStackHandler handler) {
        for (int i = 0; i < input.getSlots(); ++i)
            input.setStackInSlot(i, i < handler.getSlots() ? handler.getStackInSlot(i) : ItemStack.EMPTY);
    }
    void work(IMPRecipe current) {
        if((++work_time) >= total_work_time) {
            work_time -= current.work_time();
            ItemStackHandler temp = getSorted();
            for (int i = 0; i < temp.getSlots() && i < current.input().length; ++i)
                temp.extractItem(i, current.input()[i].getCount(), false);
            copyToInput(temp);
            output.insertItem(0, current.output().copy(), false);
            isSorted = false;
            markDirty();
        }
    }
    @Override
    public void update() {
        IBlockState state = world.getBlockState(pos);
        if(!checkCharged(world, pos, state.getValue(FACING))) {
            world.destroyBlock(pos, true);
            return;
        }
        if(world.isRemote)
            return;
        if(work_time > total_work_time)
            work_time = total_work_time;
        IMPRecipe current = detect();
        if(current != null && output.insertItem(0, current.output(), true).isEmpty()) {
            world.setBlockState(pos, state.withProperty(WORKING, Boolean.TRUE));
            if(total_work_time != current.work_time()) {
                total_work_time = current.work_time();
                work_time = Integer.min(work_time, total_work_time - 1);
            }
            if(!isSorted) {
                copyToInput(getSorted());
                isSorted = true;
            }
            work(current);
        } else {
            if(work_time > 0) work_time -= 3;
            if(work_time < 0) work_time = 0;
            world.setBlockState(pos, state.withProperty(WORKING, Boolean.FALSE));
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
