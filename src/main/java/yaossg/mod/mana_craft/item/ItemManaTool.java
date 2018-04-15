package yaossg.mod.mana_craft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.EnumHelper;
import yaossg.mod.mana_craft.Util;


public interface ItemManaTool {
     Item.ToolMaterial manaToolMaterial
            = EnumHelper.addToolMaterial("MANA", Util.DIAMOND_PICKAXE, 256, 6, 3, 32 ).setRepairItem(new ItemStack(ManaCraftItems.itemManaIngot));

    int getManaValue();
    class ItemManaSword extends ItemSword implements ItemManaTool {
        public ItemManaSword() {
            super(manaToolMaterial);
        }
        @Override
        public int getManaValue() {
            return 8;
        }
    }

    class ItemManaPickaxe extends ItemPickaxe implements ItemManaTool {
        public ItemManaPickaxe() {
            super(manaToolMaterial);
        }
        @Override
        public int getManaValue() {
            return 13;
        }
    }

    class ItemManaAxe extends ItemAxe implements ItemManaTool {
        public ItemManaAxe() {
            super(manaToolMaterial, 12, -2.8f);
        }
        @Override
        public int getManaValue() {
            return 17;
        }
    }

    class ItemManaShovel extends ItemSpade implements ItemManaTool {
        public ItemManaShovel() {
            super(manaToolMaterial);
        }
        @Override
        public int getManaValue() {
            return 3;
        }
    }

    class ItemManaHoe extends ItemHoe implements ItemManaTool {
        public ItemManaHoe() {
            super(manaToolMaterial);
        }
        @Override
        public int getManaValue() {
            return 7;
        }
    }

    class ItemManaShears extends ItemShears implements ItemManaTool {
        public ItemManaShears() {
            this.setMaxStackSize(1);
            this.setMaxDamage(288);
        }

        @Override
        public int getManaValue() {
            return 6;
        }
    }
    abstract class ItemManaBow extends ItemBow implements ItemManaTool {

    }

}
