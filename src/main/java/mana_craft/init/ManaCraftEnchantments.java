package mana_craft.init;

import mana_craft.ManaCraft;
import mana_craft.enchantment.EnchantmentFloating;
import mana_craft.enchantment.EnchantmentManaEvoker;
import mana_craft.enchantment.EnchantmentManaRecycler;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import org.apache.commons.lang3.ArrayUtils;

import static net.minecraft.creativetab.CreativeTabs.COMBAT;
import static net.minecraft.creativetab.CreativeTabs.TOOLS;
import static sausage_core.api.util.common.SausageUtils.nonnull;

@ObjectHolder(ManaCraft.MODID)
public class ManaCraftEnchantments {
	public static final Enchantment floating = null;
	public static final Enchantment mana_evoker = null;
	public static final Enchantment mana_recycler = null;

	public static void init() {
		ForgeRegistries.ENCHANTMENTS.registerAll(new EnchantmentFloating(), new EnchantmentManaEvoker(), new EnchantmentManaRecycler());
		TOOLS.setRelevantEnchantmentTypes(
				ArrayUtils.add(TOOLS.getRelevantEnchantmentTypes(), EnchantmentManaRecycler.TYPE)
		);
		COMBAT.setRelevantEnchantmentTypes(
				ArrayUtils.addAll(COMBAT.getRelevantEnchantmentTypes(), EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE));

		nonnull(ManaCraft.IB.tab).setRelevantEnchantmentTypes(
				EnchantmentManaRecycler.TYPE, EnchantmentFloating.TYPE, EnchantmentManaEvoker.TYPE);
	}
}
