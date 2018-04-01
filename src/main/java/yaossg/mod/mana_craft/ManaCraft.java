package yaossg.mod.mana_craft;
import net.minecraft.creativetab.CreativeTabs;
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

@Mod(modid = ManaCraft.MODID, name = ManaCraft.NAME, version = ManaCraft.VERSION, acceptedMinecraftVersions = "1.12.2")
public class ManaCraft
{
    public static final String MODID = "mana_craft";
    public static final String NAME = "ManaCraft";
    public static final String VERSION = "1.0.0";

    @SidedProxy(clientSide = "yaossg.mod.mana_craft.proxy.ClientProxy",
            serverSide = "yaossg.mod.mana_craft.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(ManaCraft.MODID)
    public static ManaCraft instance;
    private Logger logger;
    public Logger getLogger() {
        return logger;
    }

    public static Logger LOGGER; // same as instance.getLogger()

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LOGGER = logger = event.getModLog();
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

    public static final CreativeTabs tabMana = new CreativeTabs("mana") {
        public ItemStack getTabIconItem() {
            return new ItemStack(ManaCraftItems.itemMana);
        }
    };
}