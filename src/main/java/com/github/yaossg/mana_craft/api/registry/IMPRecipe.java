package com.github.yaossg.mana_craft.api.registry;

import com.github.yaossg.mana_craft.api.IngredientStack;
import com.google.common.collect.Comparators;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Comparator;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DO NOT implement this interface directly
 * use parse() or of() instead
 * */
@Immutable
public interface IMPRecipe extends Comparable<IMPRecipe> {
    IngredientStack[] input();

    ItemStack output();

    /**
     * @return work time, by ticks.
     * */
    int work_time();

    Comparator<IMPRecipe> comparator =
            Comparator.<IMPRecipe, Iterable<IngredientStack>>comparing(recipe -> Arrays.asList(recipe.input()),
                    Comparators.lexicographical(ManaCraftRegistries.IComparators.ingredientStack));

    @Override
    default int compareTo(IMPRecipe o) {
        return comparator.compare(this, o);
    }

    @Nonnull
    static IMPRecipe of(int work_time, @Nonnull ItemStack output, IngredientStack... input) {
        checkNotNull(output);
        checkNotNull(input);
        checkArgument(input.length > 0);
        checkArgument(work_time > 0);
        Arrays.sort(input, ManaCraftRegistries.IComparators.ingredientStack);
        return new IMPRecipe() {
            @Override
            public IngredientStack[] input() {
                return input;
            }

            @Override
            public ItemStack output() {
                return output;
            }

            @Override
            public int work_time() {
                return work_time;
            }

            @Override
            public boolean equals(Object obj) {
                if(this == obj) return true;
                if(getClass() != obj.getClass()) return false;
                return compareTo((IMPRecipe) obj) == 0;
            }
        };
    }

    @Nonnull
    static IMPRecipe parse(JsonContext context, JsonObject json) {
        JsonArray input = JsonUtils.getJsonArray(json, "input");
        IngredientStack[] ingredients = Streams.stream(input)
                .map(json0 -> IngredientStack.parse(context, json0))
                .toArray(IngredientStack[]::new);
        ItemStack stack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "output"), context);
        return of(JsonUtils.getInt(json, "work_time"), stack, ingredients);
    }
}
