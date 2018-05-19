package yaossg.mod.mana_craft.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    // note: even if their type are 'List',they should be considered as 'Set' actually
    public abstract List<Recipe> getRecipes();
    public abstract List<Fuel> getFuel();

    public interface Fuel {
        Item getItem();
        int getBurnLevel();
        int getBurnTime();
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
            };
        }
    }

    public interface Recipe {
        Comparator<ItemStack> comparator = Comparator.<ItemStack, ResourceLocation>comparing(stack -> stack.getItem().getRegistryName()).thenComparingInt(ItemStack::getCount);

        ItemStack[] getInput();
        ItemStack getOutput();
        int getWorkTime();
        static Recipe of(ItemStack ouput, int time, ItemStack... input) {
            Arrays.sort(input, comparator);
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