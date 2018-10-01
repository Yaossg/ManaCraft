package mana_craft.enchantment;

import mana_craft.ManaCraft;
import mana_craft.item.ItemManaWand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.util.EnumHelper;

public class EnchantmentFloating extends Enchantment {
    static final EnumEnchantmentType TYPE = EnumHelper.addEnchantmentType("mana_wand", input -> input instanceof ItemManaWand);
    EnchantmentFloating() {
        super(Rarity.VERY_RARE, TYPE,
                new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        setName(ManaCraft.MODID + ".floating").setRegistryName("floating");
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 20;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }
}
