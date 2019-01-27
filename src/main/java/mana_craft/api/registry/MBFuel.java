package mana_craft.api.registry;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Fuel for Mana Boosters
 * */
@Immutable
public final class MBFuel implements Predicate<ItemStack>, Supplier<Ingredient> {
    final Ingredient ingredient;
    public final int level;
    public final int time;

    @Override
    @Nonnull
    public Ingredient get(){
        return ingredient;
    }

    @Override
    public boolean test(ItemStack stack) {
        return get().apply(stack);
    }

    public MBFuel(@Nonnull Ingredient ingredient, int level, int time) {
        this.ingredient = checkNotNull(ingredient);
        checkArgument((this.time = time) > 0);
        checkArgument(0 < level && level < 100);
        this.level = level;
    }

    public static MBFuel parse(JsonContext context, JsonObject json) {
        Ingredient ingredient = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "fuel"), context);
        return new MBFuel(ingredient, JsonUtils.getInt(json, "level"), JsonUtils.getInt(json, "time"));
    }
}
