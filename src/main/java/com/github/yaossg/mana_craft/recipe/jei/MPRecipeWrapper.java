package com.github.yaossg.mana_craft.recipe.jei;

import com.github.yaossg.mana_craft.api.IngredientStack;
import com.github.yaossg.mana_craft.api.registry.IMPRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.yaossg.mana_craft.inventory.GUIContainerManaProducer.texture;

public class MPRecipeWrapper implements IRecipeWrapper {
    private final List<List<ItemStack>> input;
    private final ItemStack output;
    private final IDrawableAnimated arrow;

    MPRecipeWrapper(IGuiHelper guiHelper, IMPRecipe recipe) {
        IngredientStack[] ingredientStacks = recipe.input();
        ItemStack[][] itemStacks = new ItemStack[ingredientStacks.length][];
        for (int i = 0; i < ingredientStacks.length; i++) {
            IngredientStack ingredientStack = ingredientStacks[i];
            itemStacks[i] = ingredientStack.getIngredient().getMatchingStacks();
            for (int j = 0; j < itemStacks[i].length; j++)
                itemStacks[i][j].setCount(ingredientStack.getCount());
        }
        input = Arrays.stream(itemStacks).map(Arrays::asList).collect(Collectors.toList());
        output = recipe.output();
        arrow = guiHelper.drawableBuilder(texture, 176, 0, 24, 17)
                .buildAnimated(recipe.work_time(), IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, input);
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        arrow.draw(minecraft, 41, 10);
    }
}
