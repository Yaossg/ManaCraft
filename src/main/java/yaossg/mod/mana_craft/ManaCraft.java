package yaossg.mod.mana_craft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yaossg.mod.mana_craft.item.ManaCraftItems;
import yaossg.mod.mana_craft.proxy.CommonProxy;
import yaossg.mod.mana_craft.util.Util;

@Mod(modid = ManaCraft.MODID, name = ManaCraft.NAME, version = ManaCraft.VERSION, acceptedMinecraftVersions = "1.12.2")
public class ManaCraft
{
    public static final String MODID = "mana_craft";
    public static final String NAME = "ManaCraft";
    public static final String VERSION = "0.1.3";

    @SidedProxy(clientSide = "yaossg.mod.mana_craft.proxy.ClientProxy",
            serverSide = "yaossg.mod.mana_craft.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(ManaCraft.MODID)
    public static ManaCraft instance;

    public static Logger logger;

    public static void giveAdvancement(Entity player, String advance) {
        Util.giveAdvancement(player, MODID + ":mana/" + advance);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info(NAME + " v" + VERSION + " is loading (modid:" + MODID + ")");
        logger.warn("The mod is still unstable and in early development, There are lots of bugs to fix and ideas to add");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }

    public static final CreativeTabs tabMana = new CreativeTabs(MODID + ".mana") {
        public ItemStack getTabIconItem() {
            return new ItemStack(ManaCraftItems.itemMana);
        }
    };
}