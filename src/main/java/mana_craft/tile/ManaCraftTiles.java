package mana_craft.tile;

import mana_craft.ManaCraft;
import sausage_core.api.util.common.SausageUtils;

public class ManaCraftTiles {
    public static void init() {
        SausageUtils.registerTileEntity(ManaCraft.MODID, TileManaProducer.class);
        SausageUtils.registerTileEntity(ManaCraft.MODID, TileManaBooster.class);
        TileManaProducer.init();
    }

}
