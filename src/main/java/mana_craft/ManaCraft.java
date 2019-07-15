package mana_craft;

import mana_craft.init.ManaCraftItems;
import mana_craft.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.registry.IBRegistryManager;

/**
 * @author Yaossg
 */
@Mod(modid = ManaCraft.MODID,
		name = ManaCraft.NAME,
		version = ManaCraft.VERSION,
		acceptedMinecraftVersions = "1.12.2",
		dependencies = "required-after:sausage_core@[1.5,);after:tconstruct")
@Mod.EventBusSubscriber
public class ManaCraft {
	public static final String MODID = "mana_craft";
	public static final String NAME = "ManaCraft";
	public static final String VERSION = "@version@";
	@SidedProxy(clientSide = "mana_craft.proxy.ClientProxy",
			serverSide = "mana_craft.proxy.CommonProxy")
	public static CommonProxy proxy;
	@Instance(MODID)
	public static ManaCraft instance;
	public static Logger logger;

	public static void giveAdvancement(Entity player, String advance) {
		SausageUtils.giveAdvancement(player, MODID, "mana", advance);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		SausageUtils.loadingInformation(NAME, VERSION, MODID);
		AutoSyncConfig.AUTO_SYNC_CONFIG.register(MODID);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static final CreativeTabs TAB = new CreativeTabs(MODID + ".mana") {
		public ItemStack getTabIconItem() {
			return new ItemStack(ManaCraftItems.mana);
		}
	};
	public static final IBRegistryManager IB = new IBRegistryManager(MODID, TAB);

	@SubscribeEvent
	public static void loadModels(ModelRegistryEvent event) {
		IB.loadAllModel();
	}

	@SubscribeEvent
	public static void onMissingMappings(RegistryEvent.MissingMappings<Item> event) {
		for(RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
			ResourceLocation name = entry.key;
			if(name.getResourceDomain().equals("mana_craft"))
				switch(name.getResourcePath()) {
					case "mana_emerald":
					case "orichalcum_gear":
					case "orichalcum_plate":
						entry.ignore();
				}
		}
	}
}