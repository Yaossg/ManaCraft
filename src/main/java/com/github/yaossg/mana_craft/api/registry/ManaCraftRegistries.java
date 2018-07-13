package com.github.yaossg.mana_craft.api.registry;

import com.github.yaossg.mana_craft.api.IngredientStack;
import com.google.common.collect.Comparators;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.nio.file.Path;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * registries of ManaCraft
 * */
public interface ManaCraftRegistries {
    SortedSet<IMPRecipe> recipes = new TreeSet<>();
    SortedSet<IMBFuel> fuels = new TreeSet<>();
    List<Path> recipe2load = new ArrayList<>();
    List<Path> fuel2load = new ArrayList<>();
    //useful comparators
    Comparator<Item> comparatorItem = Comparator.comparing(Item::getRegistryName);
    Comparator<ItemStack> comparatorItemStack = Comparator.comparing(ItemStack::getItem, comparatorItem)
            .thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed());
    Comparator<Ingredient> comparatorIngredient = Comparator.<Ingredient, Iterable<ItemStack>>
            comparing(ingredient -> Arrays.asList(ingredient.getMatchingStacks()),
            Comparators.lexicographical(comparatorItemStack));
    Comparator<IngredientStack> comparatorIngredientStack =
            Comparator.comparing(IngredientStack::getIngredient, comparatorIngredient)
            .thenComparing(Comparator.comparingInt(IngredientStack::getCount).reversed());
    static void addRecipe(IMPRecipe recipe, String path) {
        checkArgument(recipes.add(recipe), "recipe at %s has already been added", path);
    }
    static void addFuel(IMBFuel fuel, String path) {
        checkArgument(fuels.add(fuel), "fuel at %s has already been added", path);
    }
}