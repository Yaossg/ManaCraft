package com.github.yaossg.mana_craft.proxy;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.config.Config;
import com.github.yaossg.mana_craft.entity.ManaCraftEntities;
import com.github.yaossg.mana_craft.event.ManaCraftEvents;
import com.github.yaossg.mana_craft.inventory.ManaCraftGUIs;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.mana_craft.tile.ManaCraftTiles;
import com.github.yaossg.mana_craft.worldgen.ManaCraftWorldGens;
import com.github.yaossg.sausage_core.api.util.IGUIManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.init(event);
        ManaCraftItems.manager.registerAll();
        ManaCraftBlocks.manager.registerAll();
        ManaCraftTiles.init();
        IGUIManager.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftEntities.init();
        addSmelting();
        addOreDictionary();
    }
    public static void addSmelting() {
        GameRegistry.addSmelting(ManaCraftBlocks.manaOre, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaIngotOre, new ItemStack(ManaCraftItems.manaIngot), 0.4f);
        GameRegistry.addSmelting(ManaCraftBlocks.manaBlock, new ItemStack(ManaCraftItems.manaBall), 0.2f);
    }
    public static void addOreDictionary() {
        OreDictionary.registerOre("dyeLightBlue", ManaCraftItems.blueShit);
    }
    public void init(FMLInitializationEvent event)
    {
        ManaCraftEvents.init();
        ManaCraftWorldGens.init();
    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}