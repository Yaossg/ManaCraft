package mana_craft.tile;

import mana_craft.api.registry.IMPRecipe;
import mana_craft.block.BlockManaFoot;
import sausage_core.api.util.common.IItemComparators;
import sausage_core.api.util.common.IngredientStack;
import sausage_core.api.util.inventory.ITileDropItems;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Arrays;

import static mana_craft.api.registry.ManaCraftRegistries.instance;
import static mana_craft.block.BlockManaProducer.WORKING;
import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.config.ManaCraftConfig.delay;
import static mana_craft.config.ManaCraftConfig.destroy;
import static net.minecraft.block.state.BlockWorldState.hasState;
import static net.minecraft.block.state.pattern.BlockStateMatcher.forBlock;

public class TileManaProducer extends TileEntity implements ITickable, ITileDropItems {
    public int work_time;
    public int total_work_time;
    public ItemStackHandler input = new ItemStackHandler(4);
    public ItemStackHandler output = new ItemStackHandler();

    @Override
    public ItemStackHandler[] getItemStackHandlers() {
        return new ItemStackHandler[] {input, output};
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

    private static BlockPattern pattern;
    public static void init() {
        pattern = FactoryBlockPattern.start()
                .aisle("     ", "     ", "  _  ", "     ")
                .aisle("  _  ", " _#_ ", " # # ", " *#* ")
                .aisle(" _^_ ", "_#!#_", "_ O _", " ### ")
                .aisle("  _  ", " _#_ ", " # # ", " *#* ")
                .aisle("     ", "     ", "  _  ", "     ")
                .where('_', hasState(forBlock(Blocks.AIR)))
                .where('^', hasState(forBlock(manaLantern)))
                .where('#', hasState(forBlock(manaBlock)))
                .where('!', hasState(forBlock(manaGlass)))
                .where('*', hasState(forBlock(manaIngotBlock)))
                .where('O', TileManaProducer::core)
                .build();
    }
    public static boolean core(BlockWorldState bws) {
        IBlockState state = bws.getBlockState();
        if(state.getBlock() != manaProducer)
            return false;
        World world = bws.world;
        EnumFacing facing = state.getValue(BlockManaFoot.FACING);
        BlockPos pos = bws.getPos();
        return world.isAirBlock(pos.offset(facing))
                && world.getBlockState(pos.offset(facing.rotateY())).getBlock() == manaGlass
                && world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() == manaGlass
                && world.getBlockState(pos.offset(facing.getOpposite())).getBlock() == manaGlass;
    }
    public static boolean checkCharged(World world, BlockPos pos) {
        BlockPattern.PatternHelper helper = pattern.match(world, pos);
        if(helper != null)
            return helper.translateOffset(2, 2, 2).getPos().equals(pos);
        return false;
    }

    public boolean isCharged = false;
    private int checkDelay = delay;
    boolean check() {
        if(--checkDelay < 0) isCharged = false;
        if(!isCharged) {
            if(!(isCharged = checkCharged(world, pos))) {
                if(destroy)
                    world.destroyBlock(pos, true);
                return false;
            }
            checkDelay = 8;
        }
        return true;
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
        Arrays.sort(items, IItemComparators.itemStack);
        handler = new ItemStackHandler(items.length);
        for (int i = 0; i < handler.getSlots(); ++i)
            handler.setStackInSlot(i, items[i]);
        return handler;
    }

    @Nullable
    IMPRecipe detect() {
        ItemStackHandler handler = getSorted();
        for (IMPRecipe recipe : instance().recipesView()) {
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
            isCharged = isSorted = false;
            markDirty();
        }
    }

    @Override
    public void update() {
        IBlockState state = world.getBlockState(pos);
        if(world.isRemote || !check()) return;
        work_time = Math.min(work_time, total_work_time);
        IMPRecipe current = detect();
        if(current != null && output.insertItem(0, current.output(), true).isEmpty()) {
            world.setBlockState(pos, state.withProperty(WORKING, Boolean.TRUE));
            if(total_work_time != current.work_time()) {
                total_work_time = current.work_time();
                work_time = Math.min(work_time, total_work_time - 1);
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
