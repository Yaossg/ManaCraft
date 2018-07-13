package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.IItemManaDamagable;
import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.github.yaossg.mana_craft.enchantment.ManaCraftEnchantments;
import com.github.yaossg.sausage_core.api.util.common.IDefaultSpecialArmor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.github.yaossg.mana_craft.config.ManaCraftConfig.*;

public class ItemManaArmor extends ItemArmor implements IDefaultSpecialArmor, IItemManaDamagable {
    public static final ItemArmor.ArmorMaterial MANA_ARMOR =
            EnumHelper.addArmorMaterial("MANA_ARMOR", ManaCraft.MODID + ":mana",
                    durability, armor, enchantability,
                    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, ManaCraftConfig.toughness)
                    .setRepairItem(new ItemStack(ManaCraftItems.manaIngot));

    public ItemManaArmor(EntityEquipmentSlot equipmentSlotIn) {
        super(MANA_ARMOR, MANA_ARMOR.ordinal(), equipmentSlotIn);
    }

    @Override
    public int getManaValue() {
        return getArmorMaterial().getDamageReductionAmount(armorType);
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
        Random random = entity.getRNG();
        if(stack.attemptDamageItem(damage, random, entity instanceof EntityPlayerMP ? (EntityPlayerMP) entity : null))
        {
            int base = getManaValue();
            int level = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.manaRecycler, stack);
            InventoryHelper.spawnItemStack(entity.world, entity.posX, entity.posY, entity.posZ,
                    new ItemStack(ManaCraftItems.mana, base + level + random.nextInt(base * (level + 1))));
            entity.renderBrokenItemStack(stack);
            stack.shrink(1);
            stack.setItemDamage(0);
        }
    }
}