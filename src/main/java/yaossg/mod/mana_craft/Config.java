package yaossg.mod.mana_craft;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
    public static Configuration config;
    //defaults
    public static int PES = 20;
    public static int limit = 3;
    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }
    public static void load() {
        String key, comment;

        key = "PES";
        comment = "Pig Explosion Size (disabled if 0 or negate)";
        PES = config.get(Configuration.CATEGORY_GENERAL, key, PES, comment).getInt();

        key = "limit";
        comment = "How many Mana Producers can Mana Booster boost (disabled if 0 or negate)";
        limit = config.get(Configuration.CATEGORY_GENERAL, key, limit, comment).getInt();


        config.save();
    }
}
