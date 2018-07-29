package com.github.yaossg.mana_craft.recipe.jei;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

import static com.github.yaossg.mana_craft.inventory.GUIContainerManaBooster.texture;

public class MBFuelCategory implements IRecipeCategory<MBFuelWrapper> {
    public static final String UID = ManaCraft.MODID + ".mana_booster";
    private final IDrawable background, icon;
    public MBFuelCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(texture, 79, 31, 84, 36);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ManaCraftBlocks.manaBooster));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("gui.jei.category." + getUid());
    }

    @Override
    public String getModName() {
        return ManaCraft.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MBFuelWrapper mbFuelWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 16);
        guiItemStacks.set(ingredients);
    }
}
