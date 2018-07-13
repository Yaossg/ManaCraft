package com.github.yaossg.mana_craft.enchantment;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ManaCraftEnchantments {
    public static final Enchantment floating = new EnchantmentFloating();
    public static final Enchantment manaEvoker = new EnchantmentManaEvoker();
    public static final Enchantment manaRecycler = new EnchantmentManaRecycler();
    public static void init() {
        ForgeRegistries.ENCHANTMENTS.register(floating);
        CreativeTabs.COMBAT.setRelevantEnchantmentTypes(EnchantmentFloating.TYPE);
        ForgeRegistries.ENCHANTMENTS.register(manaEvoker);
        CreativeTabs.COMBAT.setRelevantEnchantmentTypes(EnchantmentManaEvoker.TYPE);
        ForgeRegistries.ENCHANTMENTS.register(manaRecycler);
        CreativeTabs.TOOLS.setRelevantEnchantmentTypes(EnchantmentManaRecycler.TYPE);
    }
}
