package mana_craft.proxy;

import mana_craft.ManaCraft;
import mana_craft.block.BlockManaHead;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.entity.ManaCraftVillagers;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.item.ItemManaArmor;
import mana_craft.item.ItemManaTools;
import mana_craft.item.ManaCraftItems;
import mana_craft.loot.ManaCraftLoots;
import mana_craft.potion.ManaCraftPotionTypes;
import mana_craft.recipe.ManaCraftRecipes;
import mana_craft.subscriber.ManaCraftSubscribers;
import mana_craft.tile.TileManaBooster;
import mana_craft.tile.TileManaProducer;
import mana_craft.world.gen.ManaCraftWorldGens;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.inventory.IEnumGUIHandler;

import static mana_craft.block.ManaCraftBlocks.mana_glass;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        IEnumGUIHandler.register(ManaCraft.instance, ManaCraftGUIs.values());
        ManaCraftRecipes.init(event);
    }

    public static void addOre() {
        registerOre("dye", blue_shit);
        registerOre("dyeLightBlue", blue_shit);
        registerOre("blockGlass", mana_glass);
        registerOre("blockGlassHardened", mana_glass);
        registerOre("gemEmerald", mana_emerald);
        registerOre("record", mana_record);
    }

    public void init(FMLInitializationEvent event) {
        ManaCraftSubscribers.init();
        ManaCraftLoots.init();
        ManaCraftVillagers.init();
        ManaCraftWorldGens.init();
        ManaCraftPotionTypes.init();
        addOre();
        addSmelting(ManaCraftBlocks.mana_ore, new ItemStack(ManaCraftItems.mana, 4), 0.6f);
        addSmelting(ManaCraftBlocks.mana_ingot_ore, new ItemStack(ManaCraftItems.mana_ingot), 0.4f);
        addSmelting(ManaCraftBlocks.mana_block, new ItemStack(ManaCraftItems.mana_ball), 0.2f);
        BlockManaHead.init();
        SausageUtils.registerTileEntities(ManaCraft.MODID, TileManaProducer.class, TileManaBooster.class);
        TileManaProducer.init();
        ItemManaTools.MANA_TOOL.setRepairItem(new ItemStack(mana_ingot));
        ItemManaArmor.MANA_ARMOR.setRepairItem(new ItemStack(mana_ingot));
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManaCraftRecipes.loadAll();
        MinecraftForge.addGrassSeed(new ItemStack(mana), 1);
    }
}