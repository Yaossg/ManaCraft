package mana_craft.tile;

import mana_craft.api.registry.MBFuel;
import mana_craft.api.registry.ManaBoostable;
import mana_craft.config.ManaCraftConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.item.SingleItemStackHandler;
import sausage_core.api.util.tile.IMachineLogic;
import sausage_core.api.util.tile.ITileDropItems;
import sausage_core.api.util.tile.TileBase;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

import static mana_craft.api.registry.IManaCraftRegistries.BOOSTABLES;
import static mana_craft.api.registry.IManaCraftRegistries.MB_FUELS;
import static mana_craft.block.BlockManaBooster.BURNING;
import static mana_craft.block.BlockManaProducer.SavedData;
import static mana_craft.block.BlockManaProducer.WORKING;
import static mana_craft.config.ManaCraftConfig.*;

public class TileManaBooster extends TileBase implements ITickable, ITileDropItems, IMachineLogic {
    public int burn_time;
    public int burn_level;
    public int total_burn_time;
    public SingleItemStackHandler handler = new SingleItemStackHandler();

    @Override
    public ItemStackHandler[] getItemStackHandlers() {
        return new ItemStackHandler[] {handler};
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        handler.deserializeNBT(compound.getCompoundTag("fuel"));
        burn_time = compound.getInteger("burn_time");
        burn_level = compound.getInteger("burn_level");
        total_burn_time = compound.getInteger("total_burn_time");
        state = compound.getInteger("state");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("fuel", handler.serializeNBT());
        compound.setInteger("burn_time", burn_time);
        compound.setInteger("burn_level", burn_level);
        compound.setInteger("total_burn_time", total_burn_time);
        compound.setInteger("state", state);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability
                && facing != null && facing.getAxis().isHorizontal()
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(hasCapability(capability, facing)) return SausageUtils.rawtype(handler);
        return super.getCapability(capability, facing);
    }

    static {
        BOOSTABLES.register(new ManaBoostable<>(TileEntityFurnace.class, furnace -> furnace.isBurning(), ITickable::update));
        BOOSTABLES.register(new ManaBoostable<>(TileEntityBrewingStand.class, brew -> brew.getField(0) > 0, ITickable::update));
    }

    private static final int STATES = 16;
    int state = STATES - 1;
    int[] times = new int[STATES];

    boolean detect0() {
        return world.canSeeSky(pos.up()) && burn_time > 0;
    }

    @Override
    public boolean detect() {
        if(detect0())
            return true;
        MB_FUELS.find(fuel -> fuel.test(handler.get()))
                .ifPresent(fuel -> {
                    total_burn_time = burn_time = fuel.time;
                    burn_level = fuel.level;
                    handler.extractItem(1, false);
                    markDirty();
        });
        return detect0();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean work() {
        if(++state == STATES) state = 0;
        if(state == 0) {
            int sum = 0;
            for (int i = 1; i < times.length; ++i)
                sum += times[i] =  burn_level / times.length;
            times[0] = burn_level - sum;
        }
        if(state % 3 == 0)
            --burn_time;
        if(state % 2 == 0)
            SavedData.get(world).list.stream()
                    .filter(dp -> world.provider.getDimension() == dp.getDim())
                    .filter(dp -> pos.distanceSq(dp.getPos()) <= boostRadius * boostRadius)
                    .filter(dp -> dp.getPos().getY() > pos.getY())
                    .filter(dp -> world.getBlockState(dp.getPos()).getValue(WORKING))
                    .map(dp -> (TileManaProducer) world.getTileEntity(dp.getPos()))
                    .filter(Objects::nonNull)
                    .limit(boostLimit)
                    .forEach(tile -> {
                        tile.progress += burn_level;
                        burn_time -= 3;
            });
        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL.facings()) {
            BlockPos offset = pos.offset(facing);
            TileEntity tileEntity = world.getTileEntity(offset);

            BOOSTABLES.find(boostable -> boostable.clazz.isInstance(tileEntity)).ifPresent(boostable -> {
                if(boostable.canBoost.test(tileEntity)) {
                    --burn_time;
                    for (int i = 0; i < times[state] && boostable.canBoost.test(tileEntity); ++i)
                        boostable.boost.accept(tileEntity);
                }
            });
        }
        if(burn_time < 0) burn_time = 0;
        return burn_time == 0;
    }

    boolean work;
    @Override
    public void update() {
        if(world.isRemote)
            return;
        IBlockState state = world.getBlockState(pos);
        work = tick(work);
        world.setBlockState(pos, state.withProperty(BURNING, work));
    }
}
