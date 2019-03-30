package mana_craft.inventory;

import mana_craft.tile.TileManaBooster;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import sausage_core.api.core.inventory.ContainerBase;

public class ContainerManaBooster extends ContainerBase<TileManaBooster> {
	public int burn_time;
	public int burn_level;
	public int total_burn_time;

	ContainerManaBooster(InventoryPlayer inventory, TileEntity tileEntity) {
		super(tileEntity);
		addSlotToContainer(new SlotItemHandler(this.tileEntity.handler, 0, 52, 37));
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 9; ++j)
				addSlotToContainer(new Slot(inventory, 9 + j + i * 9, 8 + j * 18, 62 + i * 18));
		for(int i = 0; i < 9; ++i)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 120));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		this.burn_time = tileEntity.burn_time;
		this.burn_level = tileEntity.burn_level;
		this.total_burn_time = tileEntity.total_burn_time;
		listeners.forEach(listener -> {
			listener.sendWindowProperty(this, 0, burn_time);
			listener.sendWindowProperty(this, 1, burn_level);
			listener.sendWindowProperty(this, 2, total_burn_time);
		});
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
			case 0:
				this.burn_time = data;
				break;
			case 1:
				this.burn_level = data;
				break;
			case 2:
				this.total_burn_time = data;
				break;
		}
	}
}
