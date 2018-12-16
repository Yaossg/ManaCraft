package mana_craft.potion;

import mana_craft.ManaCraft;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.item.ManaCraftItems;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sausage_core.api.util.potion.PotionTypeModID;

import static sausage_core.api.util.common.Conversions.To.item;

public class ManaCraftPotions {
    public static Potion potion;
    public static PotionType mana_evoker;
    public static PotionType long_mana_evoker;
    public static PotionType strong_mana_evoker;
    public static PotionType mega_mana_evoker;
    public static void preInit() {
        ForgeRegistries.POTIONS.register(potion = new PotionManaEvoker().setRegistryName("mana_evoker"));
        ForgeRegistries.POTION_TYPES.registerAll(
                mana_evoker = new PotionTypeModID(new PotionEffect(potion, 3000)).setRegistryName("mana_evoker"),
                long_mana_evoker = new PotionTypeModID("mana_evoker", new PotionEffect(potion, 8000)).setRegistryName("long_mana_evoker"),
                strong_mana_evoker = new PotionTypeModID("mana_evoker", new PotionEffect(potion, 1200, 1)).setRegistryName("strong_mana_evoker"),
                mega_mana_evoker = new PotionTypeModID("mana_evoker", new PotionEffect(potion, 12000, 2)).setRegistryName("mega_mana_evoker"));
    }

    public static void init() {
        PotionHelper.addMix(PotionTypes.AWKWARD, ManaCraftItems.mana_ball, mana_evoker);
        PotionHelper.addMix(mana_evoker, Items.REDSTONE, long_mana_evoker);
        PotionHelper.addMix(mana_evoker, Items.GLOWSTONE_DUST, strong_mana_evoker);
        PotionHelper.addMix(PotionTypes.AWKWARD, item(ManaCraftBlocks.mana_foot), mega_mana_evoker);
    }
}
