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
import java.util.List;

import static com.github.yaossg.mana_craft.inventory.GUIContainerManaProducer.texture;

public class MPRecipeCategory implements IRecipeCategory<MPRecipeWrapper> {
    public static final String UID = ManaCraft.MODID + ".mana_producer";
    private static final int outputID = -1;
    private final IDrawable background, icon;
    public MPRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(texture, 46, 27, 85, 35);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ManaCraftBlocks.manaProducer));
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("gui.jei.category." + UID);
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
    public void setRecipe(IRecipeLayout recipeLayout, MPRecipeWrapper mpRecipeWrappers, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.init(outputID, false, 68, 8);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                group.init(j + i * 2, true, j * 18, i * 18);
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
        group.set(outputID, outputs.get(0));
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if(j + i * 2 < inputs.size())
                    group.set(j + i * 2, inputs.get(j + i * 2));
    }
}