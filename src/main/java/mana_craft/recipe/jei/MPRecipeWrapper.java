package mana_craft.recipe.jei;

import mana_craft.api.registry.IMPRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sausage_core.api.util.common.IngredientStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static mana_craft.inventory.GUIContainerManaProducer.texture;

public class MPRecipeWrapper implements IRecipeWrapper {
    private final List<List<ItemStack>> input;
    private final ItemStack output;
    private final IDrawableAnimated arrow;

    MPRecipeWrapper(IGuiHelper guiHelper, IMPRecipe recipe) {
        IngredientStack[] ingredientStacks = recipe.input();
        input = Arrays.stream(ingredientStacks)
                .map(IngredientStack::getMatchingStacks)
                .map(Arrays::asList)
                .collect(Collectors.toList());
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
