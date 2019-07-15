package mana_craft.api.registry;

import net.minecraft.util.ResourceLocation;
import sausage_core.api.registry.SCFRecipeManager;
import sausage_core.api.util.registry.IModdedRegistry;

public class ManaCraftRegistries {
	public static final IModdedRegistry<MPRecipe> MP_RECIPES =
			SCFRecipeManager.newType(
					new ResourceLocation("mana_craft", "mp_recipe"), MPRecipe.class,
					MPRecipe::parse, "1.0", "Recipes for Mana Producers"
			).registry;
	public static final IModdedRegistry<MBFuel> MB_FUELS =
			SCFRecipeManager.newType(
					new ResourceLocation("mana_craft", "mb_fuel"), MBFuel.class,
					MBFuel::parse, "1.0", "Fuels for Mana Boosters"
			).registry;
}