package com.github.yaossg.mana_craft.recipe;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.github.yaossg.mana_craft.api.registry.ManaCraftRegistries.instance;

public class ManaCraftRecipes {
    static void addSmelting() {
        GameRegistry.addSmelting(ManaCraftBlocks.manaOre, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaIngotOre, new ItemStack(ManaCraftItems.manaIngot), 0.4f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaBlock, new ItemStack(ManaCraftItems.manaBall), 0.2f);
    }

    public static void init() {
        addSmelting();
        instance().getPath(ManaCraft.class, "/assets/mana_craft/machines/").ifPresent( root -> {
            instance().addRecipePath(ManaCraft.MODID, root.resolve("recipes"));
            instance().addFuelPath(ManaCraft.MODID, root.resolve("fuels"));
        });
    }
}
