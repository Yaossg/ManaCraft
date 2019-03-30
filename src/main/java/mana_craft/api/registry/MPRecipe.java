package mana_craft.api.registry;

import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import sausage_core.api.registry.RecipeLocatable;
import sausage_core.api.util.common.JsonRecipeHelper;
import sausage_core.api.util.item.IngredientStack;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Recipe for Mana Producers
 */
public final class MPRecipe extends RecipeLocatable {
	final IngredientStack[] input;
	final ItemStack output;
	/**
	 * work time, by ticks.
	 */
	public final int work_time;

	public IngredientStack[] input() {
		return input;
	}

	public ItemStack output() {
		return output.copy();
	}

	public MPRecipe(int work_time, ItemStack output, IngredientStack... input) {
		this.output = checkNotNull(output);
		this.input = checkNotNull(input);
		checkArgument(input.length > 0);
		checkArgument((this.work_time = work_time) > 0);
	}

	public static MPRecipe parse(JsonObject json) {
		JsonArray input = JsonUtils.getJsonArray(json, "input");
		IngredientStack[] ingredients = Streams.stream(input)
				.map(IngredientStack::parse)
				.toArray(IngredientStack[]::new);
		ItemStack stack = JsonRecipeHelper.getItemStack(JsonUtils.getJsonObject(json, "output"));
		return new MPRecipe(JsonUtils.getInt(json, "work_time"), stack, ingredients);
	}
}
