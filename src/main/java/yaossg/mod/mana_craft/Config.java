package yaossg.mod.mana_craft;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
    public static Configuration config;
    //defaults
    public static int PES = 20;
    public static boolean PEA = true;
    public static boolean MBI = true;
    public static int limit = 3;
    public static int weight = 2333333;
    public static int mana_ore_size = 12;
    public static int mana_ore_height = 36;
    public static int mana_ore_times = 4;
    public static int mana_ingot_ore_size = 8;
    public static int mana_ingot_ore_height = 24;
    public static int mana_ingot_ore_times = 1;
    public static int mixture_chance = 3;
    public static int mixture_height = 32;
    public static int mixture_times = 4;

    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
    }
    public static void load() {
        String key, comment;

        key = "PES";
        comment = "Pig Explosion Size (disabled if not positive)";
        PES = config.get(Configuration.CATEGORY_GENERAL, key, PES, comment).getInt();

        key = "PEA";
        comment = "true if pigs eat Mana Apples (requires PES > 0)";
        PEA = config.get(Configuration.CATEGORY_GENERAL, key, PEA, comment).getBoolean();

        key = "MBI";
        comment = "true if Mana Ball invokes pigs (requires PES > 0)";
        MBI = config.get(Configuration.CATEGORY_GENERAL, key, MBI, comment).getBoolean();

        key = "limit";
        comment = "the number of Mana Producers that Mana Booster can boost (disabled if not positive)";
        limit = config.get(Configuration.CATEGORY_GENERAL, key, limit, comment).getInt();

        key = "weight";
        comment = "the weight of ore generation (disabled if not positive)";
        weight = config.get(Configuration.CATEGORY_GENERAL, key, weight, comment).getInt();

        key = "mana_ore_size";
        comment = "max size of a single Mana Ore vein";
        mana_ore_size = config.get(Configuration.CATEGORY_GENERAL, key, mana_ore_size, comment).getInt();

        key = "mana_ore_height";
        comment = "max height of Mana Ores veins";
        mana_ore_height = config.get(Configuration.CATEGORY_GENERAL, key, mana_ore_height, comment).getInt();

        key = "mana_ore_times";
        comment = "times of generation of Mana Ore veins per chunk";
        mana_ore_times = config.get(Configuration.CATEGORY_GENERAL, key, mana_ore_times, comment).getInt();

        key = "mana_ingot_ore_size";
        comment = "max size of a single Mana Ingot Ore vein";
        mana_ingot_ore_size = config.get(Configuration.CATEGORY_GENERAL, key, mana_ingot_ore_size, comment).getInt();

        key = "mana_ingot_ore_height";
        comment = "max height of Mana Ingot Ores veins";
        mana_ingot_ore_times = config.get(Configuration.CATEGORY_GENERAL, key, mana_ingot_ore_times, comment).getInt();

        key = "mana_ingot_ore_times";
        comment = "times of generation of Mana Ingot Ore veins per chunk";
        mana_ingot_ore_height= config.get(Configuration.CATEGORY_GENERAL, key, mana_ingot_ore_height, comment).getInt();

        key = "mixture_chance";
        comment = "Change of generation of mixture veins (1 in [value below] per chunk)";
        mixture_chance= config.get(Configuration.CATEGORY_GENERAL, key, mixture_chance, comment).getInt();

        key = "mixture_height";
        comment = "max height of mixture veins";
        mixture_height = config.get(Configuration.CATEGORY_GENERAL, key, mixture_height, comment).getInt();

        key = "mixture_times";
        comment = "times of generation of mixture veins per chunk";
        mixture_times = config.get(Configuration.CATEGORY_GENERAL, key, mixture_times, comment).getInt();

        config.save();
    }
}
