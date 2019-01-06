package mana_craft.inventory;

import mana_craft.tile.TileManaProducer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;
import sausage_core.api.core.inventory.ContainerBase;

public class ContainerManaProducer extends ContainerBase<TileManaProducer> {
    public int progress;
    public int work_time;

    ContainerManaProducer(InventoryPlayer inventory, TileEntity tileEntity) {
        super(tileEntity);
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j)
                addSlotToContainer(new SlotItemHandler(this.tileEntity.input, j + i * 2, 47 + j * 18, 28 + i * 18));

        addSlotToContainer(new SlotItemHandler(this.tileEntity.output, 0, 115, 36) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int i = 0; i < 9; ++i)
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        this.progress = tileEntity.progress;
        this.work_time = tileEntity.work_time;
        listeners.forEach(listener -> {
            listener.sendWindowProperty(this, 0, progress);
            listener.sendWindowProperty(this, 1, work_time);
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        switch (id) {
            case 0:
                this.progress = data;
                break;
            case 1:
                this.work_time = data;
                break;
        }
    }
}
