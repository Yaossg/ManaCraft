package com.github.yaossg.mana_craft.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.OptionalInt;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.*;

/**
 * {@link Ingredient} with a count
 * */
@Immutable
public final class IngredientStack implements Predicate<ItemStack> {
    private final Ingredient ingredient;
    private final int count;
    public IngredientStack(Ingredient ingredient) {
        this(ingredient, 1);
    }
    public IngredientStack(Ingredient ingredient, int count) {
        checkArgument(0 < count && count <= 64, "Invalid count (%s), it should be (0, 64]", count);
        this.ingredient = checkNotNull(ingredient);
        this.count = count;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }
    public int getCount() {
        return count;
    }

    @Override
    public boolean test(ItemStack input) {
        return count == input.getCount() && ingredient.apply(input);
    }
    private static OptionalInt seekCount(JsonContext context, JsonElement json) {
        OptionalInt count = OptionalInt.empty();
        if(json.isJsonArray()) {
            for (JsonElement element : json.getAsJsonArray()) {
                if(element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if("minecraft:empty".equals(context.appendModId(JsonUtils.getString(object, "type", "minecraft:item")))) {
                        if(object.has("count")) {
                            checkState(!count.isPresent(), "multi-defined count in one ingredient");
                            count = OptionalInt.of(object.getAsInt());
                        }
                    }
                }
            }
        }
        if(json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            if(object.has("count"))
                count = OptionalInt.of(JsonUtils.getInt(object, "count"));
        }
        return count;
    }

    public static IngredientStack parse(JsonContext context, JsonElement json) {
        return new IngredientStack(CraftingHelper.getIngredient(json, context), seekCount(context, json).orElse(1));
    }
}
