package mana_craft.config;

import mana_craft.ManaCraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ManaCraft.MODID, name = ManaCraft.NAME)
public class ManaCraftConfig {
    @Comment("radius of Mana Booster can boost")
    @RangeInt(min = 0, max = 10)
    @LangKey("mana_craft.general.boostRadius")
    public static double boostRadius = 3;

    @Comment("the number of Mana Producers that Mana Booster can boost (set this to 0 to disable)")
    @RangeInt(min = 0, max = 100)
    @LangKey("mana_craft.general.boostLimit")
    public static int boostLimit = 3;

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
    @RequiresMcRestart
    public static int durability = 24;

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

    @Comment("min speed of floating mana ball")
    @LangKey("mana_craft.general.min_speed")
    @RangeDouble(min = 0.001, max = 0.036)
    public static double minSpeed = 0.006;

    @Comment("can Mana Produecer Generator replace block")
    @LangKey("mana_craft.general.replace")
    public static boolean replace = false;

    @Comment("whether Mana Produecer can self-destroy without energizing")
    @LangKey("mana_craft.general.destroy")
    public static boolean destroy = true;

    @Comment("checking delay of Mana Producer")
    @LangKey("mana_craft.general.delay")
    @RangeInt(min = 1, max = 100)
    public static int delay = 8;

    @Comment("Mana Shooter become dying below the ratio of health")
    @LangKey("mana_craft.general.ratio")
    @RangeDouble(min = 0, max = 1)
    public static float ratio = 0.1f;

    @Comment("is there mana variant of village")
    @LangKey("mana_craft.general.village")
    @RequiresMcRestart
    public static boolean village = true;

    @Comment("is there ManaCraft village structure")
    @LangKey("mana_craft.general.village_structure")
    @RequiresMcRestart
    public static boolean village_structure = true;

    @Comment("is there special loot for ManaCraft")
    @LangKey("mana_craft.general.loot")
    @RequiresMcRestart
    public static boolean loot = true;

    @Comment("does grass drop mana")
    @LangKey("mana_craft.general.grassMana")
    @RequiresMcRestart
    public static boolean grassMana = true;

    @Config(modid = ManaCraft.MODID, name = ManaCraft.NAME + " OreGens")
    public static class OreGens {
        @Comment("max size of a Mana Ore vein")
        @LangKey("mana_craft.ore_gens.general.sizeManaOre")
        @RangeInt(min = 0, max = 40)
        @RequiresMcRestart
        public static int sizeManaOre = 17;

        @Comment("max height of Mana Ores veins")
        @LangKey("mana_craft.ore_gens.general.heightManaOre")
        @RangeInt(min = 0, max = 256)
        public static int heightManaOre = 40;

        @Comment("times of generation of Mana Ore veins per chunk")
        @LangKey("mana_craft.ore_gens.general.timesManaOre")
        @RangeInt(min = 0, max = 16)
        public static int timesManaOre = 2;

        @Comment("max size of a Orichalcum Ore vein")
        @LangKey("mana_craft.ore_gens.general.sizeOrichalcumOre")
        @RangeInt(min = 0, max = 40)
        @RequiresMcRestart
        public static int sizeOrichalcumOre = 10;

        @Comment("max height of Mana Orichalcum veins")
        @LangKey("mana_craft.ore_gens.general.heightOrichalcumOre")
        @RangeInt(min = 0, max = 256)
        public static int heightOrichalcumOre = 24;

        @Comment("times of generation of Orichalcum Ore veins per chunk")
        @LangKey("mana_craft.ore_gens.general.timesOrichalcumOre")
        @RangeInt(min = 0, max = 16)
        public static int timesOrichalcumOre = 1;

        @Comment({"Change of generation of mixture veins",
                "3.6 means 60% 4 times and 40% 3 times"})
        @LangKey("mana_craft.ore_gens.general.mixtureChance")
        @RangeDouble(min = 0, max = 16)
        public static float mixtureChance = 0.35f;

        @Comment("max height of mixture veins")
        @LangKey("mana_craft.ore_gens.general.heightMixture")
        @RangeInt(min = 0, max = 256)
        public static int heightMixture = 32;
    }


    @Mod.EventBusSubscriber(modid = ManaCraft.MODID)
    public static class Sync {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(ManaCraft.MODID))
                ConfigManager.sync(ManaCraft.MODID, Type.INSTANCE);
        }

    }
}
