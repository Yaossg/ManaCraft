package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.api.IItemManaDamagable;
import mana_craft.config.ManaCraftConfig;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;
import sausage_core.api.core.common.IDefaultSpecialArmor;

import javax.annotation.Nonnull;

import static mana_craft.config.ManaCraftConfig.*;
import static sausage_core.api.util.common.SausageUtils.nonnull;

public class ItemManaArmor extends ItemArmor implements IDefaultSpecialArmor, IItemManaDamagable {
    public static final ItemArmor.ArmorMaterial MANA_ARMOR = nonnull(
            EnumHelper.addArmorMaterial(ManaCraft.MODID + ":MANA", ManaCraft.MODID + ":mana",
                    durability, armor, enchantability,
                    SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, ManaCraftConfig.toughness));

    public ItemManaArmor(EntityEquipmentSlot equipmentSlotIn) {
        super(MANA_ARMOR, MANA_ARMOR.ordinal(), equipmentSlotIn);
    }

    @Override
    public int getManaValue() {
        return getArmorMaterial().getDamageReductionAmount(armorType);
    }

    @Override
    public void onArmorBroken(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, EnumArmorType type) {
        onArmorBroken(entity, stack);
        entity.renderBrokenItemStack(stack);
        stack.shrink(1);
    }
}