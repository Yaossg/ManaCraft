package com.github.yaossg.mana_craft.tile;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.sausage_core.api.util.common.SausageUtils;

public class ManaCraftTiles {
    public static void init() {
        SausageUtils.registerTile(TileManaProducer.class, ManaCraft.MODID);
        SausageUtils.registerTile(TileManaBooster.class, ManaCraft.MODID);
        TileManaProducer.init();
    }

}
