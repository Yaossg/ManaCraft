package yaossg.mod.mana_craft.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import yaossg.mod.mana_craft.api.ManaCraftAPIs;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import java.util.Arrays;

import static yaossg.mod.mana_craft.API.recipes;
import static yaossg.mod.mana_craft.api.ManaCraftAPIs.Recipe;
import static yaossg.mod.mana_craft.block.BlockManaProducer.*;

public class TileManaProducer extends TileEntity implements ITickable {
    public int work_time;
    public int total_work_time;
    public ItemStackHandler input = new ItemStackHandler(4);
    public ItemStackHandler output = new ItemStackHandler();

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.input.deserializeNBT(compound.getCompoundTag("input"));
        this.output.deserializeNBT(compound.getCompoundTag("output"));
        this.work_time = compound.getInteger("work_time");
        this.total_work_time = compound.getInteger("total_work_time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("input", this.input.serializeNBT());
        compound.setTag("output", this.output.serializeNBT());
        compound.setInteger("work_time", this.work_time);
        compound.setInteger("total_work_time", this.total_work_time);
        return super.writeToNBT(compound);
    }

    public boolean checkCharged() {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);
        return world.getBlockState(pos.down()).getBlock() == ManaCraftBlocks.blockManaIngot
                && world.getBlockState(pos.up()).getBlock() == ManaCraftBlocks.blockManaGlass
                && world.isAirBlock(pos.offset(facing))
                && world.getBlockState(pos.offset(facing.rotateY())).getBlock() == ManaCraftBlocks.blockManaGlass
                && world.getBlockState(pos.offset(facing.rotateY().rotateY())).getBlock() == ManaCraftBlocks.blockManaGlass
                && world.getBlockState(pos.offset(facing.rotateY().rotateY().rotateY())).getBlock() == ManaCraftBlocks.blockManaGlass

                && world.isAirBlock(pos.offset(facing, 2))
                && world.isAirBlock(pos.offset(facing.rotateY(), 2))
                && world.isAirBlock(pos.offset(facing.rotateY().rotateY(), 2))
                && world.isAirBlock(pos.offset(facing.rotateY().rotateY().rotateY(), 2))

                && world.getBlockState(pos.add(1, -1, 0)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(0, -1, 1)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(-1, -1, 0)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(0, -1, -1)).getBlock() == ManaCraftBlocks.blockMana

                && world.getBlockState(pos.add(1, 0, 1)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(-1, 0, 1)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(-1, 0, -1)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(1, 0, -1)).getBlock() == ManaCraftBlocks.blockMana

                && world.getBlockState(pos.add(1, 1, 0)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(0, 1, 1)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(-1, 1, 0)).getBlock() == ManaCraftBlocks.blockMana
                && world.getBlockState(pos.add(0, 1, -1)).getBlock() == ManaCraftBlocks.blockMana

                && world.getBlockState(pos.add(1, -1, 1)).getBlock() == ManaCraftBlocks.blockManaIngot
                && world.getBlockState(pos.add(-1, -1, 1)).getBlock() == ManaCraftBlocks.blockManaIngot
                && world.getBlockState(pos.add(-1, -1, -1)).getBlock() == ManaCraftBlocks.blockManaIngot
                && world.getBlockState(pos.add(1, -1, -1)).getBlock() == ManaCraftBlocks.blockManaIngot

