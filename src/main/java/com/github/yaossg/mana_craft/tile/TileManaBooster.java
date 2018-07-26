package com.github.yaossg.mana_craft.tile;

import com.github.yaossg.mana_craft.api.registry.IMBFuel;
import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.github.yaossg.sausage_core.api.util.inventory.IDefaultInventory;
import net.minecraft.block.state.IBlockState;
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
import java.util.Optional;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.instance;
import static com.github.yaossg.mana_craft.block.BlockManaBooster.BURNING;
import static com.github.yaossg.mana_craft.block.BlockManaProducer.SavedData;
import static com.github.yaossg.mana_craft.block.BlockManaProducer.WORKING;

public class TileManaBooster extends TileEntity implements ITickable, IDefaultInventory {
    public int burn_time = 0;
    public int burn_level = 0;
    public int total_burn_time = 0;
    public ItemStackHandler handler = new ItemStackHandler();

    @Override
    public ItemStackHandler[] getItemStackHandlers() {
        return new ItemStackHandler[] {handler};
    }

    @Override
    public Optional<TileEntity> getTileEntity() {
        return Optional.of(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        handler.deserializeNBT(compound.getCompoundTag("fuel"));
        burn_time = compound.getInteger("burn_time");
        burn_level = compound.getInteger("burn_level");
        total_burn_time = compound.getInteger("total_burn_time");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("fuel", handler.serializeNBT());
        compound.setInteger("burn_time", burn_time);
        compound.setInteger("burn_level", burn_level);
        compound.setInteger("total_burn_time", total_burn_time);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability
                && facing !=null && facing.getAxis().isHorizontal()
                || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(hasCapability(capability, facing)) return (T) handler;
        return super.getCapability(capability, facing);
    }
    final int states = 16;
    int state = states - 1;
    int[] times = new int[states];
    void work() {
        if(++state == states) state = 0;
        if(state % 3 == 0)
            --burn_time;
        if(state % 2 == 0)
            SavedData.get(world).list.stream()
                    .filter(pos0 -> pos.distanceSq(pos0) <= ManaCraftConfig.boostRadius * ManaCraftConfig.boostRadius
                            && pos0.getY() > pos.getY() && world.getBlockState(pos0).getValue(WORKING))
                    .map(pos0 -> (TileManaProducer) world.getTileEntity(pos0))
                    .limit(ManaCraftConfig.boostLimit)
                    .forEach(tile -> {
                        tile.work_time += burn_level;
                        burn_time -= 3;
            });
        if(state == 0) {
            int sum = 0;
            for (int i = 1; i < times.length; ++i)
                sum += times[i] =  burn_level / times.length;
            times[0] = burn_level - sum;
        }
        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL.facings()) {
            TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
            if(tileEntity instanceof TileEntityFurnace || tileEntity instanceof TileEntityBrewingStand) {
                ITickable tickable = (ITickable) tileEntity;
                --burn_time;
                for (int i = 0; i < times[state]; ++i)
                    tickable.update();
            }
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
                Optional<IMBFuel> fuel = instance().fuelsView().stream().filter(fuel0 -> fuel0.test(handler.getStackInSlot(0))).findAny();
                if(fuel.isPresent()) {
                    world.setBlockState(pos, state.withProperty(BURNING, Boolean.TRUE));
                    total_burn_time = burn_time = fuel.get().time();
                    burn_level = fuel.get().level();
                    handler.extractItem(0, 1, false);
                    markDirty();
                    return;
                }
                world.setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE));
            }
            return;
        }
        world.setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE));
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
