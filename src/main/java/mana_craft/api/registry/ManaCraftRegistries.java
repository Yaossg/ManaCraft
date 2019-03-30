package mana_craft.api.registry;

import net.minecraft.util.ResourceLocation;
import sausage_core.api.registry.SCFRecipeManager;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.registry.IModdedRegistry;

public class ManaCraftRegistries {
	public static final String MODID = "mana_craft";
	public static final IModdedRegistry<MPRecipe> MP_RECIPES =
			SCFRecipeManager.newType(
					new ResourceLocation(MODID, "mp_recipe"), MPRecipe.class,
					MPRecipe::parse, "1.0", "Recipes for Mana Producers"
			).registry;
	public static final IModdedRegistry<MBFuel> MB_FUELS =
			SCFRecipeManager.newType(
					new ResourceLocation(MODID, "mb_fuel"), MBFuel.class,
					MBFuel::parse, "1.0", "Fuels for Mana Boosters"
			).registry;

	/**
	 * INTERNAL USE
	 */
	public static void init() {
		SausageUtils.getPath(ManaCraftRegistries.class, "/assets/mana_craft/defaults/").ifPresent(root -> {
			SCFRecipeManager.where(new ResourceLocation(MODID, "mp_recipe")).addDefaults(MODID, root.resolve("recipes"));
			SCFRecipeManager.where(new ResourceLocation(MODID, "mb_fuel")).addDefaults(MODID, root.resolve("fuels"));
		});
	}
}