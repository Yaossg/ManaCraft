package com.github.yaossg.mana_craft.tile;

import com.github.yaossg.mana_craft.api.registry.IMBFuel;
import com.github.yaossg.mana_craft.block.BlockManaBooster;
import com.github.yaossg.mana_craft.block.BlockManaProducer;
import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.fuels;

public class TileManaBooster extends TileEntity implements ITickable {
    public int burn_time = 0;
    public int burn_level = 0;
    public int total_burn_time = 0;
    public ItemStackHandler fuel = new ItemStackHandler();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        fuel.deserializeNBT(compound.getCompoundTag("fuel"));
        burn_time = compound.getInteger("burn_time");
        burn_level = compound.getInteger("burn_level");
        total_burn_time = compound.getInteger("total_burn_time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("fuel", this.fuel.serializeNBT());
        compound.setInteger("burn_time", this.burn_time);
        compound.setInteger("burn_level", this.burn_level);
        compound.setInteger("total_burn_time", this.total_burn_time);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability) return (T) fuel;
        return super.getCapability(capability, facing);
    }

    boolean flip = false;
    void work() {
        if(flip = !flip) {
            --burn_time;
            for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL.facings()) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if(tileEntity instanceof TileEntityFurnace) {
                    TileEntityFurnace furnace = (TileEntityFurnace) tileEntity;
                    furnace.setField(2, Math.min(furnace.getField(2) + burn_level / 2, furnace.getCookTime(ItemStack.EMPTY) - 1));
                    BlockFurnace.setState(furnace.isBurning(), world, furnace.getPos());
                    burn_time -= 4;
                }
                if(tileEntity instanceof TileEntityBrewingStand) {
                    TileEntityBrewingStand brew = (TileEntityBrewingStand) tileEntity;
                    brew.setField(0, Math.max(brew.getField(0) - burn_level / 2, 1));
                    burn_time -= 4;
                }
            }

            BlockManaProducer.SavedData.get(world).list.stream().filter(pos0 -> pos.distanceSq(pos0) <= ManaCraftConfig.boostRadius * ManaCraftConfig.boostRadius && pos0.getY() > pos.getY() && world.getBlockState(pos0).getValue(BlockManaProducer.WORKING)).map(pos0 -> (TileManaProducer) world.getTileEntity(pos0)).limit(ManaCraftConfig.boostLimit).filter(Objects::nonNull).forEach(tile -> {
                tile.work_time += burn_level;
                burn_time -= 3;
            });
        }

    }
    @Override
    public void update() {
        if(world.isRemote)
            return;
        IBlockState state = world.getBlockState(pos);
        if(world.canSeeSky(pos.up())) {
            if(burn_time > 0) {
                work();
            } else {
                ItemStack stack = fuel.getStackInSlot(0);
                Optional<IMBFuel> opt = fuels.stream().filter(fuel0 -> fuel0.test(stack)).findAny();
                if(opt.isPresent()) {
                    world.setBlockState(pos, state.withProperty(BlockManaBooster.BURNING, Boolean.TRUE));
                    total_burn_time = burn_time = opt.get().getBurnTime();
                    burn_level = opt.get().getBurnLevel();
                    fuel.extractItem(0, 1, false);
                    markDirty();
                    return;
                }
                world.setBlockState(pos, state.withProperty(BlockManaBooster.BURNING, Boolean.FALSE));
            }
            return;
        }
        world.setBlockState(pos, state.withProperty(BlockManaBooster.BURNING, Boolean.FALSE));
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
