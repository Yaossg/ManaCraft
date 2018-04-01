package yaossg.mod.mana_craft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.event.ManaCraftEvents;
import yaossg.mod.mana_craft.inventory.ManaCraftGUIs;
import yaossg.mod.mana_craft.item.*;
import yaossg.mod.mana_craft.block.*;
import yaossg.mod.mana_craft.tile.ManaCraftTiles;

public class Loader {

    public static void init(FMLPreInitializationEvent event) {
        Config.init(event);
        ManaCraftItems.init();
        ManaCraftBlocks.init();
        ManaCraftTiles.init();
        ManaCraftGUIs.init();
        ManaCraftEvents.init();
    }
    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        ManaCraftItems.clientInit();
        ManaCraftBlocks.clientInit();
    }
    public static void addSmelting() {
        GameRegistry.addSmelting(ManaCraftBlocks.blockManaOre, new ItemStack(ManaCraftItems.itemMana, 4), 0.6f);
        GameRegistry.addSmelting(ManaCraftBlocks.blockManaIngotOre, new ItemStack(ManaCraftItems.itemManaIngot), 0.4f);
    }
}
