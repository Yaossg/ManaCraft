package com.github.yaossg.mana_craft.event;

import com.github.yaossg.mana_craft.ManaCraft;
import com.github.yaossg.mana_craft.api.IItemManaTool;
import com.github.yaossg.mana_craft.block.ManaCraftBlocks;
import com.github.yaossg.mana_craft.item.ManaCraftItems;
import com.github.yaossg.sausage_core.api.util.BufferedRandom;
import com.github.yaossg.sausage_core.api.util.Explosions;
import com.github.yaossg.sausage_core.api.util.NBTs;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class ManaToolsEvent {

    public static final Random random = new BufferedRandom();
    @SubscribeEvent
    public static void onPlayerDestroyItem(PlayerDestroyItemEvent event) {
        Item item = event.getOriginal().getItem();
        if(item instanceof IItemManaTool) {
            EntityPlayer player = event.getEntityPlayer();
            InventoryHelper.spawnItemStack(player.world, player.posX, player.posY, player.posZ, new ItemStack(ManaCraftItems.mana,
                    1 + random.nextInt(((IItemManaTool) item).getManaValue() - 1)));
        }
    }

    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event) {
        ItemStack item = event.getCurrent();
        if(item.getItem().equals(ManaCraftItems.manaHoe)) {
            World world = event.getWorld();
            Block block = world.getBlockState(event.getPos()).getBlock();
            if (block.equals(ManaCraftBlocks.manaBlock) && ItemStack.areItemStacksEqual(item, new ItemStack(ManaCraftItems.manaHoe))) {
                item.setStackDisplayName("Final Hoe of Mana");
                item.addEnchantment(Enchantments.SHARPNESS, 7);
                item.addEnchantment(Enchantments.FIRE_ASPECT, 3);
                item.getOrCreateSubCompound("display").setTag("Lore", NBTs.asList(
                        I18n.format("message.mana_craft.hoe")));
                world.setBlockToAir(event.getPos());
                Explosions.createThenApply(world, event.getEntity(), event.getPos(), 1.25f,
                        true, false);
                event.getEntityPlayer().addExperience(10);
                ManaCraft.giveAdvancement(event.getEntityPlayer(), "final_hoe");
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