                && world.canSeeSky(pos.up(2))
                && world.canSeeSky(pos.add(1, 2, 0))
                && world.canSeeSky(pos.add(-1, 2, 0))
                && world.canSeeSky(pos.add(0, 2, 1))
                && world.canSeeSky(pos.add(0, 2, -1))
                && world.canSeeSky(pos.add(1, 2, 1))
                && world.canSeeSky(pos.add(-1, 2, 1))
                && world.canSeeSky(pos.add(1, 2, -1))
                && world.canSeeSky(pos.add(-1, 2, -1))
                && world.canSeeSky(pos.offset(facing, 2));
    }
    static {
        recipes.addAll(Arrays.asList(
                Recipe.of(new ItemStack(ManaCraftItems.itemMana, 5), 4800,
                        new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.SUGAR)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.itemManaNugget, 3), 12000,
                        new ItemStack(Items.GOLD_NUGGET, 3), new ItemStack(ManaCraftItems.itemManaBall, 2)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.itemManaIngot), 12000 * 8,
                        new ItemStack(Items.GOLD_INGOT), new ItemStack(ManaCraftItems.itemManaBall, 6)
                ),
                Recipe.of(new ItemStack(ManaCraftBlocks.blockManaIngot), 12000 * 64,
                        new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(ManaCraftItems.itemManaBall, 54)
                ),
                Recipe.of(new ItemStack(ManaCraftBlocks.blockManaGlass), 2333,
                        new ItemStack(Blocks.GLASS), new ItemStack(ManaCraftItems.itemManaBall, 3), new ItemStack(ManaCraftItems.itemManaNugget, 3)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.itemManaCoal), 5500,
                        new ItemStack(Items.COAL), new ItemStack(ManaCraftItems.itemManaBall, 8)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.itemManaDiamond), 126000,
                        new ItemStack(Items.DIAMOND), new ItemStack(ManaCraftItems.itemManaCoal, 64)
                )
        ));

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
        Arrays.sort(items, ManaCraftAPIs.Recipe.comparator);
        handler = new ItemStackHandler(items.length);
        for (int i = 0; i < handler.getSlots(); ++i)
            handler.setStackInSlot(i, items[i]);
        return handler;
    }

    ManaCraftAPIs.Recipe detect() {
        ItemStackHandler handler = getSorted();
        for(ManaCraftAPIs.Recipe recipe : recipes) {
            ItemStack[] matches = recipe.getInput();
            boolean good = handler.getSlots() >= matches.length;
            for (int i = 0; i < matches.length && good
                    && (good = ItemStack.areItemStacksEqual(handler.extractItem(i, matches[i].getCount(), true), matches[i])); ++i);
            if (good)
                return recipe;
        }
        return null;
    }

    void copyToInput(ItemStackHandler handler) {
        for (int i = 0; i < input.getSlots(); ++i)
            if(i < handler.getSlots())
                input.setStackInSlot(i, handler.getStackInSlot(i));
            else
                input.setStackInSlot(i, ItemStack.EMPTY);
    }
    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = this.getWorld().getBlockState(pos);
            if(work_time > total_work_time)
                work_time = total_work_time;
            if(checkCharged()) {
                ManaCraftAPIs.Recipe current = detect();
                if (current != null && output.insertItem(0, current.getOutput(), true).isEmpty()) {
                    this.getWorld().setBlockState(pos, state.withProperty(WORKING, Boolean.TRUE));
                    if(total_work_time != current.getWorkTime()) {
                        total_work_time = current.getWorkTime();
                        work_time = Integer.min(work_time, total_work_time - 1);
                    }
                    if(!isSorted) {
                        copyToInput(getSorted());
                        isSorted = true;
                    }
                    if ((work_time += 8) >= total_work_time) {
                        this.work_time -= current.getWorkTime();
                        ItemStackHandler temp = getSorted();
                        for (int i = 0; i < temp.getSlots() && i < current.getInput().length; ++i) {
                            temp.extractItem(i, current.getInput()[i].getCount(),false);
                        }
                        copyToInput(temp);
                        output.insertItem(0, current.getOutput().copy(), false);
                        markDirty();
                    }
                } else {
                    if (work_time > 0)
                        work_time -= 24;
                    if(work_time < 0)
                        work_time = 0;
                    world.setBlockState(pos, state.withProperty(WORKING, Boolean.FALSE));
                }
            } else {
                work_time = 0;
                world.setBlockState(pos, state.withProperty(WORKING, Boolean.FALSE));
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
