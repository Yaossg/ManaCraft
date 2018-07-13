package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.api.IItemManaTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import static com.github.yaossg.mana_craft.config.ManaCraftConfig.durability;
import static com.github.yaossg.mana_craft.config.ManaCraftConfig.enchantability;

public class ItemManaTools {
    public static final Item.ToolMaterial MANA_TOOL =
            EnumHelper.addToolMaterial("MANA", Item.ToolMaterial.DIAMOND.getHarvestLevel(),
                            durability * 20, 6, 3, enchantability)
                    .setRepairItem(new ItemStack(ManaCraftItems.manaIngot));

    public static class ItemManaSword extends ItemSword implements IItemManaTool {
        ItemManaSword() {
            super(MANA_TOOL);
        }

        @Override
        public int getManaValue() {
            return 8;
        }
    }

    public static class ItemManaPickaxe extends ItemPickaxe implements IItemManaTool {
        ItemManaPickaxe() {
            super(MANA_TOOL);
        }

        @Override
        public int getManaValue() {
            return 13;
        }
    }

    public static class ItemManaAxe extends ItemAxe implements IItemManaTool {
        ItemManaAxe() {
            super(MANA_TOOL, 12, -2.8f);
        }

        @Override
        public int getManaValue() {
            return 17;
        }
    }

    public static class ItemManaShovel extends ItemSpade implements IItemManaTool {
        ItemManaShovel() {
            super(MANA_TOOL);
        }

        @Override
        public int getManaValue() {
            return 3;
        }
    }

    public static class ItemManaHoe extends ItemHoe implements IItemManaTool {
        ItemManaHoe() {
            super(MANA_TOOL);
        }

        @Override
        public int getManaValue() {
            return 7;
        }
    }

    public static class ItemManaShears extends ItemShears implements IItemManaTool {
        ItemManaShears() {
            setMaxStackSize(1);
            setMaxDamage(durability * 14);
        }

        @Override
        public float getDestroySpeed(ItemStack stack, IBlockState state) {
            Block block = state.getBlock();
            if(block != Blocks.WEB && state.getMaterial() != Material.LEAVES) {
                return block == Blocks.WOOL ? 30f : super.getDestroySpeed(stack, state);
            } else {
                return 90f;
            }
        }

        @Override
        public int getManaValue() {
            return 6;
        }
    }
}
