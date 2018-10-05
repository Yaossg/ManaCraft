package mana_craft.tile;

import mana_craft.ManaCraft;
import sausage_core.api.util.common.SausageUtils;

public class ManaCraftTiles {
    public static void init() {
        SausageUtils.registerTileEntities(ManaCraft.MODID, TileManaProducer.class, TileManaBooster.class);
        TileManaProducer.init();
    }

}
