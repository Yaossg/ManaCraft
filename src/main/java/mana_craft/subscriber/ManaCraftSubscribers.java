package mana_craft.subscriber;

import mana_craft.config.ManaCraftConfig;
import net.minecraftforge.common.MinecraftForge;

public class ManaCraftSubscribers {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(ManaToolSubscriber.class);
        if(ManaCraftConfig.dropManaChance > 0)
            MinecraftForge.EVENT_BUS.register(ManaDropSubscriber.class);
    }
}
