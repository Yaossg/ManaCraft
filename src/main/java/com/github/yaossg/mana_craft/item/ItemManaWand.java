package com.github.yaossg.mana_craft.item;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.IItemManaTool;
import com.github.yaossg.mana_craft.entity.EntityManaBall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemManaWand extends Item implements IItemManaTool {
    ItemManaWand() {
        this.setMaxDamage(255);
        this.setMaxStackSize(1);
    }
    private static boolean isAmmo(ItemStack stack) {
        return stack.getItem() == ManaCraftItems.manaBall;
    }
    private static ItemStack findAmmo(EntityPlayer player) {
        if(isAmmo(player.getHeldItemOffhand())) {
            return player.getHeldItemOffhand();
        } else if(isAmmo(player.getHeldItemMainhand())) {
            return player.getHeldItemMainhand();
        } else {
            return player.inventory.mainInventory.stream().filter(ItemManaWand::isAmmo).findAny().orElse(ItemStack.EMPTY);
        }
    }
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entityLiving;
            ItemStack ammo = findAmmo(player);
            if(player.isCreative()) {
                ammo = new ItemStack(ManaCraftItems.manaBall);
            }
            if (!ammo.isEmpty()) {
                if (!worldIn.isRemote) {
                    EntityManaBall entity = new EntityManaBall(worldIn, player);
                    entity.shoot(player, player.rotationPitch, player.rotationYaw, 0,
                            EntityManaBall.betterVelocity + (useDuration - timeLeft) / 32f, EntityManaBall.defaultInaccuracy);
                    worldIn.spawnEntity(entity);
                    if(!player.isCreative() && stack.attemptDamageItem(1, player.getRNG(), player instanceof EntityPlayerMP ? (EntityPlayerMP)player : null)) {
                        player.renderBrokenItemStack(stack);
                        ItemStack copy = stack.copy();
                        stack.shrink(1);
                        stack.setItemDamage(0);
                        net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, copy, player.getActiveHand());
                    }
                    ManaCraft.giveAdvancement(player, "not_staff");
                }
                if (!player.isCreative()) {
                    ammo.shrink(1);
                    if (ammo.isEmpty()) {
                        player.inventory.deleteStack(ammo);
                    }
                }
            }
        }
    }
    public final int useDuration = 48000;
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return useDuration;
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
}
