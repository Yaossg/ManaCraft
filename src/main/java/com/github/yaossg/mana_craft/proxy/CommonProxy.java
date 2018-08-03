package com.github.yaossg.mana_craft.proxy;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.ManaCraftRegistriesImpl;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.enchantment.ManaCraftEnchantments;
import com.github.yaossg.mana_craft.entity.ManaCraftEntities;
import com.github.yaossg.mana_craft.entity.ManaCraftVillage;
import com.github.yaossg.mana_craft.event.ManaCraftEvents;
import com.github.yaossg.mana_craft.inventory.ManaCraftGUIs;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.mana_craft.recipe.ManaCraftRecipes;
import com.github.yaossg.mana_craft.tile.ManaCraftTiles;
import com.github.yaossg.mana_craft.world.biome.ManaCraftBiomes;
import com.github.yaossg.mana_craft.world.gen.ManaCraftWorldGens;
import com.github.yaossg.sausage_core.api.util.inventory.IEnumGUIBase;
import com.github.yaossg.sausage_core.api.util.inventory.IGUIManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ManaCraftItems.manager.registerAll();
        ManaCraftBlocks.init();
        ManaCraftTiles.init();
        IEnumGUIBase.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftEntities.init();
        ManaCraftEnchantments.init();
        ManaCraftRecipes.init();
        addOreDictionary();

    }

    public static void addOreDictionary() {
        registerOre("dyeLightBlue", ManaCraftItems.blueShit);
        registerOre("blockGlass", ManaCraftBlocks.manaGlass);
        registerOre("blockGlassHardened", ManaCraftBlocks.manaGlass);
        registerOre("gemEmerald", ManaCraftItems.manaEmerald);
    }

    public void init(FMLInitializationEvent event) {
        ManaCraftEvents.init();
        ManaCraftBiomes.init();
        ManaCraftVillage.init();
        ManaCraftWorldGens.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManaCraftRegistriesImpl.INSTANCE.loadAll();
        MinecraftForge.addGrassSeed(new ItemStack(ManaCraftItems.mana), 1);
    }
}