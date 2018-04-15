package yaossg.mod.mana_craft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yaossg.mod.mana_craft.ManaCraft;

public class ManaCraftEntities {

    private static int nextID = 0;
    public static void init() {
        registerEntity(EntityManaBall.class, "mana_ball", 64, 10, true);
    }
    @SideOnly(Side.CLIENT)
    public static void clientInit() {
        RenderingRegistry.registerEntityRenderingHandler(EntityManaBall.class, EntityManaBall.Render::new);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
                                       int updateFrequency, boolean sendsVelocityUpdates)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(ManaCraft.MODID + ":" + name), entityClass, name, nextID++, ManaCraft.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }
}
