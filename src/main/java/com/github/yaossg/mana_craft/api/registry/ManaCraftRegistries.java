package com.github.yaossg.mana_craft.api.registry;

import com.github.yaossg.mana_craft.api.IngredientStack;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Comparators;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.JsonContext;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * registries of ManaCraft
 * register or load {@link IMPRecipe} and {@link IMBFuel}
 * load when {@link net.minecraftforge.fml.common.event.FMLPostInitializationEvent} is fired
 * */
public abstract class ManaCraftRegistries {
    public interface IComparators {
        Comparator<Item> item =
                Comparator.comparing(Item::getRegistryName);
        Comparator<ItemStack> itemStack =
                Comparator.comparing(ItemStack::getItem, item)
                        .thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed());
        Comparator<Ingredient> ingredient =
                Comparator.<Ingredient, Iterable<ItemStack>>comparing(ingredient -> Arrays.asList(ingredient.getMatchingStacks()),
                        Comparators.lexicographical(itemStack));
        Comparator<IngredientStack> ingredientStack =
                Comparator.comparing(IngredientStack::getIngredient, ingredient)
                        .thenComparing(Comparator.comparingInt(IngredientStack::getCount).reversed());
    }

    static ManaCraftRegistries INSTANCE;
    public static ManaCraftRegistries instance() {
        return INSTANCE;
    }

    static {
        try {
            INSTANCE = (ManaCraftRegistries)
                    Class.forName("com.github.yaossg.mana_craft.ManaCraftRegistriesImpl")
                            .getDeclaredField("INSTANCE").get(null);
        } catch (Exception e) {
            throw new RuntimeException("Cannot find implementation", e);
        }
    }

    protected final SortedSet<IMPRecipe> recipes = new TreeSet<>();
    protected final SortedSet<IMBFuel> fuels = new TreeSet<>();

    public SortedSet<IMPRecipe> getRecipes() {
        return Collections.unmodifiableSortedSet(recipes);
    }

    public SortedSet<IMBFuel> getFuels() {
        return Collections.unmodifiableSortedSet(fuels);
    }

    protected void addRecipe(IMPRecipe recipe, Path path) {
        checkArgument(recipes.add(recipe), "WARNING: recipe \'%s\' is never loaded", path);
    }
    protected void addFuel(IMBFuel fuel, Path path) {
        checkArgument(fuels.add(fuel), "WARNING: fuel \'%s\' is never loaded", path);
    }

    protected Multimap<String, Path> pathRecipe = ArrayListMultimap.create();
    protected Multimap<String, Path> pathFuel = ArrayListMultimap.create();

    public void addRecipePath(String modid, Path path) {
        pathRecipe.put(modid, path);
    }
    public void addFuelPath(String modid, Path path) {
        pathFuel.put(modid, path);
    }

    public List<Path> walk(Path value) {
        try {
            return Files.walk(value)
                    .filter(path -> "json".equals(
                            FilenameUtils.getExtension(path.getFileName().toString())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public abstract Optional<Path> getPath(Class<?> clazz, String meta);

    public abstract <T> void loadEntries(String modid, List<Path> paths,
                                         BiFunction<JsonContext, JsonObject, T> parser, BiConsumer<T, Path> consumer);

}