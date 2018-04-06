package yaossg.mod.mana_craft.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.tile.TileManaProducer;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ManaCraftGUIs implements IGuiHandler {

    public enum GUIs {
        NULL((player, tileEntity) -> null, container -> null),
        ManaProducer(ContainerManaProducer::new, GUIContainerManaProducer::new),
        ManaBooster(ContainerManaBooster::new, GUIContainerManaBooster::new);
        BiFunction<EntityPlayer, TileEntity, Container> container;
        Function<Container, GuiContainer> client;
        GUIs(BiFunction<EntityPlayer, TileEntity, Container> container, Function<Container, GuiContainer> client) {
            this.container = container;
            this.client = client;
        }
        public Container getServer(EntityPlayer player, World world, BlockPos pos) {
            return container.apply(player, world.getTileEntity(pos));
        }
        public GuiContainer getClient(Container container) {
            return client.apply(container);
        }
        public static Container matchServer(int id, EntityPlayer player, World world, BlockPos pos) {
            return Arrays.stream(GUIs.values())
                    .filter(gui -> gui.ordinal() == id)
                    .map(gui -> gui.getServer(player, world, pos))
                    .findAny().orElse(null);
        }

        public static GuiContainer matchClient(int id, EntityPlayer player, World world, BlockPos pos) {
            return Arrays.stream(GUIs.values())
                    .filter(gui -> gui.ordinal() == id)
                    .map(gui -> gui.getClient(gui.getServer(player, world, pos)))
                    .findAny().orElse(null);
        }
    }

    public static void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ManaCraft.instance, new ManaCraftGUIs());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return GUIs.matchServer(ID, player, world, new BlockPos(x, y, z));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return GUIs.matchClient(ID, player, world, new BlockPos(x, y, z));
    }
}
