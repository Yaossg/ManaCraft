package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.mana_craft.tile.TileManaProducer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerManaProducer extends Container {

    private Slot[] slotInput = new Slot[4];
    @SuppressWarnings("all")
    private Slot slotOutput;
    public int work_time;
    public int total_work_time;
    protected TileManaProducer tileEntity;

    public ContainerManaProducer(EntityPlayer player, TileEntity tileEntity) {
        this.tileEntity = (TileManaProducer) tileEntity;
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j)
                this.addSlotToContainer(slotInput[j + i * 2] = new SlotItemHandler(this.tileEntity.input, j + i * 2, 47 + j * 18, 28 + i * 18) {
                    @Override
                    public void onSlotChanged() {
                        ContainerManaProducer.this.tileEntity.isSorted = false;
                        super.onSlotChanged();
                    }
                });

        this.addSlotToContainer(slotOutput = new SlotItemHandler(this.tileEntity.output, 0, 115,36) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = inventorySlots.get(index);

        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

        boolean isMerged = false;

        if (index < 5) {
            isMerged = mergeItemStack(newStack, 5, 41, true);
        } else if (index < 32) {
            for(Slot slot0 : slotInput) {
                isMerged = slot0.getStack().isStackable() && mergeItemStack(newStack, 0, 4, false )
                        || mergeItemStack(newStack, 32, 41, false);
            }
        } else {
            for(Slot slot0 : slotInput) {
                isMerged = slot0.getStack().isStackable() && mergeItemStack(newStack, 0, 4, false )
                        || mergeItemStack(newStack, 5,32, false);
            }
        }

        if (!isMerged)
            return ItemStack.EMPTY;

        if (newStack.isEmpty())
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        return oldStack;
    }
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return playerIn.getDistanceSq(tileEntity.getPos()) <= 64;
    }
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        this.work_time = tileEntity.work_time;
        this.total_work_time = tileEntity.total_work_time;
        listeners.forEach( listener -> {
            listener.sendWindowProperty(this, 0, work_time);
            listener.sendWindowProperty(this, 1, total_work_time);
        });
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id)
        {
            case 0:
                this.work_time = data;
                break;
            case 1:
                this.total_work_time = data;
                break;
        }
    }
}
