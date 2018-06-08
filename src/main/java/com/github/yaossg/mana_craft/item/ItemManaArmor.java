package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.event.ManaToolsEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemManaArmor extends ItemArmor {
    public static ItemArmor.ArmorMaterial MANA_ARMOR = EnumHelper.addArmorMaterial("MANA_ARMOR", ManaCraft.MODID + ":mana",
            20, new int[]{3, 6, 5, 2}, 32, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f)
            .setRepairItem(new ItemStack(ManaCraftItems.manaIngot));

    ItemManaArmor(EntityEquipmentSlot equipmentSlotIn) {
        super(ItemManaArmor.MANA_ARMOR, ItemManaArmor.MANA_ARMOR.ordinal(), equipmentSlotIn);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if(itemStack.getMaxDamage() - itemStack.getItemDamage() < itemStack.getMaxDamage() / 20) {
            player.inventory.armorInventory.set(getEquipmentSlot().ordinal() - 2, ItemStack.EMPTY);
            int base = getArmorMaterial().getDamageReductionAmount(armorType);
            InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ, new ItemStack(ManaCraftItems.mana,
                    base + ManaToolsEvent.random.nextInt( 3 * base)));
        }
    }
}
