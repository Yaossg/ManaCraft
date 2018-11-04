package mana_craft;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import mana_craft.api.registry.IManaCraftRegistries;
import mana_craft.item.ItemManaArmor;
import mana_craft.item.ItemManaTools;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

import javax.annotation.Nonnull;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkArgument;

public class ManaCraftRegistries implements IManaCraftRegistries {
    public static final ManaCraftRegistries INSTANCE = new ManaCraftRegistries();
    Multimap<String, Path> pathRecipe = ArrayListMultimap.create();
    Multimap<String, Path> pathFuel = ArrayListMultimap.create();
    public void addRecipePath(String modid, Path path) {
        pathRecipe.put(modid, path);
    }
    public void addFuelPath(String modid, Path path) {
        pathFuel.put(modid, path);
    }


    public Multimap<String, Path> recipePathView() {
        return Multimaps.unmodifiableMultimap(pathRecipe);
    }
    public Multimap<String, Path> fuelPathView() {
        return Multimaps.unmodifiableMultimap(pathFuel);
    }

    @Nonnull
    @Override
    public Item.ToolMaterial fetchToolMaterial() {
        return ItemManaTools.MANA_TOOL;
    }

    @Nonnull
    @Override
    public ItemArmor.ArmorMaterial fetchArmorMaterial() {
        return ItemManaArmor.MANA_ARMOR;
    }

}
