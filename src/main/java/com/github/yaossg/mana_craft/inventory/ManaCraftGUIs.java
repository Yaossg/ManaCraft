package com.github.yaossg.mana_craft.inventory;

import com.github.yaossg.sausage_core.api.util.IGUIManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum ManaCraftGUIs implements IGUIManager {
    ManaProducer(ContainerManaProducer::new, GUIContainerManaProducer::new),
    ManaBooster(ContainerManaBooster::new, GUIContainerManaBooster::new);

    BiFunction<EntityPlayer, TileEntity, Container> common;
    Function<Container, GuiContainer> client;
    ManaCraftGUIs(BiFunction<EntityPlayer, TileEntity, Container> common, Function<Container, GuiContainer> client) {
        this.common = common;
        this.client = client;
    }

    @Override
    public BiFunction<EntityPlayer, TileEntity, Container> getCommonBuilder() {
        return common;
    }

    @Override
    public Function<Container, GuiContainer> getClientBuilder() {
        return client;
    }
}