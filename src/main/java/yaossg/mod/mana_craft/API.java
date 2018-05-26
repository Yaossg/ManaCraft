package yaossg.mod.mana_craft;

import yaossg.mod.mana_craft.api.ManaCraftAPIs;

import java.util.SortedSet;
import java.util.TreeSet;

public class API extends ManaCraftAPIs {
    public static final API INSTANCE = new API();

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
