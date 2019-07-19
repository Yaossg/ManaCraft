package mana_craft;

import mana_craft.config.ManaCraftConfig;
import mana_craft.entity.EntityManaBall;
import mana_craft.entity.EntityManaShooter;
import mana_craft.init.ManaCraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.common.SausageUtils;
import sausage_core.api.util.registry.IBRegistryManager;

import static mana_craft.init.ManaCraftItems.mana;
import static net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;

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
		ManaCraftContent.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ManaCraftContent.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (ManaCraftConfig.grassMana)
			MinecraftForge.addGrassSeed(new ItemStack(mana), 1);
	}

	public static final IBRegistryManager IB = new IBRegistryManager(MODID, new CreativeTabs(MODID + ".mana") {
		public ItemStack getTabIconItem() {
			return new ItemStack(ManaCraftItems.mana);
		}
	});

	@SubscribeEvent
	public static void loadModels(ModelRegistryEvent event) {
		IB.loadAllModel();
		registerEntityRenderingHandler(EntityManaBall.class, EntityManaBall::render);
		registerEntityRenderingHandler(EntityManaBall.Floating.class, EntityManaBall::render);
		registerEntityRenderingHandler(EntityManaShooter.class, EntityManaShooter.Render::new);
	}

	@SubscribeEvent
	public static void onMissingMappings(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
			ResourceLocation name = entry.key;
			if (name.getResourceDomain().equals("mana_craft"))
				switch (name.getResourcePath()) {
					case "mana_emerald":
					case "orichalcum_gear":
					case "orichalcum_plate":
						entry.ignore();
				}
		}
	}
}