package com.github.yaossg.mana_craft.api.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.JsonContext;

import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * registries of ManaCraft
 * register {@link IMPRecipe} and {@link IMBFuel} instance from their folder
 * load when {@link net.minecraftforge.fml.common.event.FMLPostInitializationEvent} is fired
 * */
public abstract class ManaCraftRegistries {
    private static final ManaCraftRegistries INSTANCE;
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

    public SortedSet<IMPRecipe> recipesView() {
        return Collections.unmodifiableSortedSet(recipes);
    }

    public SortedSet<IMBFuel> fuelsView() {
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

    public abstract Optional<Path> getPath(Class<?> clazz, String meta);

    public abstract <T> void loadEntries(String modid, List<Path> paths,
                                         BiFunction<JsonContext, JsonObject, T> parser, BiConsumer<T, Path> consumer);

}