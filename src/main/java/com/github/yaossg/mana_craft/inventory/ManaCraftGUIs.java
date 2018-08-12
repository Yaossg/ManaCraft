package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.sausage_core.api.util.inventory.IEnumGUIBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Function;

public enum ManaCraftGUIs implements IEnumGUIBase {
    ManaProducer {
        @Nonnull
        @Override
        public Object getServer(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
            return new ContainerManaProducer(entityPlayer.inventory, world.getTileEntity(new BlockPos(i, i1, i2)));
        }

        @Nonnull
        @Override
        public Object getClient(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
            return new GUIContainerManaProducer(new ContainerManaProducer(entityPlayer.inventory, world.getTileEntity(new BlockPos(i, i1, i2))));
        }
    },
    ManaBooster {
        @Nonnull
        @Override
        public Object getServer(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
            return new ContainerManaBooster(entityPlayer.inventory, world.getTileEntity(new BlockPos(i, i1, i2)));
        }

        @Nonnull
        @Override
        public Object getClient(EntityPlayer entityPlayer, World world, int i, int i1, int i2) {
            return new GUIContainerManaBooster(new ContainerManaBooster(entityPlayer.inventory, world.getTileEntity(new BlockPos(i, i1, i2))));
        }
    }
}