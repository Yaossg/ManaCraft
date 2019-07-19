package mana_craft.item;

import mana_craft.ManaCraft;
import mana_craft.api.common.IItemManaDamagable;
import mana_craft.config.ManaCraftConfig;
import mana_craft.entity.EntityManaBall;
import mana_craft.init.ManaCraftEnchantments;
import mana_craft.init.ManaCraftItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemManaWand extends Item implements IItemManaDamagable {
	public ItemManaWand() {
		setMaxDamage(ManaCraftConfig.durability * 10);
		setMaxStackSize(1);
	}

	private static boolean isAmmo(ItemStack stack) {
		return stack.getItem() == ManaCraftItems.mana_ball;
	}

	private static ItemStack findAmmo(EntityPlayer player) {
		return isAmmo(player.getHeldItemOffhand()) ? player.getHeldItemOffhand()
				: player.inventory.mainInventory.stream().filter(ItemManaWand::isAmmo).findAny().orElse(ItemStack.EMPTY);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			ItemStack ammo = findAmmo(player);
			if (player.isCreative()) ammo = new ItemStack(ManaCraftItems.mana_ball);
			if (ammo.isEmpty()) return;
			int progress = getMaxItemUseDuration(stack) - timeLeft;
			float speed = progress / 20f;
			if (speed < 0.16f) return;
			if (!worldIn.isRemote) {
				int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				boolean floating = EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.floating, stack) > 0;
				if (!floating && (floating = progress > 200))
					ManaCraft.giveAdvancement(player, "fly_so_high");
				EntityManaBall entity = EntityManaBall.get(worldIn, player, floating)
						.setDamage(6.4f + power + speed * (power + 1))
						.setFlame(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0);
				entity.shoot(player, player.rotationPitch, player.rotationYaw, 0,
						(EntityManaBall.highVelocity + speed * 0.8f), EntityManaBall.defaultInaccuracy);
				player.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1, 1 / (player.getRNG().nextFloat() * 0.4f + 0.8f));
				worldIn.spawnEntity(entity);
				if (!player.isCreative() && stack.attemptDamageItem(1, player.getRNG(),
						player instanceof EntityPlayerMP ? (EntityPlayerMP) player : null)) {
					player.renderBrokenItemStack(stack);
					ItemStack copy = stack.copy();
					stack.shrink(1);
					ForgeEventFactory.onPlayerDestroyItem(player, copy, player.getActiveHand());
				}
				ManaCraft.giveAdvancement(player, "cool_wand");
			}
			if (!player.isCreative())
				ammo.shrink(1);
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack item = playerIn.getHeldItem(handIn);
		if (!playerIn.isCreative() && findAmmo(playerIn).isEmpty()) {
			return ActionResult.newResult(EnumActionResult.FAIL, item);
		} else {
			playerIn.setActiveHand(handIn);
			return ActionResult.newResult(EnumActionResult.SUCCESS, item);
		}
	}

	@Override
	public int getManaValue() {
		return 5;
	}

	@Override
	public int getItemEnchantability() {
		return ManaCraftConfig.enchantability;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return super.canApplyAtEnchantingTable(stack, enchantment)
				|| enchantment == Enchantments.POWER
				|| enchantment == Enchantments.KNOCKBACK
				|| enchantment == Enchantments.FLAME;
	}
}
