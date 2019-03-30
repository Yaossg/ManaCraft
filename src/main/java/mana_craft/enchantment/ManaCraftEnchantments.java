package mana_craft.enchantment;

import mana_craft.ManaCraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import org.apache.commons.lang3.ArrayUtils;

import static net.minecraft.creativetab.CreativeTabs.COMBAT;
import static net.minecraft.creativetab.CreativeTabs.TOOLS;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftEnchantments {
	public static final Enchantment floating = null;
	public static final Enchantment mana_evoker = null;
	public static final Enchantment mana_recycler = null;

	public static void init() {
		TOOLS.setRelevantEnchantmentTypes(
				ArrayUtils.add(TOOLS.getRelevantEnchantmentTypes(), EnchantmentManaRecycler.TYPE)
		);
		COMBAT.setRelevantEnchantmentTypes(
				ArrayUtils.addAll(COMBAT.getRelevantEnchantmentTypes(), EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE));

		ManaCraft.tabMana.setRelevantEnchantmentTypes(
				EnchantmentManaRecycler.TYPE, EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE);
	}
}
