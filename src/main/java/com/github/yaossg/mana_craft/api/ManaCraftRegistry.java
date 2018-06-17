package com.github.yaossg.mana_craft.api;

import com.github.yaossg.sausage_core.api.util.Conversions;
import com.google.common.collect.Comparators;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

public interface ManaCraftRegistry {
    //register by add(), create by of()
    SortedSet<Recipe> recipes = new TreeSet<>();
    SortedSet<Fuel> fuels = new TreeSet<>();

    interface Fuel extends Comparable<Fuel>, Supplier<ItemStack> {
        @Override
        ItemStack get();
        int getBurnLevel();
        int getBurnTime();
        Comparator<Fuel> comparator = Comparator.comparing(fuel -> fuel.get().getItem().getRegistryName());
        @Override
        default int compareTo(Fuel o) {
            return comparator.compare(this, o);
        }

        static Fuel of(ItemStack item, int level, int time) {
            ItemStack stack = Conversions.To.stack(item, 1);
            return new Fuel() {
                @Override
                public ItemStack get() {
                    return stack;
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

    interface Recipe extends Comparable<Recipe> {
        ItemStack[] getInput();
        ItemStack getOutput();
        int getWorkTime(); // tick

        Comparator<ItemStack> comparator0 =
                Comparator.<ItemStack, ResourceLocation>comparing(stack -> stack.getItem().getRegistryName())
                        .thenComparing(Comparator.comparingInt(ItemStack::getCount).reversed());
        Comparator<Recipe> comparator =
                Comparator.<Recipe, Iterable<ItemStack>>comparing(recipe -> Arrays.asList(recipe.getInput()), Comparators.lexicographical(comparator0));

        @Override
        default int compareTo(Recipe o) {
            return comparator.compare(this, o);
        }

        static Recipe of(ItemStack ouput, int time, ItemStack... input) {
            Arrays.sort(input, comparator0);
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

                @Override
                public boolean equals(Object obj) {
                    if(this == obj) return true;
                    if(getClass() != obj.getClass()) return false;
                    return compareTo((Recipe) obj) == 0;
                }
            };
        }
    }
}