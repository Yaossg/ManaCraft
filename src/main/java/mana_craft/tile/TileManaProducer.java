package mana_craft.tile;

import mana_craft.api.registry.MPRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import sausage_core.api.core.multiblock.IBlockStatePredicate;
import sausage_core.api.core.multiblock.SimpleFixedDetector;
import sausage_core.api.core.tile.IMachineLogic;
import sausage_core.api.core.tile.ITileDropItems;
import sausage_core.api.core.tile.TileBase;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.item.ItemStackComparators;
import sausage_core.api.util.item.ItemStackMatches;
import sausage_core.api.util.item.PortableItemStackHandler;
import sausage_core.api.util.item.SingleItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static mana_craft.api.registry.ManaCraftRegistries.MP_RECIPES;
import static mana_craft.block.BlockManaProducer.FACING;
import static mana_craft.block.BlockManaProducer.WORKING;
import static mana_craft.init.ManaCraftBlocks.*;

public class TileManaProducer extends TileBase implements ITickable, ITileDropItems, IMachineLogic {
	public int progress;
	public int work_time;
	public PortableItemStackHandler input = new PortableItemStackHandler(4) {
		@Override
		public void onContentsChanged(int slot) {
			current = null;
		}
	};
	public SingleItemStackHandler output = new SingleItemStackHandler();

	@Override
	public IItemHandler[] getItemStackHandlers() {
		return new IItemHandler[]{input, output};
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		input.deserializeNBT(compound.getCompoundTag("input"));
		output.deserializeNBT(compound.getCompoundTag("output"));
		progress = compound.getInteger("progress");
		work_time = compound.getInteger("work_time");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("input", input.serializeNBT());
		compound.setTag("output", output.serializeNBT());
		compound.setInteger("progress", progress);
		compound.setInteger("work_time", work_time);
		return super.writeToNBT(compound);
	}

	public boolean hasCapItem(Capability<?> capability, @Nullable EnumFacing side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability && side != null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side) {
		return hasCapItem(capability, side) || super.hasCapability(capability, side);
	}

	private class SlotMapper implements IItemHandler {
		private int slot;

		SlotMapper(int slot) {
			this.slot = slot;
		}

		@Override
		public int getSlots() {
			return 1;
		}

		@Nonnull
		@Override
		public ItemStack getStackInSlot(int slot) {
			return input.getStackInSlot(this.slot);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			return input.insertItem(this.slot, stack, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return input.extractItem(this.slot, amount, simulate);
		}

		@Override
		public int getSlotLimit(int slot) {
			return input.getSlotLimit(this.slot);
		}
	}

	private SlotMapper[] mappers = {new SlotMapper(0), new SlotMapper(1), new SlotMapper(2), new SlotMapper(3)};

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		if (hasCapItem(capability, side)) switch (side) {
			case UP:
				return SausageUtils.rawtype(input);
			case DOWN:
				return SausageUtils.rawtype(output);
			default:
				return SausageUtils.rawtype(mappers[side.getHorizontalIndex()]);
		}
		return super.getCapability(capability, side);
	}

	private static SimpleFixedDetector detector;

	public static void init() {
		detector = SimpleFixedDetector.patternBuilder($ -> true)
				.layer(0,
						"  _  ",
						" #_# ",
						"_!M!_",
						" #!# ",
						"  _  ")
				.layer(-1,
						"     ",
						" *#* ",
						" ### ",
						" *#* ",
						"     ")
				.layer(1,
						"     ",
						" _#_ ",
						"_#!#_",
						" _#_ ",
						"     ")
				.layer(2,
						"     ",
						"  _  ",
						" _^_ ",
						"  _  ",
						"     ")
				.mapping('_', IBlockStatePredicate.of(Blocks.AIR))
				.mapping('^', IBlockStatePredicate.of(mana_lantern))
				.mapping('#', IBlockStatePredicate.of(mana_block))
				.mapping('!', IBlockStatePredicate.of(mana_glass))
				.mapping('*', IBlockStatePredicate.of(orichalcum_block))
				.build();
	}

	public static boolean checkFrame(World world, BlockPos pos) {
		return detector.detect(world, pos).isPresent();
	}

	public static boolean checkMachine(World world, BlockPos pos) {
		return checkFrame(world, pos) && world.isAirBlock(pos.offset(world.getBlockState(pos).getValue(FACING)));
	}

	MPRecipe current = null;

	boolean detect0() {
		return current != null && output.insertItem(current.output(), true).isEmpty();
	}

	@Override
	public boolean detect() {
		if (detect0())
			return true;
		current = MP_RECIPES.find(recipe ->
				ItemStackMatches.match(recipe.input(), ItemStackMatches.merge(input.copyStacks())) != null).orElse(null);
		return detect0();
	}

	@Override
	public boolean work() {
		if (work_time != current.work_time) {
			work_time = current.work_time;
			progress = Math.min(progress, work_time - 1);
		}
		if ((++progress) >= work_time) {
			progress -= current.work_time;
			ItemStack[] match = ItemStackMatches.match(current.input(), ItemStackMatches.merge(input.copyStacks()));
			ItemStack copy = current.output();
			ItemStackMatches.remove(input, match, ItemStackComparators.STACKABLE);
			output.insertItem(copy, false);
			current = null;
			return true;
		}
		return false;
	}

	boolean work;

	@Override
	public void update() {
		if (world.isRemote) return;
		if (checkMachine(world, pos)) {
			progress = Math.min(progress, work_time);
			work = tick(work);
			world.setBlockState(pos, world.getBlockState(pos).withProperty(WORKING, work));
			if (!work) {
				if (progress > 0) progress -= 3;
				if (progress < 0) progress = 0;
			}
		} else {
			world.destroyBlock(pos, true);
		}
	}
}
