package com.github.yaossg.mana_craft.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
    public static Configuration config;
    //definition of defaults
    //  General
    public static int bombSize = 20;
    public static boolean damage = false;
    public static boolean fire = false;
    public static int radius = 3;
    public static int limit = 3;
    public static int dropManaChance = 233;
    public static boolean dropManaApple = true;
    public static int invokeChance = 4;
    public static boolean hoe = true;
    public static int[] armor = new int[]{3, 6, 5, 2};
    //  Ore Generation
    public static boolean genOre = true;
    public static int sizeManaOre = 12;
    public static int heightManaOre = 36;
    public static int timesManaOre = 4;
    public static int sizeManaIngotOre = 8;
    public static int heightManaIngotOre = 24;
    public static int timesManaIngotOre = 1;
    public static int mixtureChance = 3;
    public static int mixtureHeight = 32;
    public static int mixtureTimes = 4;

    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        load();
        config.save();
    }

    public static void load() {
        String key, comment;

        key = "bombSize";
        comment = "Bomb Size (disabled if not positive)";
        bombSize = config.get(Configuration.CATEGORY_GENERAL, key, bombSize, comment).getInt();

        key = "damage";
        comment = "whether damage terrain";
        damage = config.get(Configuration.CATEGORY_GENERAL, key, damage, comment).getBoolean();

        key = "fire";
        comment = "wheather cause fire";
        fire = config.get(Configuration.CATEGORY_GENERAL, key, fire, comment).getBoolean();

        key = "limit";
        comment = "the number of Mana Producers that Mana Booster can boost (disabled if not positive)";
        limit = config.get(Configuration.CATEGORY_GENERAL, key, limit, comment).getInt();

        key = "radius";
        comment = "the radius of Mana Producers that Mana Booster can boost (disabled if less than 1)";
        radius = config.get(Configuration.CATEGORY_GENERAL, key, radius, comment).getInt();

        key = "genOre";
        comment = "whether generate ores";
        genOre = config.get(Configuration.CATEGORY_GENERAL, key, genOre, comment).getBoolean();

        key = "dropManaChance";
        comment = "drop chance of Mana from livings (disabled if negative, 100% if 0, 0% if 255 or greater without looting)";
        dropManaChance = config.get(Configuration.CATEGORY_GENERAL, key, dropManaChance, comment).getInt();

        key = "dropManaApple";
        comment = "whether Mana Apple would been dropped by pigs (when dropMana is true)";
        dropManaApple = config.get(Configuration.CATEGORY_GENERAL, key, dropManaApple, comment).getBoolean();

        key = "invokeChance";
        comment = "invoke chance of pigs (disabled if not positive, or 1 in [value below])";
        invokeChance = config.get(Configuration.CATEGORY_GENERAL, key, invokeChance, comment).getInt();

        key = "hoe";
        comment = "enable Final Hoe of Mana";
        hoe = config.get(Configuration.CATEGORY_GENERAL, key, hoe, comment).getBoolean();

        key = "armor";
        comment = "armor reduction amounts";
        armor = config.get(Configuration.CATEGORY_GENERAL, key, armor, comment).getIntList();

        key = "sizeManaOre";
        comment = "max size of a single Mana Ore vein";
        sizeManaOre = config.get(Configuration.CATEGORY_GENERAL, key, sizeManaOre, comment).getInt();

        key = "heightManaOre";
        comment = "max height of Mana Ores veins";
        heightManaOre = config.get(Configuration.CATEGORY_GENERAL, key, heightManaOre, comment).getInt();

        key = "timesManaOre";
        comment = "times of generation of Mana Ore veins per chunk";
        timesManaOre = config.get(Configuration.CATEGORY_GENERAL, key, timesManaOre, comment).getInt();

        key = "sizeManaIngotOre";
        comment = "max size of a single Mana Ingot Ore vein";
        sizeManaIngotOre = config.get(Configuration.CATEGORY_GENERAL, key, sizeManaIngotOre, comment).getInt();

        key = "heightManaIngotOre";
        comment = "max height of Mana Ingot Ores veins";
        timesManaIngotOre = config.get(Configuration.CATEGORY_GENERAL, key, timesManaIngotOre, comment).getInt();

        key = "timesManaIngotOre";
        comment = "times of generation of Mana Ingot Ore veins per chunk";
        heightManaIngotOre = config.get(Configuration.CATEGORY_GENERAL, key, heightManaIngotOre, comment).getInt();

        key = "mixtureChance";
        comment = "Change of generation of mixture veins (1 in [value below] per chunk)";
        mixtureChance = config.get(Configuration.CATEGORY_GENERAL, key, mixtureChance, comment).getInt();

        key = "mixtureHeight";
        comment = "max height of mixture veins";
        mixtureHeight = config.get(Configuration.CATEGORY_GENERAL, key, mixtureHeight, comment).getInt();

        key = "mixtureTimes";
        comment = "times of generation of mixture veins per chunk";
        mixtureTimes = config.get(Configuration.CATEGORY_GENERAL, key, mixtureTimes, comment).getInt();
    }
}
