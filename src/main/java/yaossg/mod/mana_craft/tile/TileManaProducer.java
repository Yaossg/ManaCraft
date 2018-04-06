package yaossg.mod.mana_craft.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.block.BlockManaProducer;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.copyOf;
import static yaossg.mod.mana_craft.block.BlockManaProducer.FACING;

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
        return world.getBlockState(pos.down()).getBlock().equals(ManaCraftBlocks.blockManaIngot)
                && world.getBlockState(pos.up()).getBlock().equals(ManaCraftBlocks.blockManaGlass)
                && world.getBlockState(pos.offset(facing)).getBlock().equals(Blocks.AIR)
                && world.getBlockState(pos.offset(facing.rotateY())).getBlock().equals(ManaCraftBlocks.blockManaGlass)
                && world.getBlockState(pos.offset(facing.rotateY().rotateY())).getBlock().equals(ManaCraftBlocks.blockManaGlass)
                && world.getBlockState(pos.offset(facing.rotateY().rotateY().rotateY())).getBlock().equals(ManaCraftBlocks.blockManaGlass)

                && world.getBlockState(pos.up(2)).getBlock().equals(Blocks.AIR)
                && world.getBlockState(pos.offset(facing, 2)).getBlock().equals(Blocks.AIR)
                && world.getBlockState(pos.offset(facing.rotateY(), 2)).getBlock().equals(Blocks.AIR)
                && world.getBlockState(pos.offset(facing.rotateY().rotateY(), 2)).getBlock().equals(Blocks.AIR)
                && world.getBlockState(pos.offset(facing.rotateY().rotateY().rotateY(), 2)).getBlock().equals(Blocks.AIR)

                && world.getBlockState(pos.add(1, -1, 0)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(0, -1, 1)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(-1, -1, 0)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(0, -1, -1)).getBlock().equals(ManaCraftBlocks.blockMana)

                && world.getBlockState(pos.add(1, 0, 1)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(-1, 0, 1)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(-1, 0, -1)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(1, 0, -1)).getBlock().equals(ManaCraftBlocks.blockMana)

                && world.getBlockState(pos.add(1, 1, 0)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(0, 1, 1)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(-1, 1, 0)).getBlock().equals(ManaCraftBlocks.blockMana)
                && world.getBlockState(pos.add(0, 1, -1)).getBlock().equals(ManaCraftBlocks.blockMana)

                && world.getBlockState(pos.add(1, -1, 1)).getBlock().equals(ManaCraftBlocks.blockManaIngot)
                && world.getBlockState(pos.add(-1, -1, 1)).getBlock().equals(ManaCraftBlocks.blockManaIngot)
                && world.getBlockState(pos.add(-1, -1, -1)).getBlock().equals(ManaCraftBlocks.blockManaIngot)
                && world.getBlockState(pos.add(1, -1, -1)).getBlock().equals(ManaCraftBlocks.blockManaIngot);
    }
    public interface Recipe {
        ItemStack[] getInput();
        ItemStack getOutput();
        int getWorkTime();
        static Recipe of(ItemStack ouput, int time, ItemStack... input) {
            Arrays.sort(input, Comparator.comparing(stack -> stack.getItem().getUnlocalizedName()));
            return new Recipe() {
                @Override
                public ItemStack[] getInput() {
                    return input;
                }

                @Override
                public ItemStack getOutput() {
                    return ouput;
                }

                @Override
                public int getWorkTime() {
                    return time;
                }
            };
        }
    }
    private static final List<Recipe> recipes = Arrays.asList(
            Recipe.of(new ItemStack(ManaCraftItems.itemMana, 7), 7500,
                    new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.SUGAR)
            ),
            Recipe.of(new ItemStack(ManaCraftItems.itemManaNugget), 5000,
                    new ItemStack(Items.GOLD_NUGGET), new ItemStack(ManaCraftItems.itemMana, 6)
            ),
            Recipe.of(new ItemStack(ManaCraftItems.itemManaNugget, 3), 14000,
                    new ItemStack(Items.GOLD_NUGGET, 3), new ItemStack(ManaCraftBlocks.blockMana, 2)
            ),
            Recipe.of(new ItemStack(ManaCraftItems.itemManaIngot), 47000,
                    new ItemStack(Items.GOLD_INGOT), new ItemStack(ManaCraftItems.itemMana, 5), new ItemStack(ManaCraftBlocks.blockMana, 5)
            ),
            Recipe.of(new ItemStack(ManaCraftItems.itemManaIngot), 47000,
                    new ItemStack(Items.GOLD_INGOT), new ItemStack(ManaCraftItems.itemMana, 50)
            ),
            Recipe.of(new ItemStack(ManaCraftBlocks.blockManaIngot), 430000,
                    new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(ManaCraftBlocks.blockMana, 48)
            ),
            Recipe.of(new ItemStack(ManaCraftBlocks.blockManaGlass, 4), 8848,
                    new ItemStack(Blocks.GLASS, 4), new ItemStack(ManaCraftItems.itemMana, 12), new ItemStack(ManaCraftItems.itemManaNugget, 12))
    );

    ItemStackHandler sorted() {
        ItemStackHandler handler = new ItemStackHandler(4);
        for (int i = 0; i < input.getSlots(); ++i)
            ItemHandlerHelper.insertItemStacked(handler, input.getStackInSlot(i).copy(), false);
        int empty = 0;
        for (int i = 0; i < handler.getSlots(); ++i)
            if(handler.getStackInSlot(i).isEmpty()) ++empty;
        ItemStack[] items = new ItemStack[handler.getSlots() - empty];
        for (int i = 0; i < items.length; ++i)
            items[i] = handler.getStackInSlot(i);
        Arrays.sort(items, Comparator.comparing(stack -> stack.getItem().getUnlocalizedName()));
        handler = new ItemStackHandler(items.length);
        for (int i = 0; i < handler.getSlots(); ++i)
            handler.setStackInSlot(i, items[i]);
        return handler;
    }

    Recipe detect() {
        ItemStackHandler handler = sorted();
        for(Recipe recipe : recipes) {
            ItemStack[] matches = recipe.getInput();
            boolean good = handler.getSlots() >= matches.length;
            for (int i = 0; i < matches.length && good
                    && (good = ItemStack.areItemStacksEqual(handler.extractItem(i, matches[i].getCount(), true), matches[i])); ++i);
            if(good)
                return recipe;
        }
        return null;
    }

    @Override
    public void update() {
        if (!world.isRemote)
        {
            IBlockState state = this.getWorld().getBlockState(pos);
            if(work_time > total_work_time)
                work_time = total_work_time;
            if(checkCharged()) {
                Recipe current = detect();
                if (current != null && output.insertItem(0, current.getOutput(), true).isEmpty()) {
                    this.getWorld().setBlockState(pos, state.withProperty(BlockManaProducer.WORKING, Boolean.TRUE));
                    if(total_work_time != current.getWorkTime()) {
                        total_work_time = current.getWorkTime();
                        work_time = Integer.min(work_time, total_work_time - 1);
                    }
                    int step = 7
                            - (world.getWorldInfo().isRaining() ? 2 : 0)
                            + (world.getWorldInfo().isThundering() ? 5 : 0)
                            + (int)((world.getBiome(pos).getRainfall() * 0.5f + 0.2f) * 8);

                    if ((work_time += step) >= total_work_time) {
                        this.work_time -= current.getWorkTime();
                        ItemStackHandler temp = sorted();
                        for (int i = 0; i < temp.getSlots(); ++i) {
                            temp.extractItem(i, current.getInput()[i].getCount(),false);
                        }
                        for (int i = 0; i < input.getSlots(); ++i) {
                            if(i < temp.getSlots())
                                input.setStackInSlot(i, temp.getStackInSlot(i));
                            else
                                input.setStackInSlot(i, ItemStack.EMPTY);
                        }
                        output.insertItem(0, current.getOutput().copy(), false);
                        this.markDirty();
                    }
                } else {
                    if (work_time > 0)
                        work_time -= 16;
                    if(work_time < 0)
                        work_time = 0;
                    world.setBlockState(pos, state.withProperty(BlockManaProducer.WORKING, Boolean.FALSE));
                }
            } else {
                work_time = 0;
                world.setBlockState(pos, state.withProperty(BlockManaProducer.WORKING, Boolean.FALSE));
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
}
