package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.api.common.IItemManaDamagable;
import mana_craft.config.ManaCraftConfig;
import mana_craft.init.ManaCraftBlocks;
import mana_craft.init.ManaCraftEnchantments;
import mana_craft.init.ManaCraftItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import sausage_core.api.util.explosion.ExExplosion;
import sausage_core.api.util.nbt.NBTs;

import java.util.Random;

import static mana_craft.config.ManaCraftConfig.durability;
import static mana_craft.config.ManaCraftConfig.enchantability;
import static sausage_core.api.util.common.SausageUtils.nonnull;

public class ItemManaTools {
	public static final Item.ToolMaterial MANA_TOOL = nonnull(
			EnumHelper.addToolMaterial(ManaCraft.MODID + ":MANA", Item.ToolMaterial.DIAMOND.getHarvestLevel(),
					durability * 20, 6.5f, 3.5f, enchantability));

	public static class ItemManaSword extends ItemSword implements IItemManaDamagable {
		public ItemManaSword() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 14;
		}
	}

	public static class ItemManaPickaxe extends ItemPickaxe implements IItemManaDamagable {
		public ItemManaPickaxe() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 19;
		}
	}

	public static class ItemManaAxe extends ItemAxe implements IItemManaDamagable {
		public ItemManaAxe() {
			super(MANA_TOOL, 12, -2.8f);
		}

		@Override
		public int getManaValue() {
			return 23;
		}
	}

	public static class ItemManaShovel extends ItemSpade implements IItemManaDamagable {
		public ItemManaShovel() {
			super(MANA_TOOL);
		}

		@Override
		public int getManaValue() {
			return 9;
		}
	}

	public static class ItemManaHoe extends ItemHoe implements IItemManaDamagable {
		public ItemManaHoe() {
			super(MANA_TOOL);
			MinecraftForge.EVENT_BUS.register(this);
			MinecraftForge.EVENT_BUS.register(ItemManaTools.class);
		}

		@Override
		public int getManaValue() {
			return 13;
		}

		@SubscribeEvent
		public void onUseHoe(UseHoeEvent event) {
			ItemStack item = event.getCurrent();
			if(ManaCraftConfig.finalHoe && ItemStack.areItemStacksEqual(item, new ItemStack(ManaCraftItems.mana_hoe))) {
				World world = event.getWorld();
				Block block = world.getBlockState(event.getPos()).getBlock();
				if(block == ManaCraftBlocks.mana_body) {
					item.setStackDisplayName("Final Hoe of Mana");
					item.addEnchantment(Enchantments.SHARPNESS, 7);
					item.addEnchantment(Enchantments.FIRE_ASPECT, 3);
					item.getOrCreateSubCompound("display").setTag("Lore", NBTs.asList(I18n.format("message.mana_craft.hoe")));
					world.setBlockToAir(event.getPos());
					ExExplosion.builder(world)
							.by(event.getEntity())
							.at(event.getPos())
							.sizeOf(1.5f)
							.causesFire()
							.hurtEntity()
							.spawnParticles()
							.build()
							.apply();
					event.getEntityPlayer().addExperience(15);
					ManaCraft.giveAdvancement(event.getEntityPlayer(), "final_hoe");
					event.setResult(Event.Result.ALLOW);
				}
			}
		}
	}

	public static class ItemManaShears extends ItemShears implements IItemManaDamagable {
		public ItemManaShears() {
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

	@SubscribeEvent
	public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
		Random random = event.getEntityPlayer().getRNG();
		ItemStack stack = event.getOriginal();
		if(stack.getItem() instanceof IItemManaDamagable && !(stack.getItem() instanceof ItemArmor)) {
			EntityPlayer player = event.getEntityPlayer();
			int value = ((IItemManaDamagable) stack.getItem()).getManaValue();
			if(value <= 0) return;
			int i = value > 1 ? 1 + random.nextInt(value - 1) : 1;
			int l = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.mana_recycler, stack);
			ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ManaCraftItems.mana, l + i * (random.nextInt(l + 2) + 1)));
		}
	}
}
