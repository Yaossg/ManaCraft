package com.github.yaossg.mana_craft.potion;

import com.github.yaossg.mana_craft.entity.EntityManaBall;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.Random;

public class PotionManaEvoker extends Potion {
    PotionManaEvoker() {
        super(false, 0xEB7BEA);
        setPotionName("ManaEvoker");
    }

    @Override
    public void performEffect(EntityLivingBase user, int amplifier) {
        Random random = user.getRNG();
        EntityManaBall ball = EntityManaBall.get(user.world, user, true)
                .setDamage(4);
        ball.posY = ball.posY - 0.48f;
        ball.shoot(user, random.nextFloat() * 360 - 180,
                random.nextFloat() * 360 - 180, 0,
                EntityManaBall.lowVelocity, EntityManaBall.defaultInaccuracy);
        ball.motionX -= user.motionX;
        ball.motionZ -= user.motionZ;
        if(!user.onGround)
            ball.motionY -= user.motionY;
        user.world.spawnEntity(ball);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 16 >> amplifier;
        return i <= 0 || duration % i == 0;
    }

    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(ManaCraftPotions.texture);
        mc.currentScreen.drawTexturedModalRect(x + 6, y + 7, 0, 0, 18, 18);
    }

    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(ManaCraftPotions.texture);
        mc.ingameGUI.drawTexturedModalRect(x + 4, y + 4, 0, 0, 18, 18);
    }
}
