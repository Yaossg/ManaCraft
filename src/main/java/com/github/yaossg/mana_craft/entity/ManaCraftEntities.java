package com.github.yaossg.mana_craft.entity;

import com.github.yaossg.mana_craft.ManaCraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

import static net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler;
import static net.minecraftforge.fml.common.registry.EntityEntryBuilder.*;
import static net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES;

public class ManaCraftEntities {

    private static int nextID = 0;

    public static void init() {
        ENTITIES.register(create()
                .entity(EntityManaBall.class)
                .id(new ResourceLocation(ManaCraft.MODID, "mana_ball"), nextID++)
                .name("ManaBall").tracker(64, 8, true).build());
        ENTITIES.register(create()
                .entity(EntityManaBall.Floating.class)
                .id(new ResourceLocation(ManaCraft.MODID, "mana_floating_ball"), nextID++)
                .name("FloatingManaBall").tracker(64, 10, true).build());
        ENTITIES.register(create().entity(EntityManaShooter.class)
                .id(new ResourceLocation(ManaCraft.MODID, "mana_shooter"), nextID++)
                .name("ManaShooter").tracker(64, 3, true)
                .egg(Color.MAGENTA.getRGB(), Color.BLACK.brighter().getRGB()).build());
    }

    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        registerEntityRenderingHandler(EntityManaBall.class, EntityManaBall::render);
        registerEntityRenderingHandler(EntityManaBall.Floating.class, EntityManaBall::render);
        registerEntityRenderingHandler(EntityManaShooter.class, EntityManaShooter.Render::new);
    }
}
