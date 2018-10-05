package mana_craft.proxy;

import mana_craft.ManaCraft;
import mana_craft.ManaCraftRegistriesImpl;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.entity.ManaCraftEntities;
import mana_craft.entity.ManaCraftVillage;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.item.ManaCraftItems;
import mana_craft.loot.ManaCraftLoots;
import mana_craft.potion.ManaCraftPotions;
import mana_craft.recipe.ManaCraftRecipes;
import mana_craft.sound.ManaCraftSounds;
import mana_craft.subscriber.ManaCraftSubscribers;
import mana_craft.tile.ManaCraftTiles;
import mana_craft.world.biome.ManaCraftBiomes;
import mana_craft.world.gen.ManaCraftWorldGens;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sausage_core.api.util.inventory.IEnumGUIHandler;

import static mana_craft.block.ManaCraftBlocks.manaGlass;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ManaCraftSounds.init();
        ManaCraftItems.manager.registerAll();
        ManaCraftBlocks.init();
        ManaCraftTiles.init();
        IEnumGUIHandler.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftEntities.init();
        ManaCraftEnchantments.init();
        ManaCraftPotions.init();
        ManaCraftRecipes.init(event);
        addOre();
    }

    public static void addOre() {
        registerOre("dye", blueShit);
        registerOre("dyeLightBlue", blueShit);
        registerOre("blockGlass", manaGlass);
        registerOre("blockGlassHardened", manaGlass);
        registerOre("gemEmerald", manaEmerald);
        registerOre("record", manaRecord);
    }

    public void init(FMLInitializationEvent event) {
        ManaCraftSubscribers.init();
        ManaCraftLoots.init();
        ManaCraftBiomes.init();
        ManaCraftVillage.init();
        ManaCraftWorldGens.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManaCraftRegistriesImpl.INSTANCE.loadAll();
        MinecraftForge.addGrassSeed(new ItemStack(mana), 1);
    }
}