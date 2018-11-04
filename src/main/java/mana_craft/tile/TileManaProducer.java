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
import net.minecraftforge.items.ItemStackHandler;
import sausage_core.api.util.item.*;
import sausage_core.api.util.tile.ITileDropItems;
import sausage_core.api.util.tile.TileBase;

import static mana_craft.api.registry.IManaCraftRegistries.MP_RECIPES;
import static mana_craft.block.BlockManaProducer.WORKING;
import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.config.ManaCraftConfig.delay;
import static mana_craft.config.ManaCraftConfig.destroy;
import static net.minecraft.block.state.BlockWorldState.hasState;
import static net.minecraft.block.state.pattern.BlockStateMatcher.forBlock;

public class TileManaProducer extends TileBase implements ITickable, ITileDropItems {
    public int work_time;
    public int total_work_time;
    public PortableItemStackHandler input = new PortableItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            current = null;
            isCharged = false;
        }
    };
    public SingleItemStackHandler output = new SingleItemStackHandler();

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

    MPRecipe current = null;
    void work() {
        if((++work_time) >= total_work_time) {
            work_time -= current.work_time;
            ItemStack[] match = ItemStackMatches.match(current.input(), ItemStackMatches.merge(input.view()));
            ItemStack copy = current.output();
            ItemStackMatches.remove(input, match);
            output.insertItem(copy, false);
        }
    }
    @Override
    public void update() {
        IBlockState state = world.getBlockState(pos);
        if(world.isRemote || !check()) return;
        work_time = Math.min(work_time, total_work_time);
        if(current == null)
            current = MP_RECIPES.find(recipe ->
                    ItemStackMatches.match(recipe.input(), ItemStackMatches.merge(input.view())) != null).orElse(null);
        if(current != null && output.insertItem(current.output(), true).isEmpty()) {
            world.setBlockState(pos, state.withProperty(WORKING, Boolean.TRUE));
            if(total_work_time != current.work_time) {
                total_work_time = current.work_time;
                work_time = Math.min(work_time, total_work_time - 1);
            }
            work();
        } else {
            if(work_time > 0) work_time -= 3;
            if(work_time < 0) work_time = 0;
            world.setBlockState(pos, state.withProperty(WORKING, Boolean.FALSE));
        }
    }
}
