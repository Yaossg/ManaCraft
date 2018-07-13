package com.github.yaossg.mana_craft.enchantment;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.IItemManaTool;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.common.util.EnumHelper;

public class EnchantmentManaRecycler extends Enchantment {
    static final EnumEnchantmentType TYPE = EnumHelper.addEnchantmentType("mana_tool", input -> input instanceof IItemManaTool);
    protected EnchantmentManaRecycler() {
        super(Rarity.UNCOMMON, TYPE, EntityEquipmentSlot.values());
        setName(ManaCraft.MODID + ".mana_recycler").setRegistryName("mana_recycler");
    }
    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMinEnchantability(enchantmentLevel) + 20;
    }

}
