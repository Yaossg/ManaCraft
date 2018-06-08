package com.github.yaossg.mana_craft.tile;

import com.github.yaossg.mana_craft.APIsInstance;
import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

import static com.github.yaossg.mana_craft.api.ManaCraftAPIs.Fuel;
import static com.github.yaossg.mana_craft.api.ManaCraftAPIs.Recipe;
import static com.github.yaossg.sausage_core.api.util.Utils.registerTile;

public class ManaCraftTiles {
    public static void init() {
        registerTile(TileManaProducer.class, ManaCraft.MODID);
        APIsInstance.recipes.addAll(Arrays.asList(
                Recipe.of(new ItemStack(ManaCraftItems.mana, 5), 600,
                        new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.REDSTONE), new ItemStack(Items.GUNPOWDER), new ItemStack(Items.SUGAR)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.manaNugget, 3), 1500,
                        new ItemStack(Items.GOLD_NUGGET, 3), new ItemStack(ManaCraftItems.manaBall, 2)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.manaIngot), 12000,
                        new ItemStack(Items.GOLD_INGOT), new ItemStack(ManaCraftItems.manaBall, 6)
                ),
                Recipe.of(new ItemStack(ManaCraftBlocks.manaIngotBlock), 96000,
                        new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(ManaCraftItems.manaBall, 54)
                ),
                Recipe.of(new ItemStack(ManaCraftBlocks.manaGlass), 300,
                        new ItemStack(Blocks.GLASS), new ItemStack(ManaCraftItems.manaBall, 3), new ItemStack(ManaCraftItems.manaNugget, 3)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.manaCoal), 688,
                        new ItemStack(Items.COAL), new ItemStack(ManaCraftItems.manaBall, 8)
                ),
                Recipe.of(new ItemStack(ManaCraftItems.manaDiamond), 15750,
                        new ItemStack(Items.DIAMOND), new ItemStack(ManaCraftItems.manaCoal, 64)
                )
        ));
        registerTile(TileManaBooster.class, ManaCraft.MODID);
        APIsInstance.fuels.addAll(Arrays.asList(
                Fuel.of(new ItemStack(ManaCraftItems.mana), 2,200),
                Fuel.of(new ItemStack(ManaCraftBlocks.manaBlock), 10, 360),
                Fuel.of(new ItemStack(ManaCraftItems.manaBall), 11, 370),
                Fuel.of(new ItemStack(ManaCraftItems.manaApple), 88, 370),
                Fuel.of(new ItemStack(ManaCraftItems.manaNugget), 50, 400),
                Fuel.of(new ItemStack(ManaCraftItems.manaIngot), 400, 450),
                Fuel.of(new ItemStack(ManaCraftBlocks.manaIngotBlock), 1296, 1250),
                Fuel.of(new ItemStack(ManaCraftItems.manaCoal), 40, 800),
                Fuel.of(new ItemStack(ManaCraftItems.manaDiamond), 1440, 1600),
                Fuel.of(new ItemStack(ManaCraftItems.manaPork), 888, 42)
        ));
    }

}
