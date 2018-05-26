package yaossg.mod.mana_craft.item;

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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yaossg.mod.mana_craft.ManaCraft;
import yaossg.mod.mana_craft.config.Config;
import yaossg.mod.sausage_core.api.util.Explosions;


public class ItemManaApple extends ItemFood {
    ItemManaApple() {
        super(6,1.0f,false);
        this.setAlwaysEdible();
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
        if(target instanceof EntityPig) {
            EntityPig pig = (EntityPig) target;
            playerIn.attackEntityFrom(new DamageSource("byPig")
                    .setDifficultyScaled().setExplosion().setMagicDamage().setFireDamage(), 32);
            ItemManaApple.appleExplosin(playerIn, pig, pig.world.isRemote);
            stack.shrink(1);
            return true;
        }
        return false;
    }
    public static void appleExplosin(Entity playerIn, EntityPig pig, boolean romote) {
        if(playerIn.getServer() != null)
            playerIn.getServer().getPlayerList().sendMessage(new TextComponentTranslation("message.mana_craft.pig"));
        if (!romote)
            if (Config.bombSize > 0) {
                Explosions.createThenApply(pig.world, pig, pig.getPosition(), Config.bombSize, Config.fire, Config.damage);
                float step = (float) Math.PI / Config.bombSize;
                for (float f = 0; f <= 2 * Math.PI; f += step) {
                    pig.world.spawnEntity(new EntityLightningBolt(pig.world,
                            pig.posX + Config.bombSize * MathHelper.cos(f), pig.posY,
                            pig.posZ + Config.bombSize * MathHelper.sin(f), false));
                }
                InventoryHelper.spawnItemStack(playerIn.world, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(ManaCraftItems.itemManaPork));
            }
        ManaCraft.giveAdvancement(playerIn, "pig_bomb");
    }
}
