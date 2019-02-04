package mana_craft.recipe.jei;

import mana_craft.api.registry.MBFuel;
import mana_craft.api.registry.MPRecipe;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.inventory.ContainerManaBooster;
import mana_craft.inventory.ContainerManaProducer;
import mana_craft.inventory.GUIContainerManaBooster;
import mana_craft.inventory.GUIContainerManaProducer;
import mana_craft.item.ManaCraftItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import static mana_craft.api.registry.ManaCraftRegistries.MB_FUELS;
import static mana_craft.api.registry.ManaCraftRegistries.MP_RECIPES;

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

        IRecipeTransferRegistry transfer = registry.getRecipeTransferRegistry();
        transfer.addRecipeTransferHandler(ContainerManaProducer.class, MPRecipeCategory.UID, 0, 4, 5, 36);
        transfer.addRecipeTransferHandler(ContainerManaBooster.class, MBFuelCategory.UID, 0, 1, 1, 36);

        registry.addIngredientInfo(new ItemStack(ManaCraftBlocks.mana_glass), ItemStack.class, I18n.format("info.mana_craft.mana_glass"));
        registry.addIngredientInfo(new ItemStack(ManaCraftBlocks.mana_obsidian), ItemStack.class, I18n.format("info.mana_craft.mana_obsidian"));

        registry.addIngredientInfo(new ItemStack(ManaCraftItems.mana_apple) , ItemStack.class, I18n.format("info.mana_craft.mana_apple"));
        registry.addIngredientInfo(new ItemStack(ManaCraftItems.mana_hoe) , ItemStack.class, I18n.format("info.mana_craft.mana_hoe"));
        registry.addIngredientInfo(new ItemStack(ManaCraftItems.mana_record) , ItemStack.class, I18n.format("info.mana_craft.mana_record"));

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers jeiHelper = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelper.getGuiHelper();
        registry.addRecipeCategories(new MPRecipeCategory(guiHelper), new MBFuelCategory(guiHelper));
    }

}
