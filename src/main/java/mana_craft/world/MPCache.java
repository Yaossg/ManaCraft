package mana_craft.world;

import mana_craft.ManaCraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import sausage_core.api.util.nbt.NBTs;

import java.util.List;

public class MPCache extends WorldSavedData {
	public final List<BlockPos> list = NonNullList.create();

	public MPCache(String name) {
		super(name);
	}

	public void add(BlockPos pos) {
		list.add(pos);
		markDirty();
	}

	public void remove(BlockPos pos) {
		list.remove(pos);
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		list.clear();
		NBTs.stream(nbt.getTagList("producers", Constants.NBT.TAG_COMPOUND))
				.map(NBTTagCompound.class::cast)
				.map(NBTUtil::getPosFromTag)
				.forEach(list::add);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("producers", list.stream().map(NBTUtil::createPosTag).collect(NBTs.toNBTList()));
		return compound;
	}

	public static final String data_id = ManaCraft.MODID + ".mana_producer";

	public static MPCache get(World world) {
		WorldSavedData data = world.getPerWorldStorage().getOrLoadData(MPCache.class, data_id);
		if (data == null) {
			data = new MPCache(data_id);
			world.getPerWorldStorage().setData(data_id, data);
		}
		return (MPCache) data;
	}
}
