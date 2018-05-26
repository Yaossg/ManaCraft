package yaossg.mod.mana_craft.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.sausage_core.api.util.IGUIManager;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum ManaCraftGUIs implements IGUIManager {
    ManaProducer(ContainerManaProducer::new, GUIContainerManaProducer::new),
    ManaBooster(ContainerManaBooster::new, GUIContainerManaBooster::new);

    public static void init() {
        IGUIManager.register(ManaCraft.instance, ManaCraftGUIs.values());
    }

    BiFunction<EntityPlayer, TileEntity, Container> server;
    Function<Container, GuiContainer> client;
    ManaCraftGUIs(BiFunction<EntityPlayer, TileEntity, Container> server, Function<Container, GuiContainer> client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public BiFunction<EntityPlayer, TileEntity, Container> getServerBuilder() {
        return server;
    }

    @Override
    public Function<Container, GuiContainer> getClientBuilder() {
        return client;
    }
}