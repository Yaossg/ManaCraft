package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.config.Config;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.sausage_core.api.util.BufferedRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ManaDropEvent {

    private static final BufferedRandom random = new BufferedRandom();
    private static void spawnEntityItem(LivingDropsEvent event, ItemStack stack) {
        Entity entity = event.getEntity();
        float fx = random.nextFloat() * 0.5f + 0.25f + (float) entity.posX;
        float fy = random.nextFloat() * 0.5f + 0.25f + (float) entity.posY;
        float fz = random.nextFloat() * 0.5f + 0.25f + (float) entity.posZ;
        EntityItem item = new EntityItem(entity.world, fx, fy, fz, stack);
        item.motionX = random.nextGaussianFloat() * 0.05;
        item.motionY = random.nextGaussianFloat() * 0.05;
        item.motionZ = random.nextGaussianFloat() * 0.05;
        event.getDrops().add(item);
    }
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        int loot = event.getLootingLevel();
        if(random.nextInt(256) + 3 * loot > Config.dropManaChance) {
            boolean piggy = Config.dropManaApple
                    && entity instanceof EntityPig
                    && random.nextInt(16) / (loot + 1) < 2;
            spawnEntityItem(event, piggy ? new ItemStack(ManaCraftItems.manaApple)
                    : new ItemStack(ManaCraftItems.mana, (int) (1 + (random.nextFloat() + 0.25) * loot)));
        }
    }
}
