package yaossg.mod.mana_craft.item;

import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import yaossg.mod.mana_craft.ManaCraft;

public class ItemManaArmor extends ItemArmor {
    private static ItemArmor.ArmorMaterial manaArmorMaterial
            = EnumHelper.addArmorMaterial("MANA", ManaCraft.MODID + ":mana",
            10, new int[] {3, 6, 5, 2}, 32, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f);
    public ItemManaArmor(EntityEquipmentSlot slot) {
        super(manaArmorMaterial, manaArmorMaterial.ordinal(), slot);
    }
}
