package mana_craft;

import mana_craft.item.ManaCraftItems;
import mana_craft.proxy.CommonProxy;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;
import sausage_core.api.common.SausageCoreAPI;
import sausage_core.api.registry.AutoSyncConfig;
import sausage_core.api.util.common.SausageUtils;

/**
 * @author Yaossg
 */
@Mod(modid = ManaCraft.MODID,
		name = ManaCraft.NAME,
		version = ManaCraft.VERSION,
		acceptedMinecraftVersions = "1.12.2",
		dependencies = "required-after:sausage_core@[1.4.1,);after:tconstruct")
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

	private static final int ACCEPTED_INTERNAL_VERSION = 1;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		if(ACCEPTED_INTERNAL_VERSION != SausageCoreAPI.INTERNAL_VERSION())
			fail();
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

	public static final CreativeTabs tabMana = new CreativeTabs(MODID + ".mana") {
		public ItemStack getTabIconItem() {
			return new ItemStack(ManaCraftItems.mana);
		}
	};
	private void fail() {
		String message = String.format("ManaCraft requires SausageCore with internal version %s, but got %s. " +
				"please visit https://github.com/Yaossg/tutorial/blob/master/mana_craft/version.md for more information",
				ACCEPTED_INTERNAL_VERSION, SausageCoreAPI.INTERNAL_VERSION());
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			show(message);
		else {
			logger.fatal("============================================================================");
			logger.fatal("****************************************************************************");
			logger.fatal(message);
			logger.fatal("****************************************************************************");
			logger.fatal("============================================================================");
			FMLCommonHandler.instance().exitJava(-1, false);
		}
	}

	@SideOnly(Side.CLIENT)
	private void show(String message) {
		String[] split = message.split("\\. ");
		throw new CustomModLoadingErrorDisplayException() {
			@Override
			public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {

			}

			@Override
			public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
				drawCenteredString(fontRenderer, split[0], errorScreen.width / 2, errorScreen.height / 2 - 25, 0xFFFFFF);
				drawCenteredString(fontRenderer, split[1], errorScreen.width / 2, errorScreen.height / 2 - 5, 0xFFFFFF);
			}

			public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
				fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
			}
		};

	}
}