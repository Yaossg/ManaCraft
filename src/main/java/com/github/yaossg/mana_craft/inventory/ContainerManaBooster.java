package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.tile.TileManaBooster;
import com.github.yaossg.sausage_core.api.util.inventory.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerManaBooster extends ContainerBase<TileManaBooster> {

    public int burn_time;
    public int burn_level;
    public int total_burn_time;

    ContainerManaBooster(EntityPlayer player, TileEntity tileEntity) {
        super(tileEntity);
        addSlotToContainer(new SlotItemHandler(this.tileEntity.fuel, 0, 80, 48));
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int i = 0; i < 9; ++i)
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
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
        switch (id) {
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
