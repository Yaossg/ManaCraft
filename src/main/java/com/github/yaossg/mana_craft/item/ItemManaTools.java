package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.api.IItemManaTool;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

// TODO remove magic numbers
public interface ItemManaTools {
     Item.ToolMaterial MANA_TOOL
            = EnumHelper.addToolMaterial("MANA", Item.ToolMaterial.DIAMOND.getHarvestLevel(), 256, 6, 3, 32)
             .setRepairItem(new ItemStack(ManaCraftItems.manaIngot));

    class ItemManaSword extends ItemSword implements IItemManaTool {
        ItemManaSword() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 8;
        }
    }

    class ItemManaPickaxe extends ItemPickaxe implements IItemManaTool {
        ItemManaPickaxe() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 13;
        }
    }

    class ItemManaAxe extends ItemAxe implements IItemManaTool {
        ItemManaAxe() {
            super(MANA_TOOL, 12, -2.8f);
        }
        @Override
        public int getManaValue() {
            return 17;
        }
    }

    class ItemManaShovel extends ItemSpade implements IItemManaTool {
        ItemManaShovel() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 3;
        }
    }

    class ItemManaHoe extends ItemHoe implements IItemManaTool {
        ItemManaHoe() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 7;
        }
    }

    class ItemManaShears extends ItemShears implements IItemManaTool {
        ItemManaShears() {
            setMaxStackSize(1);
            setMaxDamage(288);
        }
        @Override
        public int getManaValue() {
            return 6;
        }
    }
}
