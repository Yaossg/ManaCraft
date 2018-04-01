package yaossg.mod.mana_craft.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yaossg.mod.mana_craft.block.BlockManaProducer;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static yaossg.mod.mana_craft.block.BlockManaBooster.BURNING;
import static yaossg.mod.mana_craft.block.BlockManaProducer.WORKING;

public class TileManaBooster extends TileEntity implements ITickable {
    public int burn_time = 0;
    public int burn_level = 0;
    public int total_burn_time = 0;
    public ItemStackHandler fuel = new ItemStackHandler();
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.fuel.deserializeNBT(compound.getCompoundTag("fuel"));
        this.burn_time = compound.getInteger("burn_time");
        this.burn_level = compound.getInteger("burn_level");
        this.total_burn_time = compound.getInteger("total_burn_time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setTag("fuel", this.fuel.serializeNBT());
        compound.setInteger("burn_time", this.burn_time);
        compound.setInteger("burn_level", this.burn_level);
        compound.setInteger("total_burn_time", this.total_burn_time);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability) || super.hasCapability(capability, facing);
    }
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            @SuppressWarnings("unchecked")
            T result = (T) (fuel);
            return result;
        }
        return super.getCapability(capability, facing);
    }

    interface Burning {
        Item getItem();
        int getBurnLevel();
        int getBurnTime();
        static Burning of(Item item, int level, int time) {
            return new Burning() {
                @Override
                public Item getItem() {
                    return item;
                }

                @Override
                public int getBurnLevel() {
                    return level;
                }

                @Override
                public int getBurnTime() {
                    return time;
                }
            };
        }
    }

    private static final List<Burning> burnings = Arrays.asList(
            Burning.of(ManaCraftItems.itemMana, 20,500),
            Burning.of(Item.getItemFromBlock(ManaCraftBlocks.blockMana), 100, 1000),
            Burning.of(ManaCraftItems.itemManaNugget, 150, 1000),
            Burning.of(ManaCraftItems.itemManaIngot, 1000, 1500),
            Burning.of(Item.getItemFromBlock(ManaCraftBlocks.blockManaIngot), 7500, 2000)
    );
    boolean flip = false;
    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = this.getWorld().getBlockState(pos);
            if(burn_time > 0) {
                if(flip = !flip)
                {
                    --burn_time;
                    BlockManaProducer.SavedData.get(world).list.stream()
                            .filter(pos0 -> pos.distanceSq(pos0) <= 9 && pos0.getY() > pos.getY() && world.getBlockState(pos0).getValue(WORKING))
                            .map(pos0 -> (TileManaProducer) world.getTileEntity(pos0))
                            .limit(3).forEach( tile -> {
                                tile.work_time += burn_level;
                                burn_time -= 2;
                            });
                }
            } else {
                Item item = fuel.getStackInSlot(0).getItem();
                int index = -1;
                for (int i = 0; i < burnings.size(); ++i) {
                    if (burnings.get(i).getItem() == item) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    this.getWorld().setBlockState(pos, state.withProperty(BURNING, Boolean.TRUE));
                    total_burn_time = burn_time = burnings.get(index).getBurnTime();
                    burn_level = burnings.get(index).getBurnLevel();
                    fuel.extractItem(0, 1, false);
                    this.markDirty();
                } else {
                    this.getWorld().setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE));
                }
            }
        }
    }
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
}
