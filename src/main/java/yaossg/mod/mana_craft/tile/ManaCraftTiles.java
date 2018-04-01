package yaossg.mod.mana_craft.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yaossg.mod.mana_craft.ManaCraft;

public class ManaCraftTiles {
    public static void init() {
        registerTileEntity(TileManaProducer.class, "ManaProducer");
        registerTileEntity(TileManaBooster.class, "ManaBooster");
    }
    public static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String id)
    {
        GameRegistry.registerTileEntity(tileEntityClass, ManaCraft.MODID + ":" + id);
    }
}
