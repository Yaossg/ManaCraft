package com.github.yaossg.mana_craft.api.registry;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Yaossg
 * DO NOT implement this interface directly
 * use parse() or of() instead
 * */
@Immutable
public interface IMBFuel extends Comparable<IMBFuel>, Predicate<ItemStack>, Supplier<Ingredient> {
    /**
     * @return the fuel ingredient
     * please test with {@link IMBFuel#test(ItemStack)} instead of this
     * */
    @Override
    Ingredient get();

    int getBurnLevel();

    int getBurnTime();

    Comparator<IMBFuel> comparator = Comparator.comparing(IMBFuel::get, ManaCraftRegistries.comparatorIngredient);

    @Override
    default int compareTo(IMBFuel o) {
        return comparator.compare(this, o);
    }

    @Override
    default boolean test(ItemStack stack) {
        return get().apply(stack);
    }

    @Nonnull
    static IMBFuel of(@Nonnull Ingredient ingredient, int level, int time) {
        checkNotNull(ingredient);
        checkArgument(level > 0);
        checkArgument(time > 0);
        return new IMBFuel() {
            @Override
            public Ingredient get() {
                return ingredient;
            }

            @Override
            public int getBurnLevel() {
                return level;
            }

            @Override
            public int getBurnTime() {
                return time;
            }

            @Override
            public boolean equals(Object obj) {
                if(this == obj) return true;
                if(getClass() != obj.getClass()) return false;
                return compareTo((IMBFuel) obj) == 0;
            }
        };
    }

    @Nonnull
    static IMBFuel parse(JsonContext context, JsonObject json) {
        Ingredient ingredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "fuel"), context);
        return of(ingredient, JsonUtils.getInt(json, "level"), JsonUtils.getInt(json, "time"));
    }
}
