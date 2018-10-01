package mana_craft.api;

import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.item.ManaCraftItems;
import sausage_core.api.util.common.IDefaultSpecialArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * implement it to be a mana tool, weapon or armor
 * for a tool or weapon, mana will be dropped automatically by ManaCraft
 * for an armor, you should implement {@link IDefaultSpecialArmor}
 *      and then just invoke {@link IItemManaDamagable#onArmorBroken(EntityLivingBase, ItemStack)}
 *      in {@link IDefaultSpecialArmor#onArmorBroken(EntityLivingBase, ItemStack, DamageSource, int, int)}
 * */
@FunctionalInterface
public interface IItemManaDamagable {

    int getManaValue();

    /**
     * armor broken implementation
     * */
    default void onArmorBroken(EntityLivingBase entity, @Nonnull ItemStack stack) {
        Random random = entity.getRNG();
        int base = getManaValue();
        int level = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.manaRecycler, stack);
        InventoryHelper.spawnItemStack(entity.world, entity.posX, entity.posY, entity.posZ,
                new ItemStack(ManaCraftItems.mana, base + level + random.nextInt(base * (level + 2))));
    }
}
