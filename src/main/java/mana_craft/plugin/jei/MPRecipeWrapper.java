package mana_craft.plugin.jei;

import mana_craft.api.registry.MPRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sausage_core.api.util.item.IngredientStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static mana_craft.inventory.GUIContainerManaProducer.texture;

public class MPRecipeWrapper implements IRecipeWrapper {
	private final List<List<ItemStack>> input;
	private final ItemStack output;
	private final IDrawableAnimated arrow;
	final ResourceLocation location;

	MPRecipeWrapper(IGuiHelper guiHelper, MPRecipe recipe) {
		IngredientStack[] ingredientStacks = recipe.input();
		input = Arrays.stream(ingredientStacks)
				.map(IngredientStack::getMatchingStacks)
				.map(Arrays::asList)
				.collect(Collectors.toList());
		output = recipe.output();
		arrow = guiHelper.drawableBuilder(texture, 176, 0, 24, 17)
				.buildAnimated((int) Math.sqrt(recipe.work_time), IDrawableAnimated.StartDirection.LEFT, false);
		location = recipe.getLocation();
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
