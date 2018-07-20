package com.github.yaossg.mana_craft.proxy;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.ManaCraftRegistriesImpl;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.enchantment.ManaCraftEnchantments;
import com.github.yaossg.mana_craft.entity.ManaCraftEntities;
import com.github.yaossg.mana_craft.event.ManaCraftEvents;
import com.github.yaossg.mana_craft.inventory.ManaCraftGUIs;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.mana_craft.recipe.ManaCraftRecipes;
import com.github.yaossg.mana_craft.tile.ManaCraftTiles;
import com.github.yaossg.mana_craft.village.ManaCraftTrade;
import com.github.yaossg.mana_craft.worldgen.ManaCraftWorldGens;
import com.github.yaossg.sausage_core.api.util.inventory.IGUIManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ManaCraftItems.manager.registerAll();
        ManaCraftBlocks.manager.registerAll();
        ManaCraftTiles.init();
        IGUIManager.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftEntities.init();
        ManaCraftEnchantments.init();
        ManaCraftRecipes.init();
        addOreDictionary();
    }

    public static void addOreDictionary() {
        OreDictionary.registerOre("dyeLightBlue", ManaCraftItems.blueShit);
        OreDictionary.registerOre("blockGlass", ManaCraftBlocks.manaGlass);
        OreDictionary.registerOre("blockGlassHardened", ManaCraftBlocks.manaGlass);
        OreDictionary.registerOre("gemEmerald", ManaCraftItems.manaEmerald);
    }

    public void init(FMLInitializationEvent event) {
        ManaCraftEvents.init();
        ManaCraftWorldGens.init();
        ManaCraftTrade.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManaCraftRegistriesImpl.INSTANCE.loadAll();
        MinecraftForge.addGrassSeed(new ItemStack(ManaCraftItems.mana), 1);
    }
}