package yaossg.mod.mana_craft.tile;

import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
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
import yaossg.mod.mana_craft.api.ManaCraftAPIs;
import yaossg.mod.mana_craft.config.Config;
import yaossg.mod.mana_craft.block.BlockManaProducer;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.item.ManaCraftItems;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static yaossg.mod.mana_craft.APIImpl.fuels;
import static yaossg.mod.mana_craft.api.ManaCraftAPIs.Fuel;
import static yaossg.mod.mana_craft.block.BlockManaBooster.*;

public class TileManaBooster extends TileEntity implements ITickable {
    public int burn_time = 0;
    public int burn_level = 0;
    public int total_burn_time = 0;
    public ItemStackHandler fuel = new ItemStackHandler();
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.fuel.deserializeNBT(compound.getCompoundTag("fuel"));
        this.burn_time = compound.getInteger("burn_time");
        this.burn_level = compound.getInteger("burn_level");
        this.total_burn_time = compound.getInteger("total_burn_time");
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
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability) || super.hasCapability(capability, facing);
    }
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.equals(capability))
        {
            @SuppressWarnings("unchecked")
            T result = (T) (fuel);
            return result;
        }
        return super.getCapability(capability, facing);
    }
    static {
        fuels.addAll(Arrays.asList(
                Fuel.of(ManaCraftItems.itemMana, 20,200),
                Fuel.of(Item.getItemFromBlock(ManaCraftBlocks.blockMana), 100, 360),
                Fuel.of(ManaCraftItems.itemManaBall, 108, 360),
                Fuel.of(ManaCraftItems.itemManaApple, 888, 360),
                Fuel.of(ManaCraftItems.itemManaNugget, 500, 400),
                Fuel.of(ManaCraftItems.itemManaIngot, 4000, 450),
                Fuel.of(Item.getItemFromBlock(ManaCraftBlocks.blockManaIngot), 12960, 1250),
                Fuel.of(ManaCraftItems.itemManaCoal, 400, 800),
                Fuel.of(ManaCraftItems.itemManaDiamond, 14400, 1600),
                Fuel.of(ManaCraftItems.itemManaPork, 8888, 40)
        ));
    }
    boolean flip = false;
    @Override
    public void update() {
        if (!world.isRemote) {
            IBlockState state = this.getWorld().getBlockState(pos);
            if(world.canSeeSky(pos.up())) {
                if (burn_time > 0) {
                    if (flip = !flip) {
                        --burn_time;
                        for (EnumFacing facing : EnumFacing.Plane.HORIZONTAL.facings()) {
                            TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                            if(tileEntity instanceof TileEntityFurnace) {
                                TileEntityFurnace furnace = (TileEntityFurnace) tileEntity;
                                furnace.setField(2, Math.min(furnace.getField(2) + burn_level / 20, furnace.getCookTime(ItemStack.EMPTY) - 1));
                                BlockFurnace.setState(furnace.isBurning(), world, furnace.getPos());
                                burn_time -= 4;
                            }
                            if(tileEntity instanceof TileEntityBrewingStand) {
                                TileEntityBrewingStand brew = (TileEntityBrewingStand) tileEntity;
                                brew.setField(0, Math.max(brew.getField(0) - burn_level / 20, 1));
                                burn_time -= 4;
                            }
                        }

                        BlockManaProducer.SavedData.get(world).list.stream()
                                .filter(pos0 -> pos.distanceSq(pos0) <= Config.radius * Config.radius
                                        && pos0.getY() > pos.getY() && world.getBlockState(pos0).getValue(BlockManaProducer.WORKING))
                                .map(pos0 -> (TileManaProducer) world.getTileEntity(pos0))
                                .limit(Config.limit).filter(Objects::nonNull).forEach(tile -> {
                            tile.work_time += burn_level;
                            burn_time -= 3;
                        });
                    }
                } else {
                    Item item = fuel.getStackInSlot(0).getItem();
                    Optional<ManaCraftAPIs.Fuel> opt = fuels.stream().filter(e -> e.getItem() == item).findAny();
                    if(opt.isPresent()) {
                        world.setBlockState(pos, state.withProperty(BURNING, Boolean.TRUE));
                        total_burn_time = burn_time = opt.get().getBurnTime();
                        burn_level = opt.get().getBurnLevel();
                        fuel.extractItem(0, 1, false);
                        markDirty();
                    } else {
                        world.setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE));
                    }
                }
            } else {
                world.setBlockState(pos, state.withProperty(BURNING, Boolean.FALSE));
            }
        }
    }
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
