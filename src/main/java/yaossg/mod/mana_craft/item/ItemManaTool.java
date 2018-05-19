package yaossg.mod.mana_craft.item;

import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

public interface ItemManaTool {
     Item.ToolMaterial MANA_TOOL
            = EnumHelper.addToolMaterial("MANA", Item.ToolMaterial.DIAMOND.getHarvestLevel(), 256, 6, 3, 32)
             .setRepairItem(new ItemStack(ManaCraftItems.itemManaIngot));

    int getManaValue();
    class ItemManaSword extends ItemSword implements ItemManaTool {
        ItemManaSword() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 8;
        }
    }

    class ItemManaPickaxe extends ItemPickaxe implements ItemManaTool {
        ItemManaPickaxe() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 13;
        }
    }

    class ItemManaAxe extends ItemAxe implements ItemManaTool {
        ItemManaAxe() {
            super(MANA_TOOL, 12, -2.8f);
        }
        @Override
        public int getManaValue() {
            return 17;
        }
    }

    class ItemManaShovel extends ItemSpade implements ItemManaTool {
        ItemManaShovel() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 3;
        }
    }

    class ItemManaHoe extends ItemHoe implements ItemManaTool {
        ItemManaHoe() {
            super(MANA_TOOL);
        }
        @Override
        public int getManaValue() {
            return 7;
        }
    }

    class ItemManaShears extends ItemShears implements ItemManaTool {
        ItemManaShears() {
            this.setMaxStackSize(1);
            this.setMaxDamage(288);
        }
        @Override
        public int getManaValue() {
            return 6;
        }
    }
}
