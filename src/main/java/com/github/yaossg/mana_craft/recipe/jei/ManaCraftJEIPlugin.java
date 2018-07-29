package com.github.yaossg.mana_craft.recipe.jei;

import com.github.yaossg.mana_craft.api.registry.IMBFuel;
import com.github.yaossg.mana_craft.api.registry.IMPRecipe;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.inventory.GUIContainerManaBooster;
import com.github.yaossg.mana_craft.inventory.GUIContainerManaProducer;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.instance;

@SuppressWarnings("unused")
@JEIPlugin
public class ManaCraftJEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();
        registry.addRecipes(instance().recipesView(), MPRecipeCategory.UID);
        registry.handleRecipes(IMPRecipe.class, recipe -> new MPRecipeWrapper(guiHelper, recipe), MPRecipeCategory.UID);
        registry.addRecipeClickArea(GUIContainerManaProducer.class, 87, 36, 23, 18, MPRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ManaCraftBlocks.manaProducer), MPRecipeCategory.UID);

        registry.addRecipes(instance().fuelsView(), MBFuelCategory.UID);
        registry.handleRecipes(IMBFuel.class, fuel -> new MBFuelWrapper(guiHelper, fuel), MBFuelCategory.UID);
        registry.addRecipeClickArea(GUIContainerManaBooster.class, 79, 31, 16, 15, MBFuelCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ManaCraftBlocks.manaBooster), MBFuelCategory.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();
        registry.addRecipeCategories(new MPRecipeCategory(guiHelper), new MBFuelCategory(guiHelper));
    }

}
