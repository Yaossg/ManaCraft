package com.github.yaossg.mana_craft.config;

import com.github.yaossg.mana_craft.ManaCraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid = ManaCraft.MODID, name = ManaCraft.NAME)
public class ManaCraftConfig {
    @Comment("Bomb Size (set this to 0 to disable)")
    @RangeInt(min = 0, max = 100)
    @LangKey("mana_craft.general.bombSize")
    public static int bombSize = 20;

    @Comment("whether Bombs damage terrain")
    @LangKey("mana_craft.general.damageTerrain")
    public static boolean damageTerrain = false;

    @Comment("whether Bombs cause Fire")
    @LangKey("mana_craft.general.causeFire")
    public static boolean causeFire = false;

    @Comment("Mana Boost boosting boostRadius")
    @RangeInt(min = 0, max = 10)
    @LangKey("mana_craft.general.boostRadius")
    public static double boostRadius = 3;

    @Comment("the number of Mana Producers that Mana Booster can boost (set this to 0 to disable)")
    @RangeInt(min = 0, max = 100)
    @LangKey("mana_craft.general.boostLimit")
    public static int boostLimit = 3;

    @Comment("drop chance of Mana from livings")
    @RangeDouble(min = 0, max = 1)
    @LangKey("mana_craft.general.dropManaChance")
    public static float dropManaChance = 0.025f;

    @Comment("whether Mana Apple is dropped by pigs (when dropMana is true)")
    @LangKey("mana_craft.general.dropManaApple")
    public static boolean dropManaApple = true;

    @Comment("invoke chance of pigs")
    @RangeDouble(min = 0, max = 1)
    @LangKey("mana_craft.general.invokeChance")
    public static float invokeChance = 0.25f;

    @Comment("enable Final Hoe of Mana")
    @LangKey("mana_craft.general.finalHoe")
    public static boolean finalHoe = true;

    @Comment("durability of Mana Tools/Weapons/Armors")
    @RangeInt(min = 4, max = 80)
    @LangKey("mana_craft.general.durability")
    public static int durability = 20;

    @Comment("enchantability of Mana Tools/Weapons/Armors")
    @RangeInt(min = 20, max = 80)
    @LangKey("mana_craft.general.enchantability")
    @RequiresMcRestart
    public static int enchantability = 36;

    @Comment({"armor reduction amounts",
            "WARNING: make sure if its size is 4, or may cause crash"})
    @RequiresMcRestart
    public static int[] armor = {3, 6, 5, 2};

    @Comment("armor toughness")
    @LangKey("mana_craft.general.toughness")
    @RangeDouble(min = 0.5f, max = 2.5f)
    @RequiresMcRestart
    public static float toughness = 0.8f;

    @Comment("whether to spawn enchanters in village")
    @LangKey("mana_craft.general.enchanter")
    @RequiresMcRestart
    public static boolean enchanter = false;


    @Config(modid = ManaCraft.MODID, name = ManaCraft.NAME + " OreGens")
    @RequiresMcRestart
    public static class OreGens {
        @Comment({"whether to generate ores",
                "WARNING: if set this to false, values below would NOT be used"})
        public static boolean genOre = true;


        @Comment("max size of a Mana Ore vein")
        @RangeInt(min = 0, max = 40)
        public static int sizeManaOre = 13;

        @Comment("max height of Mana Ores veins")
        @RangeInt(min = 0, max = 256)
        public static int heightManaOre = 40;

        @Comment("times of generation of Mana Ore veins per chunk")
        @RangeInt(min = 0, max = 16)
        public static int timesManaOre = 4;


        @Comment("max size of a Mana Ingot Ore vein")
        @RangeInt(min = 0, max = 40)
        public static int sizeManaIngotOre = 10;

        @Comment("max height of Mana Ingot Ores veins")
        @RangeInt(min = 0, max = 256)
        public static int heightManaIngotOre = 24;

        @Comment("times of generation of Mana Ingot Ore veins per chunk")
        @RangeInt(min = 0, max = 16)
        public static int timesManaIngotOre = 1;


        @Comment({"Change of generation of mixture veins",
                "3.6 means 60% 4 times and 40% 3 times"})
        @RangeDouble(min = 0, max = 16)
        public static float mixtureChance = 0.35f;

        @Comment("max height of mixture veins")
        @RangeInt(min = 0, max = 256)
        public static int heightMixture = 32;
    }
}
