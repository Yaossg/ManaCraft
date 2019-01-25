package mana_craft.item;

import mana_craft.ManaCraft;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import sausage_core.api.util.explosion.ExExplosion;

import javax.annotation.Nullable;

public class ItemManaApple extends ItemFood {
    ItemManaApple() {
        super(6, 1.0f, false);
        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if(!worldIn.isRemote) {
            player.addExperience(20);
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1200, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1200, 0));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 18;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        if(target instanceof EntityPig && atCorner(target)) {
            EntityPig pig = (EntityPig) target;
            playerIn.attackEntityFrom(new DamageSource(ManaCraft.MODID + ".by_pig").setDifficultyScaled().setExplosion().setMagicDamage().setFireDamage(), 32);
            ItemManaApple.appleExplosion(playerIn, pig);
            stack.shrink(1);
            return true;
        }
        return false;
    }

    public static boolean atCorner(Entity entity) {
        World world = entity.world;
        BlockPos pos = entity.getPosition();
        if(world.getBlockState(pos).getMaterial() != Material.AIR) return false;
        int air4 = 0;
        for(EnumFacing side : EnumFacing.HORIZONTALS)
            if(world.getBlockState(pos.offset(side)).getMaterial() == Material.AIR) air4++;
        if(air4 != 2) return false;
        int air8 = 0;
        for(EnumFacing side : EnumFacing.HORIZONTALS)
            if(world.getBlockState(pos.offset(side).offset(side.rotateY())).getMaterial() == Material.AIR) air8++;
        return air8 == 3;
    }

    public static void appleExplosion(@Nullable Entity playerIn, EntityPig pig) {
        if(playerIn != null && playerIn.getServer() != null) {
            playerIn.getServer().getPlayerList().sendMessage(new TextComponentTranslation("message.mana_craft.pig"));
            ManaCraft.giveAdvancement(playerIn, "pig_bomb");
        }
        pig.setDead();
        World world = pig.world;
        ExExplosion.builder(world)
                .by(pig)
                .at(pig.getPosition())
                .sizeOf(10)
                .spawnParticles()
                .build()
                .apply();
        float step = (float) Math.PI / 8;
        for (float f = 0; f <= 2 * Math.PI; f += step)
            world.spawnEntity(new EntityLightningBolt(world, pig.posX + 10 * MathHelper.cos(f), pig.posY, pig.posZ + 10 * MathHelper.sin(f), false));
        if(!world.isRemote)
            InventoryHelper.spawnItemStack(world, pig.posX, pig.posY, pig.posZ, new ItemStack(ManaCraftItems.mana_pork));
    }
}
