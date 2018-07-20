package com.github.yaossg.mana_craft.enchantment;

import com.github.yaossg.mana_craft.ManaCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import static com.google.common.collect.ObjectArrays.concat;
import static net.minecraft.creativetab.CreativeTabs.COMBAT;
import static net.minecraft.creativetab.CreativeTabs.TOOLS;

public class ManaCraftEnchantments {
    public static final Enchantment floating = new EnchantmentFloating();
    public static final Enchantment manaEvoker = new EnchantmentManaEvoker();
    public static final Enchantment manaRecycler = new EnchantmentManaRecycler();
    public static void init() {
        ForgeRegistries.ENCHANTMENTS.register(floating);
        ForgeRegistries.ENCHANTMENTS.register(manaEvoker);
        ForgeRegistries.ENCHANTMENTS.register(manaRecycler);

        //add into CreativeTabs
        TOOLS.setRelevantEnchantmentTypes(
                concat(TOOLS.getRelevantEnchantmentTypes(),
                        EnchantmentManaRecycler.TYPE)
        );
        COMBAT.setRelevantEnchantmentTypes(
                concat(concat(COMBAT.getRelevantEnchantmentTypes(),
                        EnchantmentFloating.TYPE), EnchantmentManaEvoker.TYPE));
        ManaCraft.tabMana.setRelevantEnchantmentTypes(
                EnchantmentManaRecycler.TYPE, EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE);
    }
}
