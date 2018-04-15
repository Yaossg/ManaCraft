package yaossg.mod.mana_craft.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yaossg.mod.mana_craft.block.ManaCraftBlocks;
import yaossg.mod.mana_craft.entity.ManaCraftEntities;
import yaossg.mod.mana_craft.item.ManaCraftItems;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        ManaCraftItems.clientInit();
        ManaCraftBlocks.clientInit();
        ManaCraftEntities.clientInit();
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}