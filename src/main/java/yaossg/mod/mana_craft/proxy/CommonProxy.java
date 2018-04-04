package yaossg.mod.mana_craft.proxy;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import yaossg.mod.mana_craft.Config;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.event.ManaCraftEvents;
import yaossg.mod.mana_craft.inventory.ManaCraftGUIs;
import yaossg.mod.mana_craft.item.ManaCraftItems;
import yaossg.mod.mana_craft.tile.ManaCraftTiles;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.init(event);
        ManaCraftItems.init();
        ManaCraftBlocks.init();
        ManaCraftTiles.init();
        ManaCraftGUIs.init();
        ManaCraftEvents.init();
        addSmelting();
    }
    public static void addSmelting() {
        GameRegistry.addSmelting(ManaCraftBlocks.blockManaOre, new ItemStack(ManaCraftItems.itemMana, 4), 0.6f);
        GameRegistry.addSmelting(ManaCraftBlocks.blockManaIngotOre, new ItemStack(ManaCraftItems.itemManaIngot), 0.4f);
    }
    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event)
    {

    }
}