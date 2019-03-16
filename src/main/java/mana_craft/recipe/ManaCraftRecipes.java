package mana_craft.recipe;

import net.minecraft.item.ItemStack;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class ManaCraftRecipes {

    public static void addSmelt() {
        addSmelting(orichalcum_dust, new ItemStack(orichalcum_ingot), 0.05f);
        addSmelting(mana_ore, new ItemStack(mana, 4), 0.3f);
        addSmelting(orichalcum_ore, new ItemStack(orichalcum_ingot), 0.4f);
        addSmelting(mana_block, new ItemStack(mana_ball, 2), 0.2f);
    }
}
