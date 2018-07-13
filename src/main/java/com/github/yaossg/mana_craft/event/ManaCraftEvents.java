package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import net.minecraftforge.common.MinecraftForge;

public class ManaCraftEvents {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolEvent.class);
        if(ManaCraftConfig.dropManaChance > 0)
            MinecraftForge.EVENT_BUS.register(ManaDropEvent.class);
    }
}
