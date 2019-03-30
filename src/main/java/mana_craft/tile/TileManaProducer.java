package mana_craft.tile;

import mana_craft.api.registry.MPRecipe;
import mana_craft.block.BlockManaFoot;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
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
import static mana_craft.block.BlockManaProducer.WORKING;
import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.config.ManaCraftConfig.delay;
import static mana_craft.config.ManaCraftConfig.destroy;
import static net.minecraft.block.state.BlockWorldState.hasState;
import static net.minecraft.block.state.pattern.BlockStateMatcher.forBlock;

public class TileManaProducer extends TileBase implements ITickable, ITileDropItems, IMachineLogic {
	public int progress;
	public int work_time;
	public PortableItemStackHandler input = new PortableItemStackHandler(4) {
		@Override
		public void onContentsChanged(int slot) {
			current = null;
			isCharged = false;
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

	class SlotMapper implements IItemHandler {
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

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side) {
		if(hasCapItem(capability, side)) switch(side) {
			case UP:
				return SausageUtils.rawtype(input);
			case DOWN:
				return SausageUtils.rawtype(output);
			default:
				return SausageUtils.rawtype(new SlotMapper(side.getHorizontalIndex()));
		}
		return super.getCapability(capability, side);
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
				.where('^', hasState(forBlock(mana_lantern)))
				.where('#', hasState(forBlock(mana_block)))
				.where('!', hasState(forBlock(mana_glass)))
				.where('*', hasState(forBlock(orichalcum_block)))
				.where('O', TileManaProducer::core)
				.build();
	}

	public static boolean core(BlockWorldState bws) {
		IBlockState state = bws.getBlockState();
		if(state.getBlock() != mana_producer)
			return false;
		World world = bws.world;
		EnumFacing facing = state.getValue(BlockManaFoot.FACING);
		BlockPos pos = bws.getPos();
		return world.isAirBlock(pos.offset(facing))
				&& world.getBlockState(pos.offset(facing.rotateY())).getBlock() == mana_glass
				&& world.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() == mana_glass
				&& world.getBlockState(pos.offset(facing.getOpposite())).getBlock() == mana_glass;
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
			checkDelay = delay;
		}
		return true;
	}

	MPRecipe current = null;

	boolean detect0() {
		return current != null && output.insertItem(current.output(), true).isEmpty();
	}

	@Override
	public boolean detect() {
		if(detect0())
			return true;
		current = MP_RECIPES.find(recipe ->
				ItemStackMatches.match(recipe.input(), ItemStackMatches.merge(input.copyStacks())) != null).orElse(null);
		return detect0();
	}

	@Override
	public boolean work() {
		if(work_time != current.work_time) {
			work_time = current.work_time;
			progress = Math.min(progress, work_time - 1);
		}
		if((++progress) >= work_time) {
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
		if(world.isRemote || !check()) return;
		progress = Math.min(progress, work_time);
		work = tick(work);
		world.setBlockState(pos, world.getBlockState(pos).withProperty(WORKING, work));
		if(!work) {
			if(progress > 0) progress -= 3;
			if(progress < 0) progress = 0;
		}
	}
}
