package mana_craft.api.registry;

import mana_craft.ManaCraft;
import net.minecraft.util.ResourceLocation;
import sausage_core.api.registry.SCFRecipeManager;
import sausage_core.api.util.registry.IModdedRegistry;
import sausage_core.api.util.registry.SimpleRegistry;

public class ManaCraftRegistries {
    public static final IModdedRegistry<MPRecipe> MP_RECIPES =
            SCFRecipeManager.newType(
                    new ResourceLocation(ManaCraft.MODID, "mp_recipe"), MPRecipe.class,
                    MPRecipe::parse, "1.0", "Recipes for Mana Producers"
            ).registry;
    public static final IModdedRegistry<MBFuel> MB_FUELS =
            SCFRecipeManager.newType(
                    new ResourceLocation(ManaCraft.MODID, "mb_fuel"), MBFuel.class,
                    MBFuel::parse, "1.0", "Fuels for Mana Boosters"
            ).registry;
    public static final IModdedRegistry<ManaBoostItem> BOOST_ITEM = new SimpleRegistry<>(ManaBoostItem.class);
}