package mana_craft.proxy;

import mana_craft.entity.EntityManaBall;
import mana_craft.entity.EntityManaShooter;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;

public class ClientProxy extends CommonProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		registerEntityRenderingHandler(EntityManaBall.class, EntityManaBall::render);
		registerEntityRenderingHandler(EntityManaBall.Floating.class, EntityManaBall::render);
		registerEntityRenderingHandler(EntityManaShooter.class, EntityManaShooter.Render::new);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
}