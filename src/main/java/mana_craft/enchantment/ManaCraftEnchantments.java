package mana_craft.enchantment;

import mana_craft.ManaCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.lang3.ArrayUtils;

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
                ArrayUtils.add(TOOLS.getRelevantEnchantmentTypes(), EnchantmentManaRecycler.TYPE)
        );
        COMBAT.setRelevantEnchantmentTypes(
                ArrayUtils.addAll(COMBAT.getRelevantEnchantmentTypes(), EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE));

        //add another way to get these books
        ManaCraft.tabMana.setRelevantEnchantmentTypes(
                EnchantmentManaRecycler.TYPE, EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE);
    }
}
