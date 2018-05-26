package yaossg.mod.mana_craft.api;

import com.google.common.collect.Comparators;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;

public abstract class ManaCraftAPIs {
    public static ManaCraftAPIs INSTANCE;
    static {
        try
        {
            Class<?> implClass = Class.forName("yaossg.mod.mana_craft.API");
            INSTANCE = (ManaCraftAPIs) implClass.getDeclaredField("INSTANCE").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot find implementation", e);
        }
    }

    public abstract SortedSet<Recipe> getRecipes();
    public abstract SortedSet<Fuel> getFuel();
	
	// note: even if you can override to create a new fuel, you should use 'of' anyway.
    public interface Fuel extends Comparable<Fuel> {
        Item getItem();
        int getBurnLevel();
        int getBurnTime();
        Comparator<Fuel> comparator = Comparator.comparing(fuel -> fuel.getItem().getRegistryName());
        @Override
        default int compareTo(Fuel o) {
            return comparator.compare(this, o);
        }

        static Fuel of(Item item, int level, int time) {
            return new Fuel() {
                @Override
                public Item getItem() {
                    return item;
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
                    return compareTo((Fuel) obj) == 0;
                }
            };
        }
    }
	
	// note: even if you can override to create a new recipe, you had better use 'of' anyway. (without the sort of input, it may not work)
    public interface Recipe extends Comparable<Recipe> {
        Comparator<ItemStack> comparatorInput =
                Comparator.<ItemStack, ResourceLocation>comparing(stack -> stack.getItem().getRegistryName())
                        .thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed());

        ItemStack[] getInput();
        ItemStack getOutput();
        int getWorkTime(); // tick

        Comparator<Recipe> comparator =
                Comparator.<Recipe, Iterable<ItemStack>>comparing(recipe -> Arrays.asList(recipe.getInput()), Comparators.lexicographical(comparatorInput))
                    .thenComparing(Recipe::getOutput, comparatorInput);

        @Override
        default int compareTo(Recipe o) {
            return comparator.compare(this, o);
        }

        static Recipe of(ItemStack ouput, int time, ItemStack... input) {
            Arrays.sort(input, comparatorInput);
            return new Recipe() {
                @Override
                public ItemStack[] getInput() {
                    return input;
                }

                @Override
                public ItemStack getOutput() {
                    return ouput;
                }

                @Override
                public int getWorkTime() {
                    return time;
                }
            };
        }
    }
}