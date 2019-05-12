package mana_craft.proxy;

import mana_craft.ManaCraft;
import mana_craft.api.registry.ManaCraftRegistries;
import mana_craft.block.BlockManaHead;
import mana_craft.config.ManaCraftConfig;
import mana_craft.entity.ManaCraftVillagers;
import mana_craft.inventory.ManaCraftGUIs;
import mana_craft.item.ItemManaArmor;
import mana_craft.item.ItemManaTools;
import mana_craft.loot.ManaCraftLoots;
import mana_craft.potion.ManaCraftPotions;
import mana_craft.subscriber.ManaCraftSubscribers;
import mana_craft.tile.TileManaBooster;
import mana_craft.tile.TileManaProducer;
import mana_craft.world.biome.ManaCraftBiomes;
import mana_craft.world.gen.ManaCraftWorldGens;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sausage_core.api.core.ienum.IEnumGUIHandler;
import sausage_core.api.core.plugin.PluginLoader;
import sausage_core.api.util.common.SausageUtils;

import static mana_craft.block.ManaCraftBlocks.*;
import static mana_craft.item.ManaCraftItems.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class CommonProxy {
	static final PluginLoader pluginPrimer = new PluginLoader(ManaCraft.MODID);

	public void preInit(FMLPreInitializationEvent event) {
		IEnumGUIHandler.register(ManaCraft.instance, ManaCraftGUIs.values());
		ManaCraftRegistries.init();
		ManaCraftPotions.preInit();
		ManaCraftVillagers.preInit();
		if(ManaCraftConfig.ticPlugin)
			pluginPrimer.register("tconstruct", "mana_craft.plugin.PluginTConstruct");
	}

	static void addOre() {
		registerOre("dye", blue_shit);
		registerOre("oreOrichalcum", orichalcum_ore);
		registerOre("blockOrichalcum", orichalcum_block);
		registerOre("ingotOrichalcum", orichalcum_ingot);
		registerOre("dustOrichalcum", orichalcum_dust);
		registerOre("plateOrichalcum", orichalcum_plate);
		registerOre("gearOrichalcum", orichalcum_gear);
		registerOre("nuggetOrichalcum", orichalcum_nugget);
		registerOre("dyeLightBlue", blue_shit);
		registerOre("blockGlass", mana_glass);
		registerOre("blockGlassHardened", mana_glass);
		registerOre("record", mana_record);
	}

	static void misc() {
		addSmelting(orichalcum_dust, new ItemStack(orichalcum_ingot), 0.05f);
		addSmelting(mana_ore, new ItemStack(mana, 4), 0.3f);
		addSmelting(orichalcum_ore, new ItemStack(orichalcum_ingot), 0.4f);
		addSmelting(mana_block, new ItemStack(mana_ball, 2), 0.2f);
		BlockManaHead.init();

		ItemManaTools.MANA_TOOL.setRepairItem(new ItemStack(orichalcum_ingot));
		ItemManaArmor.MANA_ARMOR.setRepairItem(new ItemStack(orichalcum_ingot));

		SausageUtils.registerTileEntities(ManaCraft.MODID, TileManaProducer.class, TileManaBooster.class);
		TileManaProducer.init();
		pluginPrimer.execute();
	}

	public void init(FMLInitializationEvent event) {
		ManaCraftSubscribers.init();
		if(ManaCraftConfig.loot)
			ManaCraftLoots.init();
		ManaCraftVillagers.init();
		ManaCraftWorldGens.init();
		ManaCraftPotions.init();
		ManaCraftBiomes.init();
		addOre();
		misc();
	}

	public void postInit(FMLPostInitializationEvent event) {
		if(ManaCraftConfig.grassMana)
			MinecraftForge.addGrassSeed(new ItemStack(mana), 1);
	}
}