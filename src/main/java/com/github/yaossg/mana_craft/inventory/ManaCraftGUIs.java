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
    ManaProducer(ContainerManaProducer::new, GUIContainerManaProducer::new),
    ManaBooster(ContainerManaBooster::new, GUIContainerManaBooster::new);

    BiFunction<InventoryPlayer, TileEntity, Container> server;
    Function<Container, GuiContainer> client;

    ManaCraftGUIs(BiFunction<InventoryPlayer, TileEntity, Container> server, Function<Container, GuiContainer> client) {
        this.server = server;
        this.client = client;
    }

    @Nonnull
    @Override
    public Container getServer(EntityPlayer entityPlayer, World world, int x, int y, int z) {
        return server.apply(entityPlayer.inventory, world.getTileEntity(new BlockPos(x, y, z)));
    }

    @Nonnull
    @Override
    public GuiContainer getClient(EntityPlayer entityPlayer, World world, int x, int y, int z) {
        return client.apply(getServer(entityPlayer, world, x, y, z));
    }
}