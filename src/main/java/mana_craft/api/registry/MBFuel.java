package mana_craft.api.registry;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import sausage_core.api.registry.RecipeLocatable;
import sausage_core.api.util.common.JsonRecipeHelper;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuel for Mana Boosters
 */
public final class MBFuel extends RecipeLocatable implements Predicate<ItemStack>, Supplier<Ingredient> {
	final Ingredient ingredient;
	public final int level;
	public final int time;

	@Override
	public Ingredient get() {
		return ingredient;
	}

	@Override
	public boolean test(ItemStack stack) {
		return get().apply(stack);
	}

	public MBFuel(Ingredient ingredient, int level, int time) {
		this.ingredient = checkNotNull(ingredient);
		checkArgument((this.time = time) > 0);
		checkArgument(0 < level && level < 100);
		this.level = level;
	}

	public static MBFuel parse(JsonObject json) {
		Ingredient ingredient = JsonRecipeHelper.getIngredient(JsonUtils.getJsonObject(json, "fuel"));
		return new MBFuel(ingredient, JsonUtils.getInt(json, "level"), JsonUtils.getInt(json, "time"));
	}
}
