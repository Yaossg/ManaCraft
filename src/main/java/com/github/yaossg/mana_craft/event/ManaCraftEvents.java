package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.config.Config;
import net.minecraftforge.common.MinecraftForge;

public class ManaCraftEvents {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolsEvent.class);
        if(Config.dropManaChance >= 0)
            MinecraftForge.EVENT_BUS.register(ManaDropEvent.class);
    }
}
