package mana_craft.api.registry;

import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import sausage_core.api.util.item.IngredientStack;

import javax.annotation.concurrent.Immutable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Immutable
public final class MPRecipe {
    final IngredientStack[] input;
    final ItemStack output;
    /**
     * work time, by ticks.
     * */
    public final int work_time;

    public IngredientStack[] input() {
        return input;
    }

    public ItemStack output() {
        return output.copy();
    }

    public MPRecipe(int work_time, ItemStack output, IngredientStack... input) {
        checkNotNull(this.output = output);
        checkNotNull(this.input = input);
        checkArgument(input.length > 0);
        checkArgument((this.work_time = work_time) > 0);

    }

    public static MPRecipe parse(JsonContext context, JsonObject json) {
        JsonArray input = JsonUtils.getJsonArray(json, "input");
        IngredientStack[] ingredients = Streams.stream(input)
                .map(json0 -> IngredientStack.parse(context, json0))
                .toArray(IngredientStack[]::new);
        ItemStack stack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "output"), context);
        return new MPRecipe(JsonUtils.getInt(json, "work_time"), stack, ingredients);
    }
}
