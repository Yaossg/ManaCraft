package mana_craft.recipe.jei;

import mana_craft.api.registry.MBFuel;
import mana_craft.api.registry.MPRecipe;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.inventory.GUIContainerManaBooster;
import mana_craft.inventory.GUIContainerManaProducer;
import mana_craft.item.ManaCraftItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import static mana_craft.api.registry.IManaCraftRegistries.MB_FUELS;
import static mana_craft.api.registry.IManaCraftRegistries.MP_RECIPES;

@JEIPlugin
public class ManaCraftJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();
        registry.addRecipes(MP_RECIPES.view(), MPRecipeCategory.UID);
        registry.handleRecipes(MPRecipe.class, recipe -> new MPRecipeWrapper(guiHelper, recipe), MPRecipeCategory.UID);
        registry.addRecipeClickArea(GUIContainerManaProducer.class, 87, 36, 23, 18, MPRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ManaCraftBlocks.mana_producer), MPRecipeCategory.UID);

        registry.addRecipes(MB_FUELS.view(), MBFuelCategory.UID);
        registry.handleRecipes(MBFuel.class, fuel -> new MBFuelWrapper(guiHelper, fuel), MBFuelCategory.UID);
        registry.addRecipeClickArea(GUIContainerManaBooster.class, 52, 25, 16, 15, MBFuelCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ManaCraftBlocks.mana_booster), MBFuelCategory.UID);

        registry.addIngredientInfo(new ItemStack(ManaCraftBlocks.mana_obsidian), ItemStack.class, I18n.format("info.mana_craft.mana_obsidian"));

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();
        registry.addRecipeCategories(new MPRecipeCategory(guiHelper), new MBFuelCategory(guiHelper));
    }

}
