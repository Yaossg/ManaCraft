package mana_craft.enchantment;

import mana_craft.ManaCraft;
import mana_craft.entity.EntityManaBall;
import mana_craft.item.ItemManaArmor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Random;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class EnchantmentManaEvoker extends Enchantment {
	static final EnumEnchantmentType TYPE = EnumHelper.addEnchantmentType("mana_armor", ItemManaArmor.class::isInstance);

	public EnchantmentManaEvoker() {
		super(Rarity.RARE, TYPE, new EntityEquipmentSlot[]{HEAD, CHEST, LEGS, FEET});
		setName(ManaCraft.MODID + ".mana_evoker").setRegistryName("mana_evoker");
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 10 + enchantmentLevel * 10;
	}

	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return getMinEnchantability(enchantmentLevel) + 20;
	}

	@Override
	public void onUserHurt(EntityLivingBase user, Entity attacker, int level) {
		Random random = user.getRNG();
		ItemStack stack = EnchantmentHelper.getEnchantedItem(ManaCraftEnchantments.mana_evoker, user);
		if(stack.getItem() instanceof ItemManaArmor) {
			ManaCraft.giveAdvancement(user, "protect_around");
			int times = level + 2 + random.nextInt(2 * level + 2);
			for(int i = 0; i < times; ++i) {
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
			damageArmor(stack, ((int) (times / 2.5f)), user);
		}
	}

	private void damageArmor(ItemStack stack, int amount, EntityLivingBase entity) {
		((ISpecialArmor) stack.getItem())
				.damageArmor(entity, stack, new DamageSource("ManaEvokeDamage"), amount,
						((ItemArmor) stack.getItem()).armorType.ordinal() - 2);
	}
}