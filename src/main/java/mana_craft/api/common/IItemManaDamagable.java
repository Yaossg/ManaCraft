package mana_craft.api.common;

import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.item.ManaCraftItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import sausage_core.api.core.common.IDefaultSpecialArmor;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * implement this to be a mana tool, weapon or armor
 * for a tool or weapon, mana is dropped automatically by ManaCraft
 * */
@FunctionalInterface
public interface IItemManaDamagable {

    /**
     * @return mana value, which decides how much mana can be dropped
     * */
    int getManaValue();

    /**
     * for a mana armor, you should implement {@link IDefaultSpecialArmor},
     * then just call this method when
     * {@link IDefaultSpecialArmor#onArmorBroken(EntityLivingBase, ItemStack, DamageSource, int, IDefaultSpecialArmor.EnumArmorType)}
     * is on
     *
     * @param entity whose armor is broken
     * @param stack the armor item
     * */
    default void onArmorBroken(EntityLivingBase entity, @Nonnull ItemStack stack) {
        Random random = entity.getRNG();
        int base = getManaValue();
        int level = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.mana_recycler, stack);
        InventoryHelper.spawnItemStack(entity.world, entity.posX, entity.posY, entity.posZ,
                new ItemStack(ManaCraftItems.mana, base + level + random.nextInt(base * (level + 2))));
    }
}
