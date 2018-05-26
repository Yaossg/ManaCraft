package yaossg.mod.mana_craft.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import yaossg.mod.mana_craft.config.Config;
import yaossg.mod.mana_craft.item.ManaCraftItems;
import yaossg.mod.sausage_core.api.util.BufferedRandom;

import java.util.Random;

public class ManaDropEvent {
    public static void init() {
        if(Config.dropManaChance >= 0)
            MinecraftForge.EVENT_BUS.register(ManaDropEvent.class);
    }
    private static final Random random = new Random();
    private static final BufferedRandom rb = BufferedRandom.boxed(random);
    private static void spawnEntityItem(LivingDropsEvent event, ItemStack stack) {
        Entity entity = event.getEntity();
        float fx = rb.nextFloat() * 0.5f + 0.25f + (float) entity.posX;
        float fy = rb.nextFloat() * 0.5f + 0.25f + (float) entity.posY;
        float fz = rb.nextFloat() * 0.5f + 0.25f + (float) entity.posZ;
        EntityItem item = new EntityItem(entity.world, fx, fy, fz, stack);
        item.motionX = rb.nextGaussianFloat() * 0.05;
        item.motionY = rb.nextGaussianFloat() * 0.05;
        item.motionZ = rb.nextGaussianFloat() * 0.05;
        event.getDrops().add(item);
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        int loot = event.getLootingLevel();
        if(rb.nextInt(256) + 3 * loot > Config.dropManaChance) {
            boolean piggy = Config.dropManaApple
                    && entity instanceof EntityPig
                    && rb.nextInt(16) / (loot + 1) < 2;
            spawnEntityItem(event, piggy ? new ItemStack(ManaCraftItems.itemManaApple)
                    : new ItemStack(ManaCraftItems.itemMana, (int) (1 + (rb.nextFloat() + 0.25) * loot)));
        }
    }
}
