package mana_craft.api.registry;

import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import sausage_core.api.util.registry.SausageRegistry;

import javax.annotation.Nonnull;
import java.nio.file.Path;

import static mana_craft.api.registry.IManaCraftRegistries.Helper.INSTANCE;

public interface IManaCraftRegistries {
    class Helper {
        static IManaCraftRegistries INSTANCE;
    }
    static IManaCraftRegistries instance() {
        if(INSTANCE == null) {
            try {
                INSTANCE = (IManaCraftRegistries)
                        Class.forName("mana_craft.ManaCraftRegistries")
                                .getDeclaredField("INSTANCE").get(null);
            } catch (Exception e) {
                throw new RuntimeException("Cannot find implementation", e);
            }

        }
        return INSTANCE;
    }

    SausageRegistry<MPRecipe> MP_RECIPES = new SausageRegistry<>(MPRecipe.class);
    SausageRegistry<MBFuel> MB_FUELS = new SausageRegistry<>(MBFuel.class);

    void addRecipePath(String modid, Path path);
    void addFuelPath(String modid, Path path);

    Multimap<String, Path> recipePathView();
    Multimap<String, Path> fuelPathView();

    @Nonnull
    Item.ToolMaterial fetchToolMaterial();
    @Nonnull
    ItemArmor.ArmorMaterial fetchArmorMaterial();

}