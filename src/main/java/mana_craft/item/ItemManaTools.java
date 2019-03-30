package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.api.common.IItemManaDamagable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

import static mana_craft.config.ManaCraftConfig.durability;
import static mana_craft.config.ManaCraftConfig.enchantability;
import static sausage_core.api.util.common.SausageUtils.nonnull;

public class ItemManaTools {
	public static final Item.ToolMaterial MANA_TOOL = nonnull(
			EnumHelper.addToolMaterial(ManaCraft.MODID + ":MANA", Item.ToolMaterial.DIAMOND.getHarvestLevel(),
					durability * 20, 6.5f, 3.5f, enchantability));

	public static class ItemManaSword extends ItemSword implements IItemManaDamagable {
		ItemManaSword() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 14;
		}
	}

	public static class ItemManaPickaxe extends ItemPickaxe implements IItemManaDamagable {
		ItemManaPickaxe() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 19;
		}
	}

	public static class ItemManaAxe extends ItemAxe implements IItemManaDamagable {
		ItemManaAxe() {
			super(MANA_TOOL, 12, -2.8f);
		}

		@Override
		public int getManaValue() {
			return 23;
		}
	}

	public static class ItemManaShovel extends ItemSpade implements IItemManaDamagable {
		ItemManaShovel() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 9;
		}
	}

	public static class ItemManaHoe extends ItemHoe implements IItemManaDamagable {
		ItemManaHoe() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 13;
		}
	}

	public static class ItemManaShears extends ItemShears implements IItemManaDamagable {
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
