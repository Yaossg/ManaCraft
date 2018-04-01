package yaossg.mod.mana_craft;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
    public static Configuration config;
    public static int PES;
    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }
    public static void load() {

        String key, comment;

        key = "PES";
        comment = "Pig Explosion Size (disabled if 0 or negate)";
        PES = config.get(Configuration.CATEGORY_GENERAL, key, 20, comment).getInt();

        config.save();
    }
}
