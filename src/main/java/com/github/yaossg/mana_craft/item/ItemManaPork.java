package com.github.yaossg.mana_craft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemManaPork extends ItemFood {
    public ItemManaPork() {
        super(12, 0.8f, true);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if(!worldIn.isRemote) {
            player.addExperience(200);
            player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 15, 15));
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 400, 3));
            player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400, 0));
        }
    }
}
