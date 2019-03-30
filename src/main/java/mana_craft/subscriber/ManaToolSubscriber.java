package mana_craft.subscriber;

import mana_craft.ManaCraft;
import mana_craft.api.common.IItemManaDamagable;
import mana_craft.block.ManaCraftBlocks;
import mana_craft.config.ManaCraftConfig;
import mana_craft.enchantment.ManaCraftEnchantments;
import mana_craft.item.ManaCraftItems;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import sausage_core.api.util.explosion.ExExplosion;
import sausage_core.api.util.nbt.NBTs;

import java.util.Random;

public class ManaToolSubscriber {
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

	@SubscribeEvent
	public static void onUseHoe(UseHoeEvent event) {
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
