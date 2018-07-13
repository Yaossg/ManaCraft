package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.IItemManaDamagable;
import com.github.yaossg.mana_craft.config.ManaCraftConfig;
import com.github.yaossg.mana_craft.enchantment.ManaCraftEnchantments;
import com.github.yaossg.mana_craft.entity.EntityManaBall;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemManaWand extends Item implements IItemManaDamagable {
    ItemManaWand() {
        setMaxDamage(ManaCraftConfig.durability * 10);
        setMaxStackSize(1);
    }

    private static boolean isAmmo(ItemStack stack) {
        return stack.getItem() == ManaCraftItems.manaBall;
    }

    private static ItemStack findAmmo(EntityPlayer player) {
        return isAmmo(player.getHeldItemOffhand()) ? player.getHeldItemOffhand()
                : player.inventory.mainInventory.stream().filter(ItemManaWand::isAmmo).findAny().orElse(ItemStack.EMPTY);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if(entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            ItemStack ammo = findAmmo(player);
            if(player.isCreative()) ammo = new ItemStack(ManaCraftItems.manaBall);
            if(!ammo.isEmpty()) {
                float speed = (getMaxItemUseDuration(stack) - timeLeft) / 32f;
                if(speed > 0.1f) {
                    if(!worldIn.isRemote) {
                        int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        EntityManaBall entity = EntityManaBall.get(worldIn, player,
                                EnchantmentHelper.getEnchantmentLevel(ManaCraftEnchantments.floating, stack) > 0)
                                .setDamage(6.5f + power * 0.5f + speed * (power + 1))
                                .setFlame(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0);
                        entity.shoot(player, player.rotationPitch, player.rotationYaw, 0,
                                (EntityManaBall.highVelocity + speed), EntityManaBall.defaultInaccuracy);
                        worldIn.spawnEntity(entity);
                        if(!player.isCreative() && stack.attemptDamageItem(1, player.getRNG(),
                                player instanceof EntityPlayerMP ? (EntityPlayerMP) player : null)) {
                            player.renderBrokenItemStack(stack);
                            ItemStack copy = stack.copy();
                            stack.shrink(1);
                            stack.setItemDamage(0);
                            ForgeEventFactory.onPlayerDestroyItem(player, copy, player.getActiveHand());
                        }
                        ManaCraft.giveAdvancement(player, "not_staff");
                    }
                    if(!player.isCreative()) {
                        ammo.shrink(1);
                        if(ammo.isEmpty()) {
                            player.inventory.deleteStack(ammo);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 60000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack item = playerIn.getHeldItem(handIn);
        if(!playerIn.isCreative() && findAmmo(playerIn).isEmpty()) {
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
                || enchantment == Enchantments.FLAME
                || enchantment == ManaCraftEnchantments.floating;
    }
}
