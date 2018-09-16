package com.github.yaossg.mana_craft;

import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.mana_craft.proxy.CommonProxy;
import com.github.yaossg.sausage_core.api.util.common.SausageUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * @author Yaossg
 */
@Mod(modid = ManaCraft.MODID,
        name = ManaCraft.NAME,
        version = ManaCraft.VERSION,
        acceptedMinecraftVersions = "1.12.2",
        dependencies = "required-after:sausage_core@[0.1.10,)")
public class ManaCraft {
    public static final String MODID = "mana_craft";
    public static final String NAME = "ManaCraft";
    public static final String VERSION = "@version@";

    @SidedProxy(clientSide = "com.github.yaossg.mana_craft.proxy.ClientProxy",
            serverSide = "com.github.yaossg.mana_craft.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Instance(ManaCraft.MODID)
    public static ManaCraft instance;

    public static Logger logger;

    public static void giveAdvancement(Entity player, String advance) {
        SausageUtils.giveAdvancement(player, MODID, "mana", advance);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        SausageUtils.unstableWarning(logger = event.getModLog(), NAME, VERSION, MODID);
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
}