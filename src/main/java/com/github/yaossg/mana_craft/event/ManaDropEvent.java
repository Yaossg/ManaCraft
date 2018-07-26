package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.github.yaossg.mana_craft.entity.EntityManaBall;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ManaDropEvent {

    private static void spawnEntityItem(LivingDropsEvent event, ItemStack stack) {
        EntityLivingBase entity = event.getEntityLiving();
        Random random = entity.getRNG();
        float fx = random.nextFloat() * 0.5f + 0.25f + (float) entity.posX;
        float fy = random.nextFloat() * 0.5f + 0.25f + (float) entity.posY;
        float fz = random.nextFloat() * 0.5f + 0.25f + (float) entity.posZ;
        EntityItem item = new EntityItem(entity.world, fx, fy, fz, stack);
        item.motionX = random.nextGaussian() * 0.05;
        item.motionY = random.nextGaussian() * 0.05;
        item.motionZ = random.nextGaussian() * 0.05;
        event.getDrops().add(item);
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        Random random = entity.getRNG();
        int loot = event.getLootingLevel();
        float chance = ManaCraftConfig.dropManaChance;
        if(random.nextFloat() < chance + (loot - 1) * chance * 0.8) {
            boolean piggy = ManaCraftConfig.dropManaApple && entity instanceof EntityPig && random.nextInt(16) / (loot + 1) < 2;
            spawnEntityItem(event, piggy
                    ? new ItemStack(ManaCraftItems.manaApple)
                    : new ItemStack(ManaCraftItems.mana, (int) (1 + (random.nextFloat() + 0.25) * loot)));
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        DamageSource source = event.getSource();
        EntityLivingBase entity = event.getEntityLiving();
        if(source instanceof EntityDamageSourceIndirect && entity instanceof EntityVillager) {
            EntityDamageSourceIndirect indirect = (EntityDamageSourceIndirect) source;
            if(indirect.getImmediateSource() instanceof EntityManaBall) {
                EntityManaBall ball = (EntityManaBall) indirect.getImmediateSource();
                if(!ball.playerFriendly)
                    InventoryHelper.spawnItemStack(entity.world, entity.posX, entity.posY, entity.posZ,
                            new ItemStack(ManaCraftItems.manaEmerald));
            }
        }
    }
}
