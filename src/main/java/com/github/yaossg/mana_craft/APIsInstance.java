package com.github.yaossg.mana_craft;

import com.github.yaossg.mana_craft.api.ManaCraftAPIs;

import java.util.SortedSet;
import java.util.TreeSet;

public class APIsInstance extends ManaCraftAPIs {
    public static final APIsInstance INSTANCE = new APIsInstance();

    public static final SortedSet<Recipe> recipes = new TreeSet<>();
    public static final SortedSet<Fuel> fuels = new TreeSet<>();

    @Override
    public SortedSet<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public SortedSet<Fuel> getFuel() {
        return fuels;
    }
}
