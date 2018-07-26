package com.github.yaossg.mana_craft.entity;

import com.github.yaossg.mana_craft.ManaCraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

import static net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;

public class ManaCraftEntities {

    private static int nextID = 0;

    public static void init() {
        registerEntity(EntityManaBall.class, "mana_ball", 64, 8, true);
        registerEntity(EntityManaBall.Floating.class, "mana_floating_ball", 64, 10, true);
        registerEntity(EntityManaShooter.class, "mana_shooter", 64, 3, true);
        EntityRegistry.registerEgg(new ResourceLocation(ManaCraft.MODID, "mana_shooter"),
                Color.MAGENTA.getRGB(), Color.BLACK.brighter().getRGB());
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        registerEntityRenderingHandler(EntityManaBall.class, EntityManaBall::render);
        registerEntityRenderingHandler(EntityManaBall.Floating.class, EntityManaBall::render);
        registerEntityRenderingHandler(EntityManaShooter.class, EntityManaShooter.Render::new);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(new ResourceLocation(ManaCraft.MODID, name), entityClass, name, nextID++, ManaCraft.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}
